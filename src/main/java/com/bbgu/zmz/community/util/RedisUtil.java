package com.bbgu.zmz.community.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Component
public class RedisUtil {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 向Redis中存值，永久有效
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * 向Redis中存值，设置有效时间
     */
    public String setHavetiem(String key, String value,Integer time) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value,"NX","EX",time);
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * 向Redis中存Map值
     */
    public String setHashMap(String key,Map map) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmset(key,map);
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
    /**
     * 向Redis中操作Map中某个值
     */
    public Long setHashMapOneValue(String map,String key,String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(map, key,value);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * 向Redis中操作Map中某个值加减
     */
    public Long setHashMapOneValueInc(String map,String key,long num) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(map,key,num);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * 根据传入Key获取指定Value
     */
    public String get(String key) {
        Jedis jedis = null;
        String value;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
        return value;
    }

    /**
     * 根据传入Key获取指定Value
     */
    public String getMapOneValue(String map,String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(map,key);
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
    /*
    取出Map中所有值
     */
    public Map getMap(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            return null;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
/*
存set
 */
    public Long setSet(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key,value);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
    /*
删set
 */
    public Long delSet(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key,value);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
    /*
返回set总数
*/
    public Long getSetLength(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
    /*
    判断set中某个元素是否存在
     */
    public Boolean getSetOneExit(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key,value);
        } catch (Exception e) {
            return false;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
    /*
    取得Set中的所有元素
     */
    public Set<String> getSetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            return null;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
    /**
     * 校验Key值是否存在
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            return false;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * 删除指定Key-Value
     */
    public Long delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }
    /**
     * 删除指定Key-Value
     */
    public Long dels(String key1,String key2) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key1,key2);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * 删除指定Key-Value
     */
    public String delAll() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.flushAll();
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    public Object eval(String script, List<String> key,  List<String> param) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.eval(script,key,param);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    // 以上为常用方法，更多方法自行百度

    /**
     * 释放连接
     */
    private static void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
