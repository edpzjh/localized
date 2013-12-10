package de.malkusch.localized.test.cases;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;
import de.malkusch.localized.localeResolver.DefaultLocaleResolver;
import de.malkusch.localized.test.model.Book;
import de.malkusch.localized.test.rule.SessionRule;

/**
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class TestDefaultResolver {
	
	private Session session;
	
	private final LoggingEvent expectedMessage = LoggingEvent.warn("You didn't configure a LocaleResolver for @Localized. As default the locale resolves now to the VM's locale.");
	
	TestLogger logger;
	
	@Rule
	public final SessionRule sessionRule = new SessionRule(false);
	
	@Before
	public void logger() {
		logger = TestLoggerFactory.getTestLogger(DefaultLocaleResolver.class);
	}
	
	@After
	public void clearLogger() {
		logger.clearAll();
	}

	@Before
	public void session() {
		session = sessionRule.getSession();
	}

	@Test
	public void testWarningOnce() {
		DefaultLocaleResolver resolver = new DefaultLocaleResolver();
		resolver.setWarnOnce(true);
		
		resolver.resolveLocale(session);
		assertThat(logger.getLoggingEvents(), is(asList(expectedMessage)));
		logger.clear();
		
		resolver.resolveLocale(session);
		assertTrue(logger.getLoggingEvents().isEmpty());
	}
	
	@Test
	public void testDefaultWarning() {
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		session.save(book);
		session.flush();
		
		assertThat(logger.getLoggingEvents(), is(asList(expectedMessage)));
	}
	
}
