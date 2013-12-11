package de.malkusch.localized.exception;

/**
 * Base Exception in this package.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 * @since 0.2.8
 */
public class LocalizedException extends Exception {

	private static final long serialVersionUID = 2501116827614521012L;

	public LocalizedException() {
	}

	public LocalizedException(String message) {
		super(message);
	}

	public LocalizedException(Throwable throwable) {
		super(throwable);
	}

	public LocalizedException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
