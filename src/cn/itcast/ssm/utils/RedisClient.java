package cn.itcast.ssm.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.ItemsCustom;

import redis.clients.jedis.Jedis;

public class RedisClient {

	private static Logger logger = Logger.getLogger(RedisClient.class);
	
	private static String IP_ADDR = "172.29.12.250";
	
	private static String PASSWORD = "platform"; 
	 
	private static Integer PORT = 6379; 
	
	private static Integer redis_dbindex=1;
	
	/**
	 * redis过期时间,以秒为单位
	 */
	public final static int EXRP_90S = 90;  //90秒
	public final static int EXRP_HOUR = 60 * 60; // 一小时
	public final static int EXRP_DAY = 60 * 60 * 24; // 一天
	public final static int EXRP_MONTH = 60 * 60 * 24 * 30; // 一个月
	
	/**
	 * 获取Jedis实例
	 * @return
	 */
	public static Jedis getJedis() {
		Jedis jedis = new Jedis(IP_ADDR, PORT);			
		jedis.auth(PASSWORD);
		jedis.select(redis_dbindex);
		return jedis;
	}
	
	/**
	 * 释放资源
	 * @param jedis
	 */
	public static void releaseResource(Jedis jedis) {
		if(jedis != null) {
			jedis.quit();
		}		
	}
	
	/**
	 * 设置字符串型值
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void setStr(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			 jedis = getJedis();
			jedis.setex(key, seconds, value);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			releaseResource(jedis);
		}
	}
	
	/**
	 * 获取字符串型值
	 * @param key
	 * @return
	 */
	public static String getStr(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			releaseResource(jedis);
		}
		return value;		
	}
	
	/**
	 * 清除redis中key
	 * @param key
	 * @return
	 */
	public static long del(String key) {
		Jedis jedis = null;		
		try {
			jedis = getJedis();
			logger.info("清除Redis中key为：" + key);
			return jedis.del(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);		
			return 0;
		} finally {
			releaseResource(jedis);
		}
	}
	
	public static void setHsh(String key, Map<String, String> map, int seconds) {		
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
			jedis.hmset(key, map);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);			
		} finally {
			releaseResource(jedis);
		}		
	}
	
	/*
	 * 设置dto
	 */
	public static boolean set(String key, Object dto, int expiredTime) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			byte[] dtoBytes = ObjectUtil.objToBytes(dto);
			byte[] keyBytes = key.getBytes();
			String x = jedis.set(keyBytes, dtoBytes);
			if(x.equalsIgnoreCase("ok")) {
				jedis.expire(keyBytes, expiredTime);
				return true;
			}
		} catch (Exception e) {			
			e.printStackTrace();			
		} finally {
			releaseResource(jedis);
		}
		return false;
	}
	
	public static Object getAsDTO(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			byte[] dtoBytes = jedis.get(key.getBytes());
			if(dtoBytes == null) {
				return null;
			}
			Object dto = ObjectUtil.bytesToObj(dtoBytes);		
			return dto;
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		} finally {
			releaseResource(jedis);
		}		
	}
	public static Map<String,String> getHshAll(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			releaseResource(jedis);
		}		
	}
	public static void main(String[] args) {
//		RedisClient.setStr("15972157719", "lxj", EXRP_HOUR);
//		Map<String, String> map = new HashMap<>();
//		map.put("a", "abc");
//		RedisClient.setHsh("15972157719", map, EXRP_HOUR);
//		System.out.println(RedisClient.getHshAll("15972157719"));
//		System.out.println(RedisClient.getStr("15902735750"));
		
		Items items = new Items();
		items.setId(1);
		items.setName("测试redis存dto");
		items.setCreatetime(new Date());
		set("1", items, EXRP_HOUR);
		Items result = (Items) getAsDTO("1");
		System.out.println(result);		
	}
	
	
	
	
	 /**
     * 存储REDIS队列 顺序存储
     * @param  key reids键名
     * @param  value 键值
     */
    public static void lpush(byte[] key, byte[] value) {

        Jedis jedis = null;
        try {
        	jedis = getJedis();
            jedis.lpush(key, value);
        } catch (Exception e) {            
        	logger.error(e.getMessage(), e);
        } finally {
            //释放
        	releaseResource(jedis);
        }
    }
	
    /**
     * 获取队列数据
     * @param  key 键名
     * @return
     */
    public static byte[] lpop(byte[] key) {

        byte[] bytes = null;
        Jedis jedis = null;
        try {

			jedis = getJedis();
            bytes = jedis.lpop(key);            

        } catch (Exception e) {

            //释放redis对象
            logger.error(e.getMessage(), e);
            e.printStackTrace();

        } finally {

            
            releaseResource(jedis);

        }
        return bytes;
    }
	
    
    /**
     * 存储REDIS队列 反向存储
     * @param  key reids键名
     * @param  value 键值
     */
    public static void rpush(byte[] key, byte[] value) {

        Jedis jedis = null;
        try {

        	jedis = getJedis();
            jedis.rpush(key, value);

        } catch (Exception e) {

            //释放redis对象
            logger.error(e.getMessage(), e);
            e.printStackTrace();

        } finally {

            
            releaseResource(jedis);

        }
    }

    /**
     * 从第一个 list 的尾部移除元素并添加到第二个 list 的头部,最后返回被移除的元素值，整个操
作是原子的.如果第一个 list 是空或者不存在返回 null
     * @param  key reids键名
     * @param  destination 目标list的键名
     */
    public static void rpoplpush(byte[] key, byte[] destination) {

        Jedis jedis = null;
        try {

        	jedis = getJedis();
            jedis.rpoplpush(key, destination);

        } catch (Exception e) {
            //释放redis对象
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {            
            releaseResource(jedis);
        }
    }

    /**
     * 获取队列数据
     * @param  key 键名
     * @return
     */
    public static List lpopList(byte[] key) {

        List list = null;
        Jedis jedis = null;
        try {

        	jedis = getJedis();
            list = jedis.lrange(key, 0, -1);

        } catch (Exception e) {

            //释放redis对象
            logger.error(e.getMessage(), e);
            e.printStackTrace();

        } finally {

            
            releaseResource(jedis);

        }
        return list;
    }
    /**
     * 获取队列数据
     * @param  key 键名
     * @return
     */
    public static byte[] rpop(byte[] key) {

        byte[] bytes = null;
        Jedis jedis = null;
        try {

			jedis = getJedis();
            bytes = jedis.rpop(key);            

        } catch (Exception e) {

            //释放redis对象
            logger.error(e.getMessage(), e);
            e.printStackTrace();

        } finally {

            
            releaseResource(jedis);

        }
        return bytes;
    }
    
    /**
     * 
     * @param key
     * @param from   start 位置 0起始（左边第一个）， -1倒数第一个，-2倒数第二个
     * @param to
     * @return
     */
    public static List lrange(byte[] key, int from, int to) {
        List result = null;
        Jedis jedis = null;
        try {
        	jedis = getJedis();
            result = jedis.lrange(key, from, to);

        } catch (Exception e) {                    
            logger.error(e.getMessage(), e);            
        } finally {
        	releaseResource(jedis);
        }
        return result;
    }
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	public static String clearTailZero(double source) {
		int intSource = (int) source;
		if(source == intSource) {
			return String.valueOf(intSource);
		} else {
			return String.valueOf(source);
		}
	}
	
	
	
}
