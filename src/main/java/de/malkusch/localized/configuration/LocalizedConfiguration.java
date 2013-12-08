package de.malkusch.localized.configuration;

import java.util.Locale;

import org.hibernate.Session;

import de.malkusch.localized.exception.UnresolvedLocaleException;
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
	 * @throws UnresolvedLocaleException 
	 */
	abstract public Locale resolveLocale(Session session) throws UnresolvedLocaleException;
	
}
