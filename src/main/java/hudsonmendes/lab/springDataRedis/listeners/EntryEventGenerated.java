package hudsonmendes.lab.springDataRedis.listeners;

import hudsonmendes.lab.springDataRedis.model.EntryEvent;

public interface EntryEventGenerated {
	public void onGenerated(EntryEvent event);
}
