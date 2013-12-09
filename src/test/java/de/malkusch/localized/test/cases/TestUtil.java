package de.malkusch.localized.test.cases;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.malkusch.localized.LocalizedUtil;
import de.malkusch.localized.localeResolver.ThreadLocalLocaleResolver;
import de.malkusch.localized.test.model.Book;
import de.malkusch.localized.test.rule.SessionRule;

/**
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class TestUtil {

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
	
	@Test
	public void testGetLocales() {
		localeResolver.setLocale(Locale.GERMAN);
		Book book = new Book();
		book.setAuthor("Irvine Welsh");
		session.save(book);
		session.flush();
		session.refresh(book);
		
		Collection<Locale> locales = LocalizedUtil.getLocales(session, book);
		Assert.assertEquals(new HashSet<>(Arrays.asList(Locale.GERMAN)), locales);
		
		book.setTitle("Drecksau");
		session.save(book);
		session.flush();
		session.refresh(book);
		
		locales = LocalizedUtil.getLocales(session, book);
		Assert.assertEquals(new HashSet<>(Arrays.asList(Locale.GERMAN)), locales);
		
		localeResolver.setLocale(Locale.ENGLISH);
		book.setTitle("Filth");
		session.save(book);
		session.flush();
		session.refresh(book);
		
		locales = LocalizedUtil.getLocales(session, book);
		Assert.assertEquals(new HashSet<>(Arrays.asList(Locale.GERMAN, Locale.ENGLISH)), locales);
	}

}
