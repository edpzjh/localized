package de.malkusch.localized.event;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.spi.AbstractEvent;

import de.malkusch.localized.Localized;
import de.malkusch.localized.LocalizedIntegrator;
import de.malkusch.localized.LocalizedProperty;
import de.malkusch.localized.LocalizedUtil;
import de.malkusch.localized.dao.LocalizedStatelessSessionDAO;
import de.malkusch.localized.exception.LocalizedException;

/**
 * Base class for event handling.
 * 
 * This class dispatches the event to the @{@link Localized} fields of the
 * event's entity.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 * @since 0.2.8
 */
abstract public class AbstractEventListener {
	
	private LocalizedIntegrator integrator;
	
	protected SessionFactoryImplementor sessionFactory;

	public AbstractEventListener(LocalizedIntegrator integrator, SessionFactoryImplementor sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.integrator = integrator;
	}
	
	/**
	 * Handles the event for each @{@link Localized} field.
	 * 
	 * This method has a separate {@link Session} as Hibernate's event system
	 * seems to break when using the same Session. The session runs on the same
	 * {@link Connection}, i.e. in the same transaction.
	 */
	abstract protected void handleField(StatelessSession session, Field field, Object entity, LocalizedProperty property) throws LocalizedException;
	
	/**
	 * Dispatches an event for each @{@link Localized} field.
	 * 
	 * The Hibernate event callback should be forwarded to this method.
	 * 
	 * @see #handleField(StatelessSession, Field, Object, LocalizedProperty)
	 */
	protected <T extends AbstractEvent> void handleFields(T event, Object entity, Serializable id) {
		if (entity instanceof LocalizedProperty) {
			return;

		}
		StatelessSession session = sessionFactory.openStatelessSession(event.getSession().connection());
		try {
			Locale locale = integrator.getLocaleResolver().resolveLocale(event.getSession());
			LocalizedStatelessSessionDAO dao = new LocalizedStatelessSessionDAO(session);
			
			for (Field field : LocalizedUtil.getLocalizedFields(entity.getClass())) {
				LocalizedProperty property = dao.find(entity.getClass(), field.getName(), locale, id);
				if (property == null) {
					property = new LocalizedProperty();
					property.setField(field.getName());
					property.setLocale(locale);
					property.setType(entity.getClass());
					property.setInstance(id.toString());
					
				}
				handleField(session, field, entity, property);
				
			}
		} catch (LocalizedException e) {
			throw new IllegalStateException(e);
			
		} finally {
			session.close();
			
		}
	}
	
}
