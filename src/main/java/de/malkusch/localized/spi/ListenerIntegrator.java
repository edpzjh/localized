package de.malkusch.localized.spi;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.beanvalidation.DuplicationStrategyImpl;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import de.malkusch.localized.LocalizedProperty;

/**
 * Automatic registration of the event listeners.
 * 
 * @author malkusch
 * @see /META-INF/services/org.hibernate.integrator.spi.Integrator
 */
public class ListenerIntegrator implements Integrator {
	
	private static Set<Configuration> configurations = new HashSet<>();

	@Override
	public void integrate(Configuration configuration,
			SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		
		/**
		 * I had hard times to figure out how to add this entity automatically
		 * to the current persistence unit. As Configuration is considered to
		 * be removed this might break in future. So these are the three other
		 * ways which put the entity into the persistence unit:
		 * 
		 * 1. If you have no persistence.xml and configure the EntityManagerFactory with
		 * Spring's LocalContainerEntityManagerFactoryBean#setPackagesToScan(), you have
		 * to add LocalizedProperty.class.getPackage().getName() to those Packages.
		 * 
		 * 2. If you use persistence.xml add <mapping-file>META-INF/localized.xml</mapping-file>
		 * to your persistence-unit.
		 * 
		 * 3. If you configure it programmaticaly (e.g. in a test), use Configuration.addAnnotatedClass()
		 */
		if (! configurations.contains(configuration)) {
			configurations.add(configuration);
			configuration.addAnnotatedClass(LocalizedProperty.class);
			
		}
		
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
