package hudsonmendes.lab.springDataRedis.utils;

import org.springframework.util.Assert;

public final class Sleep {
	public static void seconds(final Integer seconds) {
		Assert.notNull(seconds, "'seconds' cannot be null.");
		Assert.isTrue(seconds > 0, "'seconds' must be bigger than 0");
		try {
			Thread.sleep(seconds * 1000L);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void milliseconds(final Integer milliseconds) {
		Assert.notNull(milliseconds, "'milliseconds' cannot be null.");
		Assert.isTrue(milliseconds > 0, "'milliseconds' must be bigger than 0");
		try {
			Thread.sleep(milliseconds * 1L);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Sleep() {
	}
}
