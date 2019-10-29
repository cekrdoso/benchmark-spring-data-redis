package hudsonmendes.lab.springDataRedis;

import redis.clients.jedis.JedisPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ConfigForRedis {

	@Value("${redis.host:redis-sentinel}")
	private String _sentinelHostName;

	@Value("${redis.port:26379}")
	private Integer _sentinelPort;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(this.redisSentinelConfiguration(),
				this.jedisPoolConfig());
		return jedisConnectionFactory;
	}

	@Bean
	public RedisSentinelConfiguration redisSentinelConfiguration() {
		return new RedisSentinelConfiguration()
					.master("mymaster")
					.sentinel(_sentinelHostName, _sentinelPort);
	}

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(5);
		jedisPoolConfig.setMaxIdle(0);
		//jedisPoolConfig.setMinIdle(1);
		jedisPoolConfig.setMaxWaitMillis(10000);
		jedisPoolConfig.setTestOnBorrow(true);
		return jedisPoolConfig;
	}

	@Bean
	public RedisOperations<String, Object> redisTemplate(final JedisConnectionFactory factory) {
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}
}
