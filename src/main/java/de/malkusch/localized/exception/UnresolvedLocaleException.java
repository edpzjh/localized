package de.malkusch.localized.exception;

public class UnresolvedLocaleException extends LocalizedException {

	private static final long serialVersionUID = 7616492956939081815L;

	public UnresolvedLocaleException() {
	}

	public UnresolvedLocaleException(String message) {
		super(message);
	}

	public UnresolvedLocaleException(Throwable throwable) {
		super(throwable);
	}

	public UnresolvedLocaleException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
