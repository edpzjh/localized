package de.malkusch.localized.configuration;

import java.util.Locale;

import org.hibernate.Session;

import de.malkusch.localized.exception.UnresolvedLocaleException;
import de.malkusch.localized.spi.AbstractEventListener;

/**
 * Provides a configuration for the listeners.
 * 
 * @see AbstractEventListener#setConfiguration(LocalizationConfiguration)
 * @author Markus Malkusch <markus@malkusch.de>
 */
public abstract class LocalizedConfiguration {
	
	/**
	 * Resolves the locale for the current {@link Session}.
	 * @throws UnresolvedLocaleException 
	 */
	abstract public Locale resolveLocale(Session session) throws UnresolvedLocaleException;
	
}
