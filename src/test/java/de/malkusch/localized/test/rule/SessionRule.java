package de.malkusch.localized.test.rule;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import de.malkusch.localized.LocalizedIntegrator;
import de.malkusch.localized.dao.LocalizedSessionDAO;
import de.malkusch.localized.localeResolver.ThreadLocalLocaleResolver;
import de.malkusch.localized.test.model.Book;

public class SessionRule implements MethodRule {

	private Session session;

	private SessionFactory sessionFactory;

	private LocalizedSessionDAO dao;
	
	private ThreadLocalLocaleResolver localeResolver;
	
	private boolean initResolver;
	
	public SessionRule() {
		this(true);
	}
	
	public SessionRule(boolean initResolver) {
		this.initResolver = initResolver;
	}
	
	public LocalizedSessionDAO getDao() {
		return dao;
	}
	
	public Session getSession() {
		return session;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public ThreadLocalLocaleResolver getLocaleResolver() {
		return localeResolver;
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
					dao = new LocalizedSessionDAO(session);
					localeResolver = new ThreadLocalLocaleResolver();
					if (initResolver) {
						LocalizedIntegrator.setLocaleResolver(localeResolver);
						
					}
					
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
		return configuration.buildSessionFactory();
	}

}
