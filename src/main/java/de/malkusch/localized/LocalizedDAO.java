package de.malkusch.localized;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.SharedSessionContract;

public class LocalizedDAO {
	
	private SharedSessionContract session;

	public LocalizedDAO(SharedSessionContract session) {
		this.session = session;
	}
	
	public LocalizedProperty find(Class<?> type, String field, Locale locale, Serializable id) {
		Query query = session
				.createQuery("FROM LocalizedProperty WHERE instance = :instance"
						+ " AND locale = :locale"
						+ " AND field = :field" + " AND type = :type");
		query.setParameter("instance", id.toString());
		query.setParameter("locale", locale);
		query.setParameter("field", field);
		query.setParameter("type", type);
		
		List<?> results = query.list();
		switch (results.size()) {

		case 0:
			return null;

		case 1:
			return (LocalizedProperty) results.get(0);

		default:
			throw new IllegalStateException(
					"This query should not return more than 1 result.");

		}
	}

}
