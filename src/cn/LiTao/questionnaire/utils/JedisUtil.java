package cn.LiTao.questionnaire.utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Devil
 */
public class JedisUtil {

    private static JedisPool pool;

    static {
        pool = new JedisPool("127.0.0.1", 6379);
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void close(Jedis jedis) {
        jedis.close();
    }

    public static void setex(String key, String value, int expire) {
        final Jedis jedis = getJedis();
        jedis.setex(key, expire, value);
        jedis.close();
    }

    public static String get(String key) {
        final Jedis jedis = getJedis();
        final String value = jedis.get(key);
        jedis.close();
        return value;
    }

}
