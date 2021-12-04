package com.gevinzone.redis.ha.demo.jedis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class JedisMain {
    public static void main(String[] args) {
        // C1.最简单demo
        Jedis jedis = new Jedis("localhost", 6380);
        System.out.println(jedis.info());
        jedis.set("uptime", Long.toString(System.currentTimeMillis()));
        System.out.println(jedis.get("uptime"));
        jedis.set("teacher", "Cuijing");
        System.out.println(jedis.get("teacher"));

		// C2.基于sentinel和连接池的demo
		Jedis sjedis = SentinelJedis.getJedis();
		System.out.println(sjedis.info());
		sjedis.set("uptime2", Long.toString(System.currentTimeMillis()));
		System.out.println(sjedis.get("uptime2"));
		SentinelJedis.close();

        // C3. 直接连接sentinel进行操作
        Jedis jedisSentinel = new Jedis("localhost", 26379); // 连接到sentinel
        List<Map<String, String>> masters = jedisSentinel.sentinelMasters();
        System.out.println(JSON.toJSONString(masters));
        List<Map<String, String>> slaves = jedisSentinel.sentinelSlaves("mymaster");
        System.out.println(JSON.toJSONString(slaves));
    }
}
