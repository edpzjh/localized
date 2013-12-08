package de.malkusch.localized;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Helper to get meta information about entities with {@literal @Localized} fields.
 * 
 * @author malkusch
 */
public class LocalizedUtil {

	/**
	 * Returns the entity's {@literal @Localized} fields.
	 * 
	 * These fields are made accessible.
	 */
	static public Collection<Field> getLocalizedFields(Class<?> clazz) {
		Collection<Field> fields = getAllDeclaredFields(clazz);
		ArrayList<Field> localizedFields = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.getAnnotation(Localized.class) != null) {
				field.setAccessible(true);
				localizedFields.add(field);
				
			}
		}
		return localizedFields;
	}
	
	static private Collection<Field> getAllDeclaredFields(Class<?> clazz) {
		ArrayList<Field> fields = new ArrayList<Field>();
		for (Field field : clazz.getDeclaredFields()) {
			fields.add(field);
			
		}
		if (clazz.getSuperclass() != null) {
			fields.addAll(getAllDeclaredFields(clazz.getSuperclass()));
			
		}
		return fields;
	}
	
}
