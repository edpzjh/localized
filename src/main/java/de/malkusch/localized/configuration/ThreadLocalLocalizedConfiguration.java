package de.malkusch.localized.configuration;

import java.util.Locale;

import org.hibernate.Session;

import de.malkusch.localized.exception.UnresolvedLocaleException;

/**
 * Resolves the locale from the current thread.
 * 
 * Before calling {@link #resolveLocale(Session)}, the thread must have
 * set the {@link Locale} by calling {@link #setLocale(Locale)}.  
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class ThreadLocalLocalizedConfiguration extends LocalizedConfiguration {
	
	private ThreadLocal<Locale> localeHolder = new ThreadLocal<Locale>();
	
	/**
	 * Sets the {@link Locale} for this thread.
	 */
	public void setLocale(Locale locale) {
		localeHolder.set(locale);
	}

	@Override
	public Locale resolveLocale(Session session) throws UnresolvedLocaleException {
		Locale locale = localeHolder.get();
		if (locale == null) {
			throw new UnresolvedLocaleException("The current thread didn't call setLocale() before.");
			
		}
		return locale;
	}

}
