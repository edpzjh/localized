package de.malkusch.localized.test.rule;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import de.malkusch.localized.LocalizedProperty;
import de.malkusch.localized.configuration.ThreadLocalLocalizedConfiguration;
import de.malkusch.localized.test.model.Book;

public class SessionRule implements MethodRule {

	private Session session;

	private SessionFactory sessionFactory;

	private ThreadLocalLocalizedConfiguration localizedConfiguration;
	
	public Session getSession() {
		return session;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public ThreadLocalLocalizedConfiguration getLocalizedConfiguration() {
		return localizedConfiguration;
	}

	@Override
	public Statement apply(final Statement statement, FrameworkMethod method,
			Object object) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				sessionFactory = createSessionFactory();
				session = sessionFactory.openSession();
				try {
					localizedConfiguration = new ThreadLocalLocalizedConfiguration();
					statement.evaluate();
					
				} finally {
					session.close();
					sessionFactory.close();
				}
			}
		};

	}

	@SuppressWarnings("deprecation")
	private SessionFactory createSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		configuration.addAnnotatedClass(Book.class);
		configuration.addAnnotatedClass(LocalizedProperty.class); // TODO This
																	// should
																	// not be
																	// necessary
		return configuration.buildSessionFactory();
	}

}
