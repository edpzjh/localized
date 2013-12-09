package de.malkusch.localized;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import org.hibernate.Session;

import de.malkusch.localized.dao.LocalizedSessionDAO;

/**
 * Helper to get meta information about entities with {@literal @Localized} fields.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class LocalizedUtil {
	
	/**
	 * Returns the set of translations for a given entity.
	 * 
	 * Even if the translation consists only of NULL values it is
	 * considered a translation.
	 * 
	 * @see #deleteLocale(Session, Object, Locale)
	 */
	static public Set<Locale> getLocales(Session session, Object entity) {
		LocalizedSessionDAO dao = new LocalizedSessionDAO(session);
		return dao.getLocales(entity);
	}
	
	/**
	 * Deletes a translation.
	 */
	static public void deleteLocale(Session session, Object entity, Locale locale) {
		LocalizedSessionDAO dao = new LocalizedSessionDAO(session);
		dao.deleteLocale(entity, locale);
	}

	/**
	 * Returns the entity's {@literal @Localized} fields.
	 * 
	 * These fields are made accessible.
	 */
	static public Collection<Field> getLocalizedFields(Class<?> clazz) {
		Collection<Field> fields = getAllDeclaredFields(clazz);
		ArrayList<Field> localizedFields = new ArrayList<>();
		for (Field field : fields) {
			if (field.getAnnotation(Localized.class) != null) {
				field.setAccessible(true);
				localizedFields.add(field);
				
			}
		}
		return localizedFields;
	}
	
	static private Collection<Field> getAllDeclaredFields(Class<?> clazz) {
		ArrayList<Field> fields = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			fields.add(field);
			
		}
		if (clazz.getSuperclass() != null) {
			fields.addAll(getAllDeclaredFields(clazz.getSuperclass()));
			
		}
		return fields;
	}
	
}
