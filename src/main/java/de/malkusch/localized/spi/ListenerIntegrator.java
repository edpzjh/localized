package de.malkusch.localized.spi;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.beanvalidation.DuplicationStrategyImpl;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import de.malkusch.localized.spi.ReadEventListener;

/**
 * Automatic registration of the event listeners.
 * 
 * @author malkusch
 * @see /META-INF/services/org.hibernate.integrator.spi.Integrator
 */
public class ListenerIntegrator implements Integrator {

	@Override
	public void integrate(Configuration configuration,
			SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {

		
		final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
		eventListenerRegistry.addDuplicationStrategy(DuplicationStrategyImpl.INSTANCE);
        
		eventListenerRegistry.appendListeners(EventType.POST_LOAD,   new ReadEventListener(sessionFactory));
		eventListenerRegistry.appendListeners(EventType.POST_UPDATE, new WriteEventListener(sessionFactory));
		eventListenerRegistry.appendListeners(EventType.POST_INSERT, new WriteEventListener(sessionFactory));
		eventListenerRegistry.appendListeners(EventType.POST_DELETE, new DeleteEventListener(sessionFactory));
	}

	@Override
	public void integrate(MetadataImplementor metadata,
			SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {

	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {

	}

}
