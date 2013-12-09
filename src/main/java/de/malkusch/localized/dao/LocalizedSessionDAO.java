package de.malkusch.localized.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class LocalizedSessionDAO extends LocalizedDAO<Session> {

	public LocalizedSessionDAO(Session session) {
		super(session);
	}
	
	/**
	 * Returns the set of translations for a given entity.
	 */
	public Set<Locale> getLocales(Object entity) {
		Query query = session
				.createQuery("SELECT DISTINCT locale FROM LocalizedProperty WHERE instance = :instance AND type = :type");
		query.setParameter("instance", session.getIdentifier(entity).toString());
		query.setParameter("type", entity.getClass());
		
		@SuppressWarnings("unchecked")
		List<Locale> locales = query.list();
		return new HashSet<>(locales);
	}

}
