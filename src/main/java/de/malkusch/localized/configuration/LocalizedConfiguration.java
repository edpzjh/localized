package de.malkusch.localized.configuration;

import java.util.Locale;

import org.hibernate.Session;

import de.malkusch.localized.spi.AbstractEventListener;

/**
 * Provides a configuration for the listeners.
 * 
 * The configuration will be installed automatically during initialization.
 * 
 * @see AbstractEventListener#setConfiguration(LocalizationConfiguration)
 * @author malkusch
 */
public abstract class LocalizedConfiguration {
	
	/**
	 * Registers this configuration statically into the listeners.
	 * 
	 * @see LocalizationInterceptor#setConfiguration(LocalizationConfiguration)
	 */
	public LocalizedConfiguration() {
		AbstractEventListener.setConfiguration(this);
	}
	
	/**
	 * Resolves the locale for the current {@link Session}.
	 */
	abstract public Locale resolveLocale(Session session);
	
}
