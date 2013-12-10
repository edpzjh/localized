package de.malkusch.localized.test.cases;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.malkusch.localized.localeResolver.DefaultLocaleResolver;
import de.malkusch.localized.test.model.Book;
import de.malkusch.localized.test.rule.SessionRule;

/**
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class TestDefaultResolver {
	
	private Session session;
	
	@Rule
	public final SessionRule sessionRule = new SessionRule();

	@Before
	public void session() {
		session = sessionRule.getSession();
	}

	@Test
	public void testWarningOnce() {
		DefaultLocaleResolver resolver = new DefaultLocaleResolver();
		resolver.setWarnOnce(true);
		
		resolver.resolveLocale(session);
		//TODO assert Warning
		
		resolver.resolveLocale(session);
		//TODO assert no Warning
	}
	
	@Test
	public void testDefaultWarning() {
		//TODO initiate LocalizedInitiator with default resolver
		
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		session.save(book);
		session.flush();
		
		//TODO assert Warning
	}
	
}
