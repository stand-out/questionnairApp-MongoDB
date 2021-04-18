package cn.LiTao.questionnaire.service;

import cn.LiTao.questionnaire.utils.HongbaoStrategy;
import cn.LiTao.questionnaire.utils.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import java.util.Set;

public class HongbaoService {

    public void createHongbaoPrizePool(String prizePoolId, int amount, int size, int type) {
        Jedis jedis = JedisUtil.getJedis();

        // 获取剩余包并合并到总金额
        Set<String> lastPackage = jedis.smembers(prizePoolId);
        int preAmount = calcPreAmount(lastPackage);
        jedis.del(prizePoolId);
        amount += preAmount;
        size += lastPackage.size();
        String[] redisSetData;

        if (type == 2) {
            redisSetData = HongbaoStrategy.toRedisSetData(
                    HongbaoStrategy.evenly(amount, size));
        }
        else {
            redisSetData = HongbaoStrategy.toRedisSetData(
                    HongbaoStrategy.random(amount, size));
        }


        jedis.sadd(prizePoolId, redisSetData);
        JedisUtil.close(jedis);
    }

    public int getHongbaoFromPrizePool(String prizePoolId) {
        Jedis jedis = JedisUtil.getJedis();

        final String prizePoolItem = jedis.spop(prizePoolId);

        if (StringUtils.isBlank(prizePoolItem)) {
            jedis.del(prizePoolId);
            return -1;
        }
        JedisUtil.close(jedis);
        return Integer.parseInt(prizePoolItem.split(HongbaoStrategy.REDIS_JC_SPLIT_SYMBOL)[1]);
    }

    public Set<String> getPreData(String prizePoolId) {
        Jedis jedis = JedisUtil.getJedis();

        Set<String> lastPackage = jedis.smembers(prizePoolId);
        jedis.close();

        return lastPackage;
    }

    public int calcPreAmount (Set<String> lastPackage) {
        return lastPackage.stream().map(each -> each.split(HongbaoStrategy.REDIS_JC_SPLIT_SYMBOL)[1]).mapToInt(Integer::parseInt).sum();
    }

}
