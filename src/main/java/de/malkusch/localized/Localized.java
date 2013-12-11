package de.malkusch.localized;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.malkusch.localized.localeResolver.LocaleResolver;

/**
 * Indicates fields which have locale dependent values based on {@link LocaleResolver#resolveLocale(org.hibernate.Session)}.
 * 
 * @see LocalizedIntegrator
 * @see LocaleResolver#resolveLocale(org.hibernate.Session)
 * @author Markus Malkusch <markus@malkusch.de>
 * @since 0.2.8
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Localized {

}
