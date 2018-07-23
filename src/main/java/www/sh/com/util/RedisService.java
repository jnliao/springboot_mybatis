package www.sh.com.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 */
@Service
public class RedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);
    private static JedisPool pool = null;
    
    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;
    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;
    
    @Autowired
    public RedisService(@Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") Integer port,
                        @Value("${spring.redis.password}") String password, @Value("${spring.redis.database}") Integer database) {
    	if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(10000);
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(2000);
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            config.setTestOnBorrow(true);
            //pool = new JedisPool(config, host, port, 100000);
            if(StringUtils.isBlank(password)){
                password = null;
            }
            pool = new JedisPool(config,host,port,100000,password,database);
        }
    	
	}

	/**
     * <p>通过key获取储存在redis中的value</p>
     * <p>并释放连接</p>
     * @param key
     * @return 成功返回value 失败返回null
     */
    public String get(String key){
        Jedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * <p>向redis存入key和value,并释放连接资源</p>
     * <p>如果key已经存在 则覆盖</p>
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
            return "0";
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    /**
     * <p>向redis存入key和value,并释放连接资源</p>
     * <p>如果key已经存在 则覆盖</p>
     * @return 成功 返回OK 失败返回 0
     */
    public List<String> scan(String cursor, ScanParams params){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            int i = 0;
            
            ScanResult<String> result = jedis.scan(cursor, params);
            List<String> list = new ArrayList<String>();
            while(true){
            	i++;
            	if(result.getResult()!=null && result.getResult().size()>0){
            		list.addAll(result.getResult());
            		if(i>100){// 100
            			return list;
            		}else if(result.getStringCursor().equals("0")){
                		return list;
                	}
                }
            	if(i<=100){
            		result = jedis.scan(result.getStringCursor(), params);
            	}
            }
        } catch (Exception e) {
           return null;
        } finally {
            returnResource(pool, jedis);
        }
    }


    /**
     * <p>删除指定的key,也可以传入一个包含key的数组</p>
     * @param keys 一个key  也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(String...keys){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    /**
     * <p>通过key向指定的value值追加值</p>
     * @param key
     * @param str
     * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度  异常返回0L
     */
    public Long append(String key ,String str){
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.append(key, str);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>判断key是否存在</p>
     * @param key
     * @return true OR false
     */
    public Boolean exists(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
            return false;
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * <p>设置key value,如果key已经存在则返回0,nx==> not exist</p>
     * @param key
     * @param value
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long setnx(String key ,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * setnx超时设置
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public Long setnx(String key, String value, int seconds){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long result = jedis.setnx(key, value);
            jedis.expire(key, seconds);
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * <p>设置key value并制定这个键值的有效期</p>
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return 成功返回OK 失败和异常返回null
     */
    public String setex(String key,String value,int seconds){
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key给field设置指定的值,如果key不存在,则先创建</p>
     * @param key
     * @param field 字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    public Long hset(String key,String field,String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hset(key, field, value);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0</p>
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key,String field,String value){
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key 和 field 获取指定的 value</p>
     * @param key
     * @param field
     * @return 没有返回null
     */
    public String hget(String key, String field){
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hget(key, field);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key 和 fields 获取指定的value 如果没有对应的value则返回null</p>
     * @param key
     * @param fields 可以使 一个String 也可以是 String数组
     * @return
     */
    public List<String> hmget(String key,String...fields){
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hmget(key, fields);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }


    /**
     * <p>通过key和field判断是否有指定的value存在</p>
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key , String field){
        Jedis jedis = null;
        Boolean res = false;
        try {
            jedis = pool.getResource();
            res = jedis.hexists(key, field);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回field的数量</p>
     * @param key
     * @return
     */
    public Long hlen(String key){
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hlen(key);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;

    }

    /**
     * <p>通过key 删除指定的 field </p>
     * @param key
     * @param fields 可以是 一个 field 也可以是 一个数组
     * @return
     */
    public Long hdel(String key ,String...fields){
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hdel(key, fields);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回所有的field</p>
     * @param key
     * @return
     */
    public Set<String> hkeys(String key){
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hkeys(key);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回所有和key有关的value</p>
     * @param key
     * @return
     */
    public List<String> hvals(String key){
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hvals(key);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取所有的field和value</p>
     * @param key
     * @return
     */
    public Map<String, String> hgetall(String key){
        Jedis jedis = null;
        Map<String, String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hgetAll(key);
        } catch (Exception e) {
            // TODO
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    public <T> T hgetall(String key, Class<T> clazz){
        Jedis jedis = null;
        Map<String, String> res = null;
        T object = null;
        try {
            jedis = pool.getResource();
            res = jedis.hgetAll(key);
            if (res != null) {
                object = JSON.parseObject(JSON.toJSONString(res), clazz);
            }
        } catch (Exception e) {
            // TODO
        } finally {
            returnResource(pool, jedis);
        }
        return object;
    }


    /**
     * <p>返回满足pattern表达式的所有key</p>
     * <p>keys(*)</p>
     * <p>返回所有的key</p>
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.keys(pattern);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key判断值得类型</p>
     * @param key
     * @return
     */
    public String type(String key){
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.type(key);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }
    
    /**
     * <p>通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1</p>
     * @param key
     * @return 加值后的结果
     */
    public Long incr(String key){
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.incr(key);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    public Long incrTTL(String key, Integer seconds){
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            if (!jedis.exists(key)){
                jedis.setex(key, seconds, "0");
            }
            res = jedis.incr(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>通过key给指定的value加值,如果key不存在,则这是value为该值</p>
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key,Long integer){
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.incrBy(key, integer);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>对key的值做减减操作,如果key不存在,则设置key为-1</p>
     * @param key
     * @return
     */
    public Long decr(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.decr(key);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }
    
    /**
     * <p>返回list队列内容</p>
     * @param key
     * @return
     */
    public List<byte[]> lrange(byte[] key,long start,long end) {
        Jedis jedis = null;
        List<byte[]> list = null;
        try {
            jedis = pool.getResource();
            list = jedis.lrange(key, start, end);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return list;
    }
    
    /**
     * <p>返回list队列内容</p>
     * @param key
     * @return
     */
    public List<String> lrange(String key,long start,long end) {
        Jedis jedis = null;
        List<String> list = null;
        try {
            jedis = pool.getResource();
            list = jedis.lrange(key, start, end);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return list;
    }
    
    /**
     * <p>清空list队列内容</p>
     * @param key
     * @return
     */
    public String ltrim(String key,long start,long end) {
        Jedis jedis = null;
        String status = null;
        try {
            jedis = pool.getResource();
            status = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return status;
    }

    /**
     * <p>减去指定的值</p>
     * @param key
     * @param integer
     * @return
     */
    public Long decrBy(String key,Long integer){
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.decrBy(key, integer);
        } catch (Exception e) {
            
            LOGGER.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
	 * 存储REDIS队列 顺序存储
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            对象
	 */
	public void lpush(String key, Object value) {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.lpush(key.getBytes(), SerializeUtils.serialize(value));
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
	}
	public void lpush(String key, String value) {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
	}
	
	public Long lrem(String key, String value, Long count) {
		Jedis jedis = null;
		Long lrem = 0L;
        try {
            jedis = pool.getResource();
            lrem = jedis.lrem(key, count, value);
            return lrem;
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return lrem;
	}

	/**
	 * 获取队列里面第一个对象
	 * 
	 * @param key
	 * @return
	 */
	public Object rpop(String key) {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return SerializeUtils.unSerialize(jedis.rpop(key.getBytes()));
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return null;
	}
	
	/**
	 * 
	 * 获取队列里面第一个对象并放到队尾
	 * @param key
	 * @return
	 */
	public Object rpoplpush(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return SerializeUtils.unSerialize(jedis.rpoplpush(key.getBytes(), key.getBytes()));
		} finally {
			returnResource(pool, jedis);
		}
	}

	/**
	 * 存储REDIS队列 反向存储
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            对象
	 */
	public void rpush(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.lpush(key.getBytes(), SerializeUtils.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
	}

	/**
	 * 获取队列里面最后一个对象
	 * 
	 * @param key
	 * @return
	 */
	public Object lpop(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return SerializeUtils.unSerialize(jedis.lpop(key.getBytes()));
		} finally {
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 获取队列里面最后一个对象
	 * 
	 * @param key
	 * @return
	 */
	public String lindex(String key,long index) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lindex(key, index);
		} finally {
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 像set集合中添加一个元素
	 * 
	 * @param key
	 * @return
	 */
	public Long sadd(String key,String... members) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sadd(key, members);
		} finally {
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 像set集合中添加一个元素
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.smembers(key);
		} finally {
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 像set集合中添加一个元素
	 * 
	 * @param key
	 * @return
	 */
	public void zadd(String key,double score,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.zadd(key.getBytes(), score, value.getBytes());
		} finally {
			returnResource(pool, jedis);
		}
	}
    /**
     * 移除set集合中指定的元素
     *
     * @param key
     * @return
     */
    public void zrem(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.zrem(key.getBytes(), value.getBytes());
        } finally {
            returnResource(pool, jedis);
        }
    }

	/**
	 * set集合移除元素
	 */
	public Long zrem(String key,String...members) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			Long zrem = jedis.zrem(key, members);
			return zrem;
		} finally {
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 获取set中指定个数元素
	 */
	public Object zrange(String key,long start,long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrange(key, start, end);
		} finally {
			returnResource(pool, jedis);
		}
	}
	/**
	 * 获取set中指定个数元素
	 */
	public Set<String> zrevrange(String key,long start,long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrange(key, start, end);
		} finally {
			returnResource(pool, jedis);
		}
	}
	/**
	 * 根据score移除元素
	 */
	public Long zremrangeByScore(String key,String start,String end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zremrangeByScore(key, start, end);
		} finally {
			returnResource(pool, jedis);
		}
	}
	/**
	 * 增加score
	 */
	public static void zincrby(String key,double score,String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.zincrby(key, score, member);
		} finally {
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 增加score
	 */
	public Long expire(String key,int seconds) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = pool.getResource();
			result = jedis.expire(key, seconds);
		} finally {
			returnResource(pool, jedis);
		}
		return result;
	}
	
	public String getSet(final String key, final String value) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.getSet(key, value);
		} finally {
			returnResource(pool, jedis);
		}
		return result;
		
    }
    
    /**
     * 返还到连接池
     */
    public static void returnResource(JedisPool pool, Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

    public synchronized boolean lock(String lockKey) throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间
            if (this.setnx(lockKey, expiresStr).equals(Long.valueOf("1"))) {
                // lock acquired
                locked = true;
                return true;
            }
            // redis里的时间
            String currentValueStr = this.get(lockKey); 
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                // lock is expired

                String oldValueStr = this.getSet(lockKey, expiresStr);
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受

                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;

            /*
                延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                使用随机的等待时间可以一定程度上保证公平性
             */
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);

        }
        return false;
    }

    public boolean lockFast(String lockKey) throws InterruptedException {
            if (this.setnx(lockKey, "1").equals(1L)) {
                this.expire(lockKey, 600);
                return true;
            }
        return false;
    }
    
    /**
     * Acqurired lock release.
     */
    public synchronized void unlock(String lockKey) {
        if (locked) {
            this.del(lockKey);
            locked = false;
        }
    }
    
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxTotal(10000);
        // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(2000);
        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(1000 * 100);
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "192.168.233.129", 6379, 100000);

        try {
            Jedis jedis = pool.getResource();
            jedis.set("test","test666");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}