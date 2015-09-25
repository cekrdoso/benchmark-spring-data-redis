package hudsonmendes.lab.springDataRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hudsonmendes.lab.springDataRedis.listeners.EntryEventGenerated;
import hudsonmendes.lab.springDataRedis.services.EntryGeneratorService;
import hudsonmendes.lab.springDataRedis.services.RealtimePublisherService;

@SpringBootApplication
public class AppEntrypoint implements CommandLineRunner {
	@Autowired
	private EntryGeneratorService _svcGenerator;

	@Autowired
	private RealtimePublisherService _svcPublisher;

	public static void main(final String[] args) {
		SpringApplication.run(AppEntrypoint.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		_svcGenerator.start(publishEvent());
	}

	private EntryEventGenerated publishEvent() {
		return event -> {
			System.out.println("EVENT: " + event.toString());
			_svcPublisher.publish(
					event.getChannelId(),
					event);
		};
	}
}
