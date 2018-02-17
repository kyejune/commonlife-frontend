package com.kolon.common.helper;

import java.util.HashSet;
import java.util.Set;

import com.kolon.common.prop.SystemPropertiesMap;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHelper {

    // CommonLife 개발
//    protected static final String REDIS_HOST = "cl-dev-redis.sktb1d.0001.apn2.cache.amazonaws.com";

    protected static final String REDIS_HOST = "127.0.0.1";
	// 개발
//	//protected static final String REDIS_HOST = "token-managements.3j2bzt.0001.apn2.cache.amazonaws.com";
//    protected static final String REDIS_HOST = "iok-info-redis.g4s72o.0001.apn2.cache.amazonaws.com";
    
    // 운영
//    protected static final String REDIS_HOST = "token-manage.3j2bzt.clustercfg.apn2.cache.amazonaws.com";
    
 // 운영
//    protected static final String REDIS_HOST = "prod-token-manage.3j2bzt.clustercfg.apn2.cache.amazonaws.com";

    	
    protected static final int REDIS_PORT = 6379;
    private final Set<Jedis> connectionList = new HashSet<Jedis>();
    private final JedisPool pool;

    /**
     * 제디스 연결풀 생성을 위한 도우미 클래스 내부 생성자. 싱글톤 패턴이므로 외부에서 호출할 수 없다.
     */
    private JedisHelper() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        
        // PRD
        config.setMaxTotal(25);
        
        //DEV
//        config.setMaxTotal(50);
        config.setBlockWhenExhausted(true);

        String redisHost = SystemPropertiesMap.getInstance().getValue( "redis.host");
        int redisPort = Integer.parseInt( SystemPropertiesMap.getInstance().getValue( "redis.port") );

        this.pool = new JedisPool(config, redisHost, redisPort);
    }

    /**
     * 싱글톤 처리를 위한 홀더 클래스, 제디스 연결풀이 포함된 도우미 객체를 반환한다.
     */
    private static class LazyHolder {
        @SuppressWarnings("synthetic-access")
        private static final JedisHelper INSTANCE = new JedisHelper();
    }

    /**
     * 싱글톤 객체를 가져온다.
     * 
     * @return 제디스 도우미객체
     */
    @SuppressWarnings("synthetic-access")
    public static JedisHelper getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * 제디스 클라이언트 연결을 가져온다.
     * 
     * @return 제디스 객체
     */
    final public Jedis getConnection() {
        Jedis jedis = this.pool.getResource();
        this.connectionList.add(jedis);

        return jedis;
    }

    /**
     * 사용이 완료된 제디스 객체를 회수한다.
     * 
     * @param jedis
     *            사용 완료된 제디스 객체
     */
    final public void returnResource(Jedis jedis) {
        this.pool.returnResource(jedis);
    }

    /**
     * 제디스 연결풀을 제거한다.
     */
    final public void destoryPool() {
//        Iterator<Jedis> jedisList = this.connectionList.iterator();
//        while (jedisList.hasNext()) {
//            Jedis jedis = jedisList.next();
//            this.pool.returnResource(jedis);
//        }

        this.pool.close();

        this.pool.destroy();
    }
}