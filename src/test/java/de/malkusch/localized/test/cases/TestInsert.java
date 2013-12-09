package de.malkusch.localized.test.cases;

import java.util.Locale;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.malkusch.localized.configuration.ThreadLocalLocalizedConfiguration;
import de.malkusch.localized.test.model.Book;
import de.malkusch.localized.test.rule.SessionRule;

public class TestInsert {

	private Session session;

	private ThreadLocalLocalizedConfiguration localizedConfiguration;

	@Rule
	public final SessionRule sessionRule = new SessionRule();

	@Before
	public void session() {
		session = sessionRule.getSession();
	}

	@Before
	public void localizedConfiguration() {
		localizedConfiguration = sessionRule.getLocalizedConfiguration();
		localizedConfiguration.setLocale(Locale.GERMAN);
	}
	
	@Test
	public void testInsertNull() {
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertNull(book.getTitle());
	}
	
	@Test
	public void testInsert() {
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		book.setTitle("Drecksau");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertEquals("Drecksau", book.getTitle());
	}

}
