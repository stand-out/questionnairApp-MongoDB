package cn.LiTao.questionnaire.service.impl;

import cn.LiTao.questionnaire.constant.ApplicationConstant;
import cn.LiTao.questionnaire.mapper.UserMapper;
import cn.LiTao.questionnaire.pojo.Project;
import cn.LiTao.questionnaire.pojo.User;
import cn.LiTao.questionnaire.pojo.WxUser;
import cn.LiTao.questionnaire.service.MyService;
import cn.LiTao.questionnaire.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

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
}
