package hudsonmendes.lab.springDataRedis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import hudsonmendes.lab.springDataRedis.listeners.EntryEventGenerated;
import hudsonmendes.lab.springDataRedis.runners.EntryGeneratorServiceRunner;
import hudsonmendes.lab.springDataRedis.utils.Sleep;

@Service
public class EntryGeneratorServiceImpl implements EntryGeneratorService {
	private final List<EntryEventGenerated> _listeners = new ArrayList<>();

	@Autowired
	private EntryEventBuilderService _svcBuilder;

	@Autowired
	private RealtimePublisherService _svcPublisher;

	private ExecutorService _worker;

	@Override
	public void start(final EntryEventGenerated listener) {
		addListener(listener);
		ensureWorker();
		while (!_worker.isShutdown())
			Sleep.seconds(1);
	}

	private void addListener(final EntryEventGenerated listener) {
		synchronized (_listeners) {
			if (listener != null)
				_listeners.add(listener);
		}
	}

	private void ensureWorker() {
		if (_worker != null)
			return;

		synchronized (this) {
			if (_worker != null)
				return;
			createWorker();
			addShutdownHook();
		}
	}

	private void createWorker() {
		_worker = Executors.newSingleThreadExecutor();
		_worker.execute(new EntryGeneratorServiceRunner()
				.with(_svcBuilder)
				.with(_svcPublisher)
				.on(_listeners));
	}

	private void addShutdownHook() {
		Assert.notNull("'worker' cannot get a shutdown hook because it's null.");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (!_worker.isShutdown())
					_worker.shutdownNow();
			}
		});
	}

}
