package hudsonmendes.lab.springDataRedis.model.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

import hudsonmendes.lab.springDataRedis.model.EntryEvent;
import hudsonmendes.lab.springDataRedis.utils.Serializer;

public class EntryEventSerializer {
	private final EntryEvent _event;

	public EntryEventSerializer(final EntryEvent event) {
		_event = event;
	}

	public String serialize() {
		try {
			return attemptSerialize();
		} catch (final JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String attemptSerialize() throws JsonProcessingException {
		return Serializer.get()
				.writeValueAsString(_event);
	}
}
