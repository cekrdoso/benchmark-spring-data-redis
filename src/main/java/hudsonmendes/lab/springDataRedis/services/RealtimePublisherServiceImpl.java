package hudsonmendes.lab.springDataRedis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import hudsonmendes.lab.springDataRedis.model.EntryEvent;

@Service
public class RealtimePublisherServiceImpl implements RealtimePublisherService {

	@Autowired
	private RedisOperations<String, Object> _redis;

	@Override
	public void publish(
			final String audienceId,
			final EntryEvent event) {

		_redis
				.boundListOps(audienceId)
				.leftPush(event);
	}

}
