package de.malkusch.localized.localeResolver;

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
 * @since 0.2.8
 */
public class ThreadLocalLocaleResolver implements LocaleResolver {
	
	private ThreadLocal<Locale> locales = new ThreadLocal<>();
	
	/**
	 * Sets the {@link Locale} for this thread.
	 */
	public void setLocale(Locale locale) {
		locales.set(locale);
	}

	@Override
	public Locale resolveLocale(Session session) throws UnresolvedLocaleException {
		Locale locale = locales.get();
		if (locale == null) {
			throw new UnresolvedLocaleException("The current thread didn't call setLocale() before.");
			
		}
		return locale;
	}

}
