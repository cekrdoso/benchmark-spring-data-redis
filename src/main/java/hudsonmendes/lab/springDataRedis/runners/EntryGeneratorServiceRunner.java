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
		"ability", "according", "account", "activity", "actually",
		"address", "against", "agreement", "already", "although",
		"American", "analysis", "another", "anything", "approach",
		"attention", "attorney", "audience", "authority", "available",
		"beautiful", "because", "behavior", "believe", "benefit",
		"between", "billion", "brother", "building", "business",
		"campaign", "candidate", "capital", "central", "century",
		"certain", "certainly", "challenge", "character", "citizen",
		"clearly", "collection", "college", "commercial", "community",
		"company", "compare", "computer", "concern", "condition",
		"conference", "Congress", "consider", "consumer", "contain",
		"continue", "control", "country", "cultural", "culture",
		"current", "customer", "daughter", "decision", "defense",
		"Democrat", "democratic", "describe", "despite", "determine",
		"develop", "difference", "different", "difficult", "direction",
		"economic", "economy", "education", "election", "employee",
		"especially", "establish", "evening", "everybody", "everyone",
		"experience", "explain", "federal", "feeling", "finally",
		"financial", "foreign", "forward", "general", "generation",
		"government", "herself", "himself", "history", "hospital",
		"however", "hundred", "husband", "identify", "imagine",
		"important", "improve", "include", "including", "increase",
		"indicate", "individual", "industry", "instead", "interest",
		"interview", "investment", "involve", "kitchen", "knowledge",
		"language", "machine", "magazine", "maintain", "majority",
		"management", "manager", "marriage", "material", "measure",
		"medical", "meeting", "mention", "message", "military",
		"million", "mission", "morning", "movement", "national",
		"natural", "necessary", "network", "newspaper", "nothing",
		"officer", "official", "operation", "outside", "painting",
		"particular", "partner", "patient", "pattern", "perform",
		"perhaps", "personal", "physical", "picture", "political",
		"politics", "popular", "population", "position", "positive",
		"possible", "practice", "prepare", "present", "president",
		"pressure", "prevent", "private", "probably", "problem",
		"process", "produce", "product", "production", "professor",
		"program", "project", "property", "protect", "provide",
		"purpose", "quality", "question", "quickly", "reality",
		"realize", "receive", "recently", "recognize", "reflect",
		"religious", "remember", "represent", "Republican", "require",
		"research", "resource", "respond", "response", "science",
		"scientist", "section", "security", "serious", "service",
		"several", "shoulder", "similar", "situation", "society",
		"soldier", "somebody", "someone", "something", "sometimes",
		"station", "strategy", "structure", "student", "subject",
		"successful", "suddenly", "suggest", "support", "surface",
		"teacher", "technology", "television", "themselves", "thought",
		"thousand", "through", "together", "tonight", "training",
		"treatment", "trouble", "understand", "usually", "various",
		"violence", "western", "whatever", "without", "yourself"
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
			Sleep.milliseconds(500);
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
