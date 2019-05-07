package com.mistra.userservice.core.redis;

import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 11:39
 * @ Description:
 */
public class JedisWapper implements JedisCommands {

    JedisPool jedisPool;

    public JedisWapper(JedisPool pool) {
        this.jedisPool = pool;
    }

    @Override
    public String set(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(var1, var2);
        }
    }

    @Override
    public String set(String var1, String var2, String var3, String var4, long var5) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public String get(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(var1);
        }
    }

    @Override
    public Boolean exists(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(var1);
        }
    }

    @Override
    public Long persist(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.persist(var1);

        }
    }

    @Override
    public String type(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.type(var1);
        }
    }

    @Override
    public Long expire(String var1, int var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.expire(var1, var2);
        }
    }

    @Override
    public Long pexpire(String var1, long var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.pexpire(var1, var2);
        }
    }

    @Override
    public Long expireAt(String var1, long var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.expireAt(var1, var2);
        }
    }

    @Override
    public Long pexpireAt(String var1, long var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.pexpireAt(var1, var2);
        }
    }

    @Override
    public Long ttl(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.ttl(var1);
        }
    }

    @Override
    public Boolean setbit(String var1, long var2, boolean var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setbit(var1, var2, var4);
        }
    }

    @Override
    public Boolean setbit(String var1, long var2, String var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setbit(var1, var2, var4);
        }
    }

    @Override
    public Boolean getbit(String var1, long var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.getbit(var1, var2);
        }
    }

    @Override
    public Long setrange(String var1, long var2, String var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setrange(var1, var2, var4);
        }
    }

    @Override
    public String getrange(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.getrange(var1, var2, var4);
        }
    }

    @Override
    public String getSet(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.getSet(var1, var2);
        }
    }

    @Override
    public Long setnx(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setnx(var1, var2);
        }
    }

    @Override
    public String setex(String var1, int var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setex(var1, var2, var3);
        }
    }

    @Override
    public Long decrBy(String var1, long var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.decrBy(var1, var2);
        }
    }

    @Override
    public Long decr(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.decr(var1);
        }
    }

    @Override
    public Long incrBy(String var1, long var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incrBy(var1, var2);
        }
    }

    @Override
    public Double incrByFloat(String var1, double var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incrByFloat(var1, var2);
        }
    }

    @Override
    public Long incr(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incr(var1);
        }
    }

    @Override
    public Long append(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.append(var1, var2);
        }
    }

    @Override
    public String substr(String var1, int var2, int var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.substr(var1, var2, var3);
        }
    }

    @Override
    public Long hset(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hset(var1, var2, var3);
        }
    }

    @Override
    public String hget(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget(var1, var2);
        }
    }

    @Override
    public Long hsetnx(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hsetnx(var1, var2, var3);
        }
    }

    @Override
    public String hmset(String s, Map<String, String> map) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hmset(s, map);
        }
    }

    @Override
    public List<String> hmget(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hmget(var1, var2);
        }
    }

    @Override
    public Long hincrBy(String var1, String var2, long var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hincrBy(var1, var2, var3);
        }
    }

    @Override
    public Boolean hexists(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hexists(var1, var2);
        }
    }

    @Override
    public Long hdel(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hdel(var1, var2);
        }
    }

    @Override
    public Long hlen(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hlen(var1);
        }
    }

    @Override
    public Set<String> hkeys(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hkeys(var1);
        }
    }

    @Override
    public List<String> hvals(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hvals(var1);
        }
    }

    @Override
    public Map<String, String> hgetAll(String s) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(s);
        }
    }

    @Override
    public Long rpush(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.rpush(var1, var2);
        }
    }

    @Override
    public Long lpush(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpush(var1, var2);
        }
    }

    @Override
    public Long llen(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.llen(var1);
        }
    }

    @Override
    public List<String> lrange(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(var1, var2, var4);
        }
    }

    @Override
    public String ltrim(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.ltrim(var1, var2, var4);
        }
    }

    @Override
    public String lindex(String var1, long var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lindex(var1, var2);
        }
    }

    @Override
    public String lset(String var1, long var2, String var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lset(var1, var2, var4);
        }
    }

    @Override
    public Long lrem(String var1, long var2, String var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrem(var1, var2, var4);
        }
    }

    @Override
    public String lpop(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpop(var1);
        }
    }

    @Override
    public String rpop(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.rpop(var1);
        }
    }

    @Override
    public Long sadd(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sadd(var1, var2);
        }
    }

    @Override
    public Set<String> smembers(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.smembers(var1);
        }
    }

    @Override
    public Long srem(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srem(var1, var2);
        }
    }

    @Override
    public String spop(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.spop(var1);
        }
    }

    @Override
    public Set<String> spop(String var1, long var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.spop(var1, var2);
        }
    }

    @Override
    public Long scard(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.scard(var1);
        }
    }

    @Override
    public Boolean sismember(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sismember(var1, var2);
        }
    }

    @Override
    public String srandmember(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srandmember(var1);
        }
    }

    @Override
    public List<String> srandmember(String var1, int var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srandmember(var1, var2);
        }
    }

    @Override
    public Long strlen(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.strlen(var1);
        }
    }

    @Override
    public Long zadd(String var1, double var2, String var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(var1, var2, var4);
        }
    }

    @Override
    public Long zadd(String s, Map<String, Double> map) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(s, map);
        }
    }

    @Override
    public Set<String> zrange(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrange(var1, var2, var4);
        }
    }

    @Override
    public Long zrem(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrem(var1, var2);
        }
    }

    @Override
    public Double zincrby(String var1, double var2, String var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zincrby(var1, var2, var4);
        }
    }

    @Override
    public Long zrank(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrank(var1, var2);
        }
    }

    @Override
    public Long zrevrank(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrank(var1, var2);
        }
    }

    @Override
    public Set<String> zrevrange(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrange(var1, var2, var4);
        }
    }

    @Override
    public Set<Tuple> zrangeWithScores(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeWithScores(var1, var2, var4);
        }
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeWithScores(var1, var2, var4);
        }
    }

    @Override
    public Long zcard(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcard(var1);
        }
    }

    @Override
    public Double zscore(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zscore(var1, var2);
        }
    }

    @Override
    public List<String> sort(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sort(var1);
        }
    }

    @Override
    public List<String> sort(String var1, SortingParams var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sort(var1, var2);
        }
    }

    @Override
    public Long zcount(String var1, double var2, double var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcount(var1, var2, var4);
        }
    }

    @Override
    public Long zcount(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcount(var1, var2, var3);
        }
    }

    @Override
    public Set<String> zrangeByScore(String var1, double var2, double var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScore(var1, var2, var4);
        }
    }

    @Override
    public Set<String> zrangeByScore(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScore(var1, var2, var3);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String var1, double var2, double var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScore(var1, var2, var4);
        }
    }

    @Override
    public Set<String> zrangeByScore(String var1, double var2, double var4, int var6, int var7) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScore(var1, var2, var4, var6, var7);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScore(var1, var2, var3);
        }
    }

    @Override
    public Set<String> zrangeByScore(String var1, String var2, String var3, int var4, int var5) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScore(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String var1, double var2, double var4, int var6, int var7) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScore(var1, var2, var4, var6, var7);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String var1, double var2, double var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScoreWithScores(var1, var2, var4);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String var1, double var2, double var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScoreWithScores(var1, var2, var4);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String var1, double var2, double var4, int var6, int var7) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScoreWithScores(var1, var2, var4, var6, var7);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String var1, String var2, String var3, int var4, int var5) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScore(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScoreWithScores(var1, var2, var3);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScoreWithScores(var1, var2, var3);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String var1, String var2, String var3, int var4, int var5) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScoreWithScores(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String var1, double var2, double var4, int var6, int var7) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScoreWithScores(var1, var2, var4, var6, var7);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String var1, String var2, String var3, int var4, int var5) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScoreWithScores(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public Long zremrangeByRank(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zremrangeByRank(var1, var2, var4);
        }
    }

    @Override
    public Long zremrangeByScore(String var1, double var2, double var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zremrangeByScore(var1, var2, var4);
        }
    }

    @Override
    public Long zremrangeByScore(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zremrangeByScore(var1, var2, var3);
        }
    }

    @Override
    public Long zlexcount(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zlexcount(var1, var2, var3);
        }
    }

    @Override
    public Set<String> zrangeByLex(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByLex(var1, var2, var3);
        }
    }

    @Override
    public Set<String> zrangeByLex(String var1, String var2, String var3, int var4, int var5) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByLex(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public Set<String> zrevrangeByLex(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByLex(var1, var2, var3);
        }
    }

    @Override
    public Set<String> zrevrangeByLex(String var1, String var2, String var3, int var4, int var5) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByLex(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public Long zremrangeByLex(String var1, String var2, String var3) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zremrangeByLex(var1, var2, var3);
        }
    }

    @Override
    public Long linsert(String s, BinaryClient.LIST_POSITION list_position, String s1, String s2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.linsert(s, list_position, s1, s2);
        }
    }


    @Override
    public Long lpushx(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpushx(var1, var2);
        }
    }

    @Override
    public Long rpushx(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.rpushx(var1, var2);
        }
    }

    @Override
    public List<String> blpop(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.blpop(var1);
        }
    }

    @Override
    public List<String> blpop(int var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.blpop(var1, var2);
        }
    }

    @Override
    public List<String> brpop(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.brpop(var1);
        }
    }

    @Override
    public List<String> brpop(int var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.brpop(var1, var2);
        }
    }

    @Override
    public Long del(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(var1);
        }
    }

    @Override
    public String echo(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.echo(var1);
        }
    }

    @Override
    public Long move(String var1, int var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.move(var1, var2);
        }
    }

    @Override
    public Long bitcount(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.bitcount(var1);
        }
    }

    @Override
    public Long bitcount(String var1, long var2, long var4) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.bitcount(var1, var2, var4);
        }
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String var1, int var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hscan(var1, var2);
        }
    }

    @Override
    public ScanResult<String> sscan(String var1, int var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sscan(var1, var2);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String var1, int var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zscan(var1, var2);
        }
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String s, String s1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hscan(s, s1);
        }
    }

    @Override
    public ScanResult<String> sscan(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sscan(var1, var2);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String var1, String var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zscan(var1, var2);
        }
    }

    @Override
    public Long pfadd(String var1, String... var2) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.pfadd(var1, var2);
        }
    }

    @Override
    public long pfcount(String var1) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.pfcount(var1);
        }
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }
}
