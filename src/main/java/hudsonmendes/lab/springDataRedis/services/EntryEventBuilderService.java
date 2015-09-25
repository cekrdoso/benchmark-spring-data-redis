package hudsonmendes.lab.springDataRedis.services;

import hudsonmendes.lab.springDataRedis.model.EntryAction;
import hudsonmendes.lab.springDataRedis.model.EntryEvent;

public interface EntryEventBuilderService {
	public EntryEvent build(final EntryAction action, final String keyword);
}
