package de.malkusch.localized.event;

import java.lang.reflect.Field;

import org.hibernate.StatelessSession;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;

import de.malkusch.localized.Localized;
import de.malkusch.localized.LocalizedIntegrator;
import de.malkusch.localized.LocalizedProperty;
import de.malkusch.localized.exception.LocalizedException;

/**
 * Replaces the entity's @{@link Localized} fields after loading.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 * @since 0.2.8
 */
public class ReadEventListener extends AbstractEventListener implements PostLoadEventListener {

	private static final long serialVersionUID = -8092744226457117745L;
	
	public ReadEventListener(LocalizedIntegrator integrator, SessionFactoryImplementor sessionFactory) {
		super(integrator, sessionFactory);
	}
	
	@Override
	public void onPostLoad(PostLoadEvent event) {
		handleFields(event, event.getEntity(), event.getId());
	}

	@Override
	protected void handleField(StatelessSession session, Field field, Object entity, LocalizedProperty property) throws LocalizedException {
		try {
			field.set(entity, property.getValue());
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new LocalizedException(e);
			
		}
	}

}
