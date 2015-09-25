package hudsonmendes.lab.springDataRedis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Serializer {
	public static ObjectMapper get() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		return mapper;
	}

	private Serializer() {
	}
}
