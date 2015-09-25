package hudsonmendes.lab.springDataRedis.services;

import java.time.Instant;

import org.springframework.stereotype.Service;

import hudsonmendes.lab.springDataRedis.model.EntryAction;
import hudsonmendes.lab.springDataRedis.model.EntryEvent;
import hudsonmendes.lab.springDataRedis.model.EntryInfo;

@Service
public class EntryEventBuilderServiceImpl implements EntryEventBuilderService {
	private static final String AUDIENCE_ID = "nfgshf9t5t9yfg";
	private static Integer ID_SEQUENCE = 0;

	@Override
	public EntryEvent build(
			final EntryAction action,
			final String keyword) {

		final EntryEvent event = new EntryEvent();
		event.setChannelId(AUDIENCE_ID);
		event.setReferenceTime(Instant.now());
		event.setAction(action);
		event.setKeyword(makeKeywordFrom(keyword));
		return event;
	}

	private EntryInfo makeKeywordFrom(final String word) {
		final EntryInfo info = new EntryInfo();
		info.setId(rollId());
		info.setMessage(word);
		info.setCreatedAt(Instant.now());
		return info;
	}

	private static Integer rollId() {
		synchronized (ID_SEQUENCE) {
			return ID_SEQUENCE++;
		}
	}

}
