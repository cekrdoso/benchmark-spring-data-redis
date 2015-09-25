package hudsonmendes.lab.springDataRedis.services;

import hudsonmendes.lab.springDataRedis.model.EntryEvent;

public interface RealtimePublisherService {
	void publish(String audienceId, EntryEvent event);
}
