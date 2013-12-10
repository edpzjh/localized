package de.malkusch.localized.localeResolver;

import java.util.Locale;

import org.hibernate.Session;
import org.slf4j.LoggerFactory;

import de.malkusch.localized.LocalizedIntegrator;

/**
 * Returns the VM's default locale.
 * 
 * This is the default locale resolver.
 * 
 * @see Locale#getDefault()
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class DefaultLocaleResolver implements LocaleResolver {

	private boolean warnOnce;
	
	/**
	 * Returns the VM's default locale.
	 * 
	 * @see Locale#getDefault()
	 */
	@Override
	public Locale resolveLocale(Session session) {
		if (warnOnce) {
			warnOnce = false;
			LoggerFactory.getLogger(getClass()).warn(
					"You didn't configure a LocaleResolver for @Localized. As default the locale resolves now to the VM's locale.");
			
		}
		return Locale.getDefault();
	}

	/**
	 * Enables a one time warning at {@link #resolveLocale(Session)}.
	 * 
	 * The {@link LocalizedIntegrator} enables this warning when using 
	 * this resolver as default.
	 */
	public void setWarnOnce(boolean warn) {
		this.warnOnce = warn;
	}

}
