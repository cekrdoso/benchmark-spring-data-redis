package hudsonmendes.lab.springDataRedis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
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
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
		.master("mymaster")
		.sentinel(_sentinelHostName, _sentinelPort);
		return new JedisConnectionFactory(sentinelConfig);
	}

	@Bean
	public RedisOperations<String, Object> redisTemplate(final JedisConnectionFactory factory) {
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}
}
