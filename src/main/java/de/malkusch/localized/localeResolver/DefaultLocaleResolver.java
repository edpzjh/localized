package de.malkusch.localized.localeResolver;

import java.util.Locale;

import org.hibernate.Session;

/**
 * Returns the VM's default locale.
 * 
 * This is the default locale resolver.
 * 
 * @see Locale#getDefault()
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class DefaultLocaleResolver implements LocaleResolver {

	/**
	 * Returns the VM's default locale.
	 * 
	 * @see Locale#getDefault()
	 */
	@Override
	public Locale resolveLocale(Session session) {
		return Locale.getDefault();
	}

}
