package com.gevinzone.homework1101.jedis;

import com.gevinzone.homework1101.business.ConcurrentService;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisDemo {
    public static void main(String[] args) {
        Jedis jedis = null;
//        jedis = new Jedis("localhost", 6379);
        GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
        JedisPool pool = new JedisPool(poolConfig, "localhost", 6379);

        try {
            jedis = pool.getResource();
//            jedisOpt(jedis);
            useLock(jedis);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void jedisOpt(Jedis jedis) {
        System.out.println(jedis.info());

        jedis.set("now", String.valueOf(System.currentTimeMillis()));
        System.out.println(jedis.get("now"));

        jedis.set("count", "1");
        jedis.incr("count");
        System.out.println(jedis.get("count"));
    }

    private static void useLock(Jedis jedis) {
        ConcurrentService service = new ConcurrentService(new JedisLock(jedis));
        int threads = 1;
        // threads > 1 会报错
        int result = service.multiThreadAdd(threads);
        System.out.println(result);
        System.out.print(result == threads);
    }
}
