package hudsonmendes.lab.springDataRedis.services;

import hudsonmendes.lab.springDataRedis.listeners.EntryEventGenerated;

public interface EntryGeneratorService {
	public void start(final EntryEventGenerated listener);
}
