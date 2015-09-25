package hudsonmendes.lab.springDataRedis.model;

import java.io.Serializable;
import java.time.Instant;

public class EntryInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String message;
	private Instant createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final Instant createdAt) {
		this.createdAt = createdAt;
	}
}