package hudsonmendes.lab.springDataRedis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ConfigForRedis {

	@Value("${redis.host:localhost}")
	private String _redisHostName;

	@Value("${redis.port:6379}")
	private Integer _redisPort;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		final JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(_redisHostName);
		factory.setPort(_redisPort);
		factory.setUsePool(true);
		return factory;
	}

	@Bean
	public RedisOperations<String, Object> redisTemplate(final JedisConnectionFactory factory) {
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}
}
