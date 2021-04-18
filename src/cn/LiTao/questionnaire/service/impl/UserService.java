package cn.LiTao.questionnaire.service.impl;

import cn.LiTao.questionnaire.constant.ApplicationConstant;
import cn.LiTao.questionnaire.mapper.*;
import cn.LiTao.questionnaire.pojo.*;
import cn.LiTao.questionnaire.service.MyService;
import cn.LiTao.questionnaire.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
public class UserService extends MyService {
    private static final int REDIS_CACHE_EXPIRE = 60 * 60 * 24 * 15;

    private UserMapper userMapper;

    @Override
    public synchronized SqlSession createSqlSession() {
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
        return sqlSession;

    }

    //    获取mapper和sqlSession
    public UserService() {
        createSqlSession();
    }

    public void userRegister(User user) {
        SqlSession sqlSession = createSqlSession();
        userMapper.insertUser(user);
//        提交
        this.sqlSession.commit();
        closeSqlSession(sqlSession);
    }

    public User userLogin(User user) {
        SqlSession sqlSession = createSqlSession();
        User resultUser = userMapper.findUserByPhoneAndPassword(user);
        closeSqlSession(sqlSession);
        return resultUser;
    }

    public User findUserById(int id) {
        SqlSession sqlSession = createSqlSession();
        User user = userMapper.findUserById(id);
        user.getProjectList().sort((Project o1, Project o2) -> (int) (o2.getLastModifyTime().getTime() - o1.getLastModifyTime().getTime()));
        closeSqlSession(sqlSession);
        return user;
    }

    public String wxLogin(String sessionKey, String openId, String encryptedData, String iv) {
        SqlSession sqlSession = createSqlSession();
        String token = null;
        if (StringUtils.isNotBlank(sessionKey) && StringUtils.isNotBlank(openId)) {
            // 解码encryptedData获取到微信用户信息
            final JsonNode userInfo = WxApiUtil.getUserInfo(encryptedData, sessionKey, iv);
            if (Objects.nonNull(userInfo)) {
                User user = userMapper.findUserByWxOpenId(openId);
                // 微信用户第一次登录 保存用户数据到MySQL
                if (Objects.isNull(user)) {
                    final WxUser wxUser = ObjectConversion.userInfoToWxUser(userInfo);
                    user = ObjectConversion.wxUserToUser(wxUser);

                    userMapper.insertUser(user);
                    log.info("微信用户注册成功：{}", user);
                }
                try {
                    // 生成token并存入redis
                    final String userJsonStr = new JsonMapper().writeValueAsString(user);
                    final String uuid = UuidUtil.getUuid();
                    final String redisKey = ApplicationConstant.REDIS_SESSION_KEY_PREFIX + uuid;

                    JedisUtil.setex(redisKey, userJsonStr, REDIS_CACHE_EXPIRE);
                    token = uuid;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                log.warn("微信用户信息解码失败 sessionKey:{} openId:{}", sessionKey, openId);
            }
        } else {
            log.warn("微信用户传入空参数 sessionKey:{} openId:{}", sessionKey, openId);
        }
        this.sqlSession.commit();
        closeSqlSession(sqlSession);
        return token;
    }

    public List<UserBalanceRecord> findUserBalanceRecord(int uid) {
        final SqlSession sqlSession = createSqlSession();
        final UserBalanceRecordMapper mapper = sqlSession.getMapper(UserBalanceRecordMapper.class);

        final List<UserBalanceRecord> userBalanceRecords = mapper.selectByUserId(uid);
        sqlSession.close();
        return userBalanceRecords;
    }


    public List<PointGoods> findAllPointGoods() {
        final SqlSession sqlSession = createSqlSession();
        final PointGoodsMapper mapper = sqlSession.getMapper(PointGoodsMapper.class);

        final List<PointGoods> pointGoods = mapper.selectAll();
        sqlSession.close();

        return  pointGoods;
    }

    public Integer convertGoods(int uid, int gid, String phone) {
        final SqlSession sqlSession = createSqlSession();
        final PointGoodsMapper pointGoodsMapper = sqlSession.getMapper(PointGoodsMapper.class);
        final UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        final User user = userMapper.findUserByIdSimple(uid);
        final PointGoods pointGoods = pointGoodsMapper.selectByPrimaryKey(gid);


        if (Objects.isNull(pointGoods)) {
            return 1;
        } else if (Objects.isNull(user)) {
            return 2;
        } else if (user.getBalance().compareTo(pointGoods.getPrice()) < 0) {
            return 3;
        }

        final ConvertRecordMapper convertRecordMapper = sqlSession.getMapper(ConvertRecordMapper.class);
        final UserBalanceRecordMapper userBalanceRecordMapper = sqlSession.getMapper(UserBalanceRecordMapper.class);
        boolean success = true;

        final ConvertRecord convertRecord = new ConvertRecord();
        convertRecord.setUid(user.getId());
        convertRecord.setGid(gid);
        convertRecord.setGoodsName(pointGoods.getGoodsName());
        convertRecord.setStatus(0);
        convertRecord.setRemark("等待客服联系");
        convertRecord.setContactNumber(phone);
        convertRecord.setCreateTime(new Date());
        convertRecord.setUpdateTime(new Date());

        final UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
        userBalanceRecord.setUid(user.getId());
        userBalanceRecord.setType(0);
        userBalanceRecord.setChangeAmount(pointGoods.getPrice());
        userBalanceRecord.setRemark("兑换" + pointGoods.getGoodsName());
        userBalanceRecord.setCreateTime(new Date());

        final User updateUser = User.builder()
                .id(user.getId())
                .balance(user.getBalance() - pointGoods.getPrice())
                .build();

        try {
            convertRecordMapper.insert(convertRecord);
            userBalanceRecordMapper.insert(userBalanceRecord);
            userMapper.updateUserData(updateUser);
            sqlSession.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            success = false;
        }
        finally {
            sqlSession.close();
        }
        return success ? 0 : 4;
    }

    public List<ConvertRecord> getUserAllConvertRecord(int uid) {
        if (uid == 0) {
            return Collections.emptyList();
        }
        final SqlSession sqlSession = createSqlSession();
        final ConvertRecordMapper convertRecordMapper = sqlSession.getMapper(ConvertRecordMapper.class);

        final List<ConvertRecord> convertRecords = convertRecordMapper.selectByUserId(uid);
        sqlSession.close();

        return convertRecords;
    }

    public List<PointRule> getPointRule() {
        final SqlSession sqlSession = createSqlSession();
        final PointRuleMapper mapper = sqlSession.getMapper(PointRuleMapper.class);

        final List<PointRule> pointRules = mapper.selectAll();
        sqlSession.close();
        return pointRules;
    }
}
