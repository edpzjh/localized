package de.malkusch.localized.configuration;

import java.util.Locale;

import org.hibernate.Session;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Configuration for a Spring/JPA environment.
 * 
 * To enable Spring support, you simply have to put this
 * bean into your Spring context.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class SpringLocalizedConfiguration extends LocalizedConfiguration {
	
	/**
	 * @see LocaleContextHolder#getLocale()
	 */
	@Override
	public Locale resolveLocale(Session session) {
		return LocaleContextHolder.getLocale();
	}

}
