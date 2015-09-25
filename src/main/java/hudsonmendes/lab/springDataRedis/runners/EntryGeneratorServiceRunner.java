package hudsonmendes.lab.springDataRedis.runners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.util.Assert;

import hudsonmendes.lab.springDataRedis.listeners.EntryEventGenerated;
import hudsonmendes.lab.springDataRedis.model.EntryAction;
import hudsonmendes.lab.springDataRedis.model.EntryEvent;
import hudsonmendes.lab.springDataRedis.services.EntryEventBuilderService;
import hudsonmendes.lab.springDataRedis.services.RealtimePublisherService;
import hudsonmendes.lab.springDataRedis.utils.Sleep;

public class EntryGeneratorServiceRunner implements Runnable {
	private final static List<String> MESSAGES = new ArrayList<>();
	private final static Random RANDOM = new Random();
	private final static String[] WORDS = new String[] {
			"jump", "pizza", "suspicion", "narrative", "bridge",
			"slide", "spring", "sabotage", "comfortable", "conscious"
	};

	private List<EntryEventGenerated> _listeners;
	private EntryEventBuilderService _svcKeywords;
	private RealtimePublisherService _svcPublisher;

	public EntryGeneratorServiceRunner on(final List<EntryEventGenerated> listeners) {
		_listeners = listeners;
		return this;
	}

	public EntryGeneratorServiceRunner with(final EntryEventBuilderService svcKeywords) {
		_svcKeywords = svcKeywords;
		return this;
	}

	public EntryGeneratorServiceRunner with(final RealtimePublisherService svcPublisher) {
		_svcPublisher = svcPublisher;
		return this;
	}

	@Override
	public void run() {
		assertRequirements();
		while (true) {
			generateNewEvent();
			Sleep.seconds(1);
		}
	}

	private void assertRequirements() {
		Assert.notNull(_svcKeywords, "'svcKeywords' cannot be null.");
		Assert.notNull(_svcPublisher, "'svcPublisher' cannot be null.");
	}

	private void generateNewEvent() {
		final String randomWord = fetchRandomWord();
		notify(_svcKeywords.build(
				actionFor(randomWord),
				randomWord));
		MESSAGES.add(randomWord);
	}

	private void notify(final EntryEvent event) {
		Assert.notNull(event, "'event' cannot be null.");

		if (_listeners == null)
			return;

		for (final EntryEventGenerated l : _listeners)
			l.onGenerated(event);
	}

	private EntryAction actionFor(final String randomWord) {
		synchronized (MESSAGES) {
			if (MESSAGES.contains(randomWord))
				return EntryAction.REMOVE;
			else
				return EntryAction.ADD;
		}
	}

	private String fetchRandomWord() {
		final Integer index = RANDOM.nextInt(WORDS.length);
		return WORDS[index];
	}
}
