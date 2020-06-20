//package cn.LiTao.questionnaire.utils;
//
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//public class JedisUtil {
//
//    private static JedisPool pool;
//
//    static {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        pool = new JedisPool(poolConfig);
//    }
//
//    public static Jedis getJedis() {
//        return pool.getResource();
//    }
//
//}
