package de.malkusch.localized.localeResolver;

import java.util.Locale;

import org.hibernate.Session;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Resolve locale for a Spring/JPA environment.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class SpringLocaleResolver implements LocaleResolver {
	
	/**
	 * @see LocaleContextHolder#getLocale()
	 */
	@Override
	public Locale resolveLocale(Session session) {
		return LocaleContextHolder.getLocale();
	}

}
