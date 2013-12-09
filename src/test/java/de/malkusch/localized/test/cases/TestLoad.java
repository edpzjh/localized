package de.malkusch.localized.test.cases;

import java.util.Locale;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.malkusch.localized.localeResolver.ThreadLocalLocaleResolver;
import de.malkusch.localized.test.model.Book;
import de.malkusch.localized.test.rule.SessionRule;

public class TestLoad {
	
	private Session session;
	
	private ThreadLocalLocaleResolver localeResolver;
	
	@Rule
	public final SessionRule sessionRule = new SessionRule();
	
	@Before
	public void session() {
		session = sessionRule.getSession();
	}
	
	@Before
	public void localizedConfiguration() {
		localeResolver = sessionRule.getLocaleResolver();
	}
	
	/**
	 * Loading of unset properties
	 */
	@Test
	public void testNull() {
		localeResolver.setLocale(Locale.GERMAN);
		
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertNull(book.getTitle());
		
		localeResolver.setLocale(Locale.ENGLISH);
		session.refresh(book);
		Assert.assertNull(book.getTitle());
	}
	
	/**
	 * Only translated into one language
	 */
	@Test
	public void testOneTranslation() {
		localeResolver.setLocale(Locale.GERMAN);
		
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		book.setTitle("Drecksau");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertEquals("Drecksau", book.getTitle());
		
		localeResolver.setLocale(Locale.ENGLISH);
		session.refresh(book);
		Assert.assertNull(book.getTitle());
		
		localeResolver.setLocale(Locale.GERMAN);
		session.refresh(book);
		Assert.assertEquals("Drecksau", book.getTitle());
	}

	/**
	 * Translated in more languages
	 */
	@Test
	public void testTranslated() {
		localeResolver.setLocale(Locale.GERMAN);
		
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		book.setTitle("Drecksau");
		session.save(book);
		session.flush();
		
		localeResolver.setLocale(Locale.ENGLISH);
		book.setTitle("Filth");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertEquals("Filth", book.getTitle());
		
		localeResolver.setLocale(Locale.GERMAN);
		session.refresh(book);
		Assert.assertEquals("Drecksau", book.getTitle());
		
		localeResolver.setLocale(Locale.ENGLISH);
		session.refresh(book);
		Assert.assertEquals("Filth", book.getTitle());
	}

}
