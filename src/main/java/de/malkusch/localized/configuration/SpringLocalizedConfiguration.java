package de.malkusch.localized.configuration;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.springframework.context.i18n.LocaleContextHolder;

import de.malkusch.localized.spi.ListenerIntegrator;

/**
 * Configuration for a Spring/JPA environment.
 * 
 * To enable Spring support, you simply have to put this
 * bean into your Spring context. This bean takes care about registering
 * to {@link ListenerIntegrator}.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class SpringLocalizedConfiguration extends LocalizedConfiguration {
	
	@PostConstruct
	private void register() {
		ListenerIntegrator.setConfiguration(this);
	}
	
	/**
	 * @see LocaleContextHolder#getLocale()
	 */
	@Override
	public Locale resolveLocale(Session session) {
		return LocaleContextHolder.getLocale();
	}

}
