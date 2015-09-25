package hudsonmendes.lab.springDataRedis.model;

import java.io.Serializable;
import java.time.Instant;

import hudsonmendes.lab.springDataRedis.model.serialization.EntryEventSerializer;

public class EntryEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private String channelId;
	private Instant referenceTime;
	private EntryInfo keyword;
	private EntryAction action;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(final String channelId) {
		this.channelId = channelId;
	}

	public Instant getReferenceTime() {
		return referenceTime;
	}

	public void setReferenceTime(final Instant referenceTime) {
		this.referenceTime = referenceTime;
	}

	public EntryInfo getKeyword() {
		return keyword;
	}

	public void setKeyword(final EntryInfo keyword) {
		this.keyword = keyword;
	}

	public EntryAction getAction() {
		return action;
	}

	public void setAction(final EntryAction action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return new EntryEventSerializer(this).serialize();
	}
}