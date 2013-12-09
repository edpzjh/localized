package de.malkusch.localized.configuration;

import java.util.Locale;

import org.hibernate.Session;

/**
 * Returns the VM's default locale.
 * 
 * If no other {@LocalizedConfiguration} will be instantiated this object
 * will be used.
 * 
 * @see Locale#getDefault()
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class DefaultLocalizedConfiguration extends LocalizedConfiguration {

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
