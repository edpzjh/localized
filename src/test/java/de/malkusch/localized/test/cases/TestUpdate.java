package de.malkusch.localized.test.cases;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.localized.localeResolver.ThreadLocalLocaleResolver;
import de.malkusch.localized.test.model.Book;
import de.malkusch.localized.test.rule.SessionRule;

@RunWith(Parameterized.class)
public class TestUpdate {

	private Session session;

	private ThreadLocalLocaleResolver localeResolver;
	
	private Book book;
	
	private Locale locale;

	@Rule
	public final SessionRule sessionRule = new SessionRule();
	
	public TestUpdate(Locale locale) {
		this.locale = locale;
	}

	@Parameters
	public static Collection<Locale[]> locales() {
		Locale[][] locales = new Locale[][] { { Locale.GERMAN }, { Locale.ENGLISH } };
		return Arrays.asList(locales);
	}


	@Before
	public void before() {
		session = sessionRule.getSession();
		localeResolver = sessionRule.getLocaleResolver();
		localeResolver.setLocale(Locale.GERMAN);
		
		book = new Book();
		book.setAuthor("Irvine Welsh");
		book.setTitle("Drecksau");
		session.save(book);
		session.flush();
		
		localeResolver.setLocale(locale);
	}
	
	@Test
	public void testUpdate() {
		book.setTitle("Drecksau 2");
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertEquals("Drecksau 2", book.getTitle());
	}
	
	@Test
	public void testSetNull() {
		book.setTitle(null);
		session.save(book);
		session.flush();
		
		session.refresh(book);
		Assert.assertNull(book.getTitle());
	}

}
