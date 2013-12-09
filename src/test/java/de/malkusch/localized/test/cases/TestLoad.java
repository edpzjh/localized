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

public class TestLoad {
	
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
	}
	
	/**
	 * Loading of unset properties
	 */
	@Test
	public void testNull() {
		localizedConfiguration.setLocale(Locale.GERMAN);
		
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertNull(book.getTitle());
		
		localizedConfiguration.setLocale(Locale.ENGLISH);
		session.refresh(book);
		Assert.assertNull(book.getTitle());
	}
	
	/**
	 * Only translated into one language
	 */
	@Test
	public void testOneTranslation() {
		localizedConfiguration.setLocale(Locale.GERMAN);
		
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		book.setTitle("Drecksau");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertEquals("Drecksau", book.getTitle());
		
		localizedConfiguration.setLocale(Locale.ENGLISH);
		session.refresh(book);
		Assert.assertNull(book.getTitle());
		
		localizedConfiguration.setLocale(Locale.GERMAN);
		session.refresh(book);
		Assert.assertEquals("Drecksau", book.getTitle());
	}

	/**
	 * Translated in more languages
	 */
	@Test
	public void testTranslated() {
		localizedConfiguration.setLocale(Locale.GERMAN);
		
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		book.setTitle("Drecksau");
		session.save(book);
		session.flush();
		
		localizedConfiguration.setLocale(Locale.ENGLISH);
		book.setTitle("Filth");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertEquals("Filth", book.getTitle());
		
		localizedConfiguration.setLocale(Locale.GERMAN);
		session.refresh(book);
		Assert.assertEquals("Drecksau", book.getTitle());
		
		localizedConfiguration.setLocale(Locale.ENGLISH);
		session.refresh(book);
		Assert.assertEquals("Filth", book.getTitle());
	}

}
