package com.superwei.utils.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private JedisPool jedisPool;
    private static final int DEFULT_DATABASE;

    public RedisUtil() {
    }

    public Jedis getJedis() {
        return this.jedisPool.getResource();
    }

    public boolean lpush(String key, String value, int expire) {
        return this.lpush(key, value, expire, DEFULT_DATABASE);
    }

    public boolean lpush(String key, String value, int expire, int dataBase) {
        Jedis jedis = null;

        boolean var7;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.lpush(key, new String[]{value});
            if (expire > 0) {
                jedis.expire(key.getBytes(), expire);
            }

            return true;
        } catch (Exception var11) {
            logger.error("插入key为" + key + "的操作产生异常：", var11);
            var7 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public String lpop(String key) {
        return this.lpop(key, DEFULT_DATABASE);
    }

    public String lpop(String key, int dataBase) {
        Jedis jedis = null;
        String obj = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            obj = jedis.lpop(key);
        } catch (Exception var9) {
            logger.error("根据" + key + "获取对象的操作产生异常：", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return obj;
    }

    public boolean hput(String tableName, String key, Object object) {
        return this.hput(tableName, key, object, DEFULT_DATABASE);
    }

    public boolean hput(String tableName, String key, Object object, int dataBase) {
        Jedis jedis = null;

        boolean var7;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.hset(tableName.getBytes(), key.getBytes(), SerializeUtil.serialize(object));
            return true;
        } catch (Exception var11) {
            logger.error(tableName + "哈希表put操作产生异常：", var11);
            var7 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public boolean hset(String tableName, String key, Object object, int expire) {
        return this.hset(tableName, key, object, expire, DEFULT_DATABASE);
    }

    public boolean hset(String tableName, String key, Object object, int expire, int dataBase) {
        Jedis jedis = null;

        boolean var8;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.hset(tableName.getBytes(), key.getBytes(), SerializeUtil.serialize(object));
            if (expire > 0) {
                jedis.expire(tableName.getBytes(), expire);
            }

            return true;
        } catch (Exception var12) {
            logger.error(tableName + "哈希表hset操作产生异常", var12);
            var8 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var8;
    }

    public Object hget(String tableName, String key) {
        return this.hget(tableName, key, DEFULT_DATABASE);
    }

    public Object hget(String tableName, String key, int dataBase) {
        Jedis jedis = null;
        Object obj = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            obj = SerializeUtil.deserialize(jedis.hget(tableName.getBytes(), key.getBytes()));
        } catch (Exception var10) {
            logger.error(tableName + "哈希表get操作产生异常：", var10);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return obj;
    }

    public void hdel(String tableName, String key) {
        this.hdel(tableName, key, DEFULT_DATABASE);
    }

    public void hdel(String tableName, String key, int dataBase) {
        Jedis jedis = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.hdel(tableName.getBytes(), new byte[][]{key.getBytes()});
        } catch (Exception var9) {
            logger.error(tableName + "哈希表del操作产生异常：", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public Map<String, Object> hgetAll(String tableName) {
        return this.hgetAll(tableName, DEFULT_DATABASE);
    }

    public Map<String, Object> hgetAll(String tableName, int dataBase) {
        Jedis jedis = null;
        Hashtable map = new Hashtable();

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            Map<byte[], byte[]> map1 = jedis.hgetAll(tableName.getBytes());
            Iterator var6 = map1.entrySet().iterator();

            while(var6.hasNext()) {
                Entry<byte[], byte[]> entry = (Entry)var6.next();
                map.put(new String((byte[])entry.getKey()), SerializeUtil.deserialize((byte[])entry.getValue()));
            }
        } catch (Exception var11) {
            logger.error(tableName + "哈希表getAll操作产生异常：", var11);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return map;
    }

    public Long getHashCount(String tableName) {
        return this.getHashCount(tableName, DEFULT_DATABASE);
    }

    public Long getHashCount(String tableName, int dataBase) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            result = jedis.hlen(tableName);
        } catch (Exception var9) {
            logger.error(tableName + "哈希表len操作产生异常：", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    public long llen(String tableName) {
        return this.llen(tableName, DEFULT_DATABASE);
    }

    public long llen(String tableName, int dataBase) {
        Jedis jedis = null;
        long re = 0L;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            re = jedis.llen(tableName.getBytes());
        } catch (Exception var10) {
            logger.error(tableName + "队列len操作产生异常：", var10);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return re;
    }

    public boolean lput(String tableName, Object object) {
        return this.lput(tableName, object, DEFULT_DATABASE);
    }

    public boolean lput(String tableName, Object object, int dataBase) {
        Jedis jedis = null;

        boolean var6;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.lpush(tableName.getBytes(), new byte[][]{SerializeUtil.serialize(object)});
            return true;
        } catch (Exception var10) {
            logger.error(tableName + "队列put的操作产生异常：", var10);
            var6 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public Object get(String key) {
        return this.get(key, DEFULT_DATABASE);
    }

    public Object get(String key, int dataBase) {
        Jedis jedis = null;
        Object obj = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            obj = SerializeUtil.deserialize(jedis.get(key.getBytes()));
        } catch (Exception var9) {
            logger.error("根据" + key + "获取对象的操作产生异常：", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return obj;
    }

    public String getString(String key) {
        return this.getString(key, DEFULT_DATABASE);
    }

    public String getString(String key, int dataBase) {
        Jedis jedis = null;
        String obj = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            obj = jedis.get(key);
        } catch (Exception var9) {
            logger.error("根据" + key + "获取对象的操作产生异常：", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return obj;
    }

    public boolean set(String key, Object object) {
        return this.set(key, object, 0, DEFULT_DATABASE);
    }

    public boolean set(String key, Object object, int expire, int dataBase) {
        Jedis jedis = null;

        boolean var7;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.set(key.getBytes(), SerializeUtil.serialize(object));
            if (expire > 0) {
                jedis.expire(key.getBytes(), expire);
            }

            return true;
        } catch (Exception var11) {
            logger.error("插入key为" + key + "的操作产生异常：", var11);
            var7 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public boolean set(String key, Object object, int expire) {
        Jedis jedis = null;

        boolean var6;
        try {
            jedis = this.getJedis();
            jedis.select(DEFULT_DATABASE);
            jedis.set(key.getBytes(), SerializeUtil.serialize(object));
            if (expire > 0) {
                jedis.expire(key.getBytes(), expire);
            }

            return true;
        } catch (Exception var10) {
            logger.error("插入key为" + key + "的操作产生异常：", var10);
            var6 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public void setStringByDataBase(String key, String str, int dataBase) {
        this.setString(key, str, 0, dataBase);
    }

    public boolean setString(String key, String str) {
        return this.setString(key, str, 0, DEFULT_DATABASE);
    }

    public boolean setString(String key, String str, int expire) {
        Jedis jedis = null;

        boolean var6;
        try {
            jedis = this.getJedis();
            jedis.select(DEFULT_DATABASE);
            jedis.set(key, str);
            if (expire > 0) {
                jedis.expire(key, expire);
            }

            return true;
        } catch (Exception var10) {
            logger.error("插入key为" + key + "的操作产生异常：", var10);
            var6 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public boolean setString(String key, String str, int expire, int dataBase) {
        Jedis jedis = null;

        boolean var7;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.set(key, str);
            if (expire > 0) {
                jedis.expire(key.getBytes(), expire);
            }

            return true;
        } catch (Exception var11) {
            logger.error("插入key为" + key + "的操作产生异常：", var11);
            var7 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public void setByDataBase(String key, Object object, int dataBase) {
        this.set(key, object, 0, dataBase);
    }

    public Set<String> keys(String pre) {
        return this.keys(pre, DEFULT_DATABASE);
    }

    public Set<String> keys(String pre, int dataBase) {
        Jedis jedis = null;
        Set re = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            re = jedis.keys(pre);
        } catch (Exception var9) {
            logger.error("获取正则符合" + pre + "的所有redis键操作产生异常：", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return re;
    }

    public boolean delete(String key) {
        return this.delete(key, DEFULT_DATABASE);
    }

    public boolean delete(String key, int dataBase) {
        Jedis jedis = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.del(key.getBytes());
        } catch (Exception var8) {
            logger.error("删除key为" + key + "的操作产生异常：", var8);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return true;
    }

    public boolean deleteString(String key) {
        return this.deleteString(key, DEFULT_DATABASE);
    }

    public boolean deleteString(String key, int dataBase) {
        Jedis jedis = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.del(key);
        } catch (Exception var8) {
            logger.error("删除key为" + key + "的操作产生异常：", var8);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return true;
    }

    public Long incr(String key) {
        return this.incr(key, DEFULT_DATABASE);
    }

    public Long incr(String key, int dataBase) {
        Jedis jedis = null;
        Long incr = null;

        Long var6;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            incr = jedis.incr(key.getBytes());
            return incr;
        } catch (Exception var10) {
            logger.error("执行key为" + key + "的自增长操作产生异常：", var10);
            var6 = new Long(0L);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }


    public Long setnx(String key,int expire) {
        Jedis jedis = null;
        Long resultnx = null;

        Long var6;
        try {
            jedis = this.getJedis();
            jedis.select(DEFULT_DATABASE);
            resultnx = jedis.setnx(key.getBytes(),key.getBytes());
            logger.info("防重复提交结果"+resultnx);
            if(resultnx.equals(Long.parseLong("0"))){
                jedis.expire(key, expire);
            }
            return resultnx;
        } catch (Exception var10) {
            logger.error("执行key为" + key + "的自增长操作产生异常：", var10);
            var6 = new Long(0L);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
        return var6;
    }

    public Long incrExpire(String key, int expire) {
        return this.incrExpirev1(key, DEFULT_DATABASE,expire);
    }

    public Long incrExpirev1(String key, int dataBase,int expire) {
        Jedis jedis = null;
        Long incr = null;

        Long var6;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            incr = jedis.incr(key.getBytes());
            if(incr==1){
                jedis.expire(key, expire);
            }
            return incr;
        } catch (Exception var10) {
            logger.error("执行key为" + key + "的自增长操作产生异常：", var10);
            var6 = new Long(0L);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public Long decr(String key) {
        return this.decr(key, DEFULT_DATABASE);
    }

    public Long decr(String key, int dataBase) {
        Jedis jedis = null;
        Long decr = null;

        Long var6;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            decr = jedis.decr(key.getBytes());
            return decr;
        } catch (Exception var10) {
            logger.error("执行key为" + key + "的自减操作产生异常：", var10);
            var6 = new Long(0L);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public Long setKeyExpireTime(String key, int expire) {
        return this.setKeyExpireTime(key, expire, DEFULT_DATABASE);
    }

    public Long setKeyExpireTime(String key, int expire, int dataBase) {
        Jedis jedis = null;
        Long result = null;

        Long var7;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            result = jedis.expire(key, expire);
            return result;
        } catch (Exception var11) {
            logger.error("设置key为" + key + "的expire操作产生异常：", var11);
            var7 = new Long(0L);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Long getKeyExpireTime(String key) {
        return this.getKeyExpireTime(key, DEFULT_DATABASE);
    }

    public Long getKeyExpireTime(String key, int dataBase) {
        Jedis jedis = null;
        Long result = null;

        Long var6;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            result = jedis.ttl(key);
            return result;
        } catch (Exception var10) {
            logger.error("获取key为" + key + "的操作产生异常：", var10);
            var6 = new Long(0L);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public void hashcounter(String tableName, String key, int number) {
        this.hashcounter(tableName, key, number, number);
    }

    public void hashcounter(String tableName, String key, int number, int dataBase) {
        Jedis jedis = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.hincrBy(tableName.getBytes(), key.getBytes(), (long)number);
        } catch (Exception var10) {
            logger.error(tableName + "哈希表计数操作产生异常：", var10);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public void delLock(String key) {
        this.delete(key, DEFULT_DATABASE);
    }

    public void delLock(String key, int dataBase) {
        Jedis jedis = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            long oldtime = Long.parseLong(jedis.get(key));
            long current = (new Date()).getTime();
            if (current < oldtime) {
                jedis.del(key);
            }
        } catch (Exception var11) {
            logger.error("key:" + key + " 释放同步锁异常：", var11);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public int getLock(String key, long timeout) {
        return this.getLock(key, timeout, DEFULT_DATABASE);
    }

    public int getLock(String key, long timeout, int dataBase) {
        Jedis jedis = null;

        byte var12;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            long now = (new Date()).getTime();
            long timestamp = now + timeout;
            long lock = jedis.setnx(key, String.valueOf(timestamp));
            if (lock != 1L && (now <= Long.parseLong(jedis.get(key)) || now <= Long.parseLong(jedis.getSet(key, String.valueOf(timestamp))))) {
                var12 = 0;
                return var12;
            }

            var12 = 1;
        } catch (Exception var16) {
            logger.error("key:" + key + " 获得同步锁异常：", var16);
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var12;
    }

    public Set<String> hkeys(String tableName) {
        return this.hkeys(tableName, DEFULT_DATABASE);
    }

    public Set<String> hkeys(String tableName, int dataBase) {
        Jedis jedis = null;
        HashSet keys = new HashSet();

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            Set<byte[]> keysbytes = jedis.hkeys(tableName.getBytes());
            Iterator var6 = keysbytes.iterator();

            while(var6.hasNext()) {
                byte[] b = (byte[])var6.next();
                if (b.length != 0) {
                    keys.add(new String(b));
                }
            }
        } catch (Exception var11) {
            logger.error(tableName + "哈希表del操作产生异常", var11);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return keys;
    }

    public boolean exists(String key) {
        return this.exists(key, DEFULT_DATABASE);
    }

    public boolean exists(String key, int dataBase) {
        Jedis jedis = null;
        boolean flag = false;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            flag = jedis.exists(key);
        } catch (Exception var9) {
            logger.error("判断key是否存在操作产生异常", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return flag;
    }

    public Long incrBy(String key, long integer) {
        return this.incrBy(key, integer, DEFULT_DATABASE);
    }

    public Long incrBy(String key, long integer, int dataBase) {
        Jedis jedis = null;
        long result = 0L;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            result = jedis.incrBy(key, integer);
            Long var8 = result;
            return var8;
        } catch (Exception var12) {
            logger.error("类型错误", var12);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    public boolean sadd(String key, List<String> values) {
        return this.sadd(key, values, 0, DEFULT_DATABASE);
    }

    public boolean sadd(String key, List<String> values, int expire, int dataBase) {
        Jedis jedis = null;

        boolean var7;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.sadd(key, values.toArray(new String[values.size()]));
            if (expire > 0) {
                jedis.expire(key.getBytes(), expire);
            }

            return true;
        } catch (Exception var11) {
            logger.error("插入key为" + key + "的操作产生异常：", var11);
            var7 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public boolean sadd(String key, List<String> values, int expire) {
        Jedis jedis = null;

        boolean var6;
        try {
            jedis = this.getJedis();
            jedis.select(DEFULT_DATABASE);
            jedis.sadd(key, values.toArray(new String[values.size()]));
            if (expire > 0) {
                jedis.expire(key.getBytes(), expire);
            }

            return true;
        } catch (Exception var10) {
            logger.error("插入key为" + key + "的操作产生异常：", var10);
            var6 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public boolean sismember(String key, String field) {
        return this.sismember(key, field, DEFULT_DATABASE);
    }

    public boolean sismember(String key, String field, int dataBase) {
        Jedis jedis = null;
        boolean flag = false;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            flag = jedis.sismember(key, field);
        } catch (Exception var9) {
            logger.error("判断key中是否存在"+field+"存在操作产生异常", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return flag;
    }

    public Set<String> smembers(String key) {
        return this.smembers(key, DEFULT_DATABASE);
    }

    public Set<String> smembers(String key, int dataBase) {
        Jedis jedis = null;
        Set<String> smembers = null;

        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            smembers = jedis.smembers(key);
        } catch (Exception var9) {
            logger.error("查询集合key为"+key+"的元素smembers产生异常", var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return smembers;
    }


    public boolean srem(String key, List<String> values) {
        return this.srem(key, values, 0, DEFULT_DATABASE);
    }

    public boolean srem(String key, List<String> values, int expire, int dataBase) {
        Jedis jedis = null;

        boolean var7;
        try {
            jedis = this.getJedis();
            jedis.select(dataBase);
            jedis.srem(key, values.toArray(new String[values.size()]));
            if (expire > 0) {
                jedis.expire(key.getBytes(), expire);
            }

            return true;
        } catch (Exception var11) {
            logger.error("srem异常, key:{},values:{},exception:{}", key, values, var11);
            var7 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public boolean srem(String key, List<String> values, int expire) {
        Jedis jedis = null;

        boolean var6;
        try {
            jedis = this.getJedis();
            jedis.select(DEFULT_DATABASE);
            jedis.srem(key, values.toArray(new String[values.size()]));
            if (expire > 0) {
                jedis.expire(key.getBytes(), expire);
            }

            return true;
        } catch (Exception var10) {
            logger.error("srem异常, key:{},values:{},exception:{}", key, values, var10);
            var6 = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    static {
        DEFULT_DATABASE = RedisDataBaseEnum.DATABASE_1.getId();
    }
}

