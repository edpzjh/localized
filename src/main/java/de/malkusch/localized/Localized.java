package de.malkusch.localized;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.malkusch.localized.configuration.LocalizedConfiguration;

/**
 * Indicates fields which have locale dependent values based on {@link LocalizedConfiguration#resolveLocale(org.hibernate.Session)}.
 * 
 * @see LocalizationInterceptor
 * @see LocalizedConfiguration#resolveLocale(org.hibernate.Session)
 * @author Markus Malkusch <markus@malkusch.de>
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Localized {

}
