package de.malkusch.localized.test.cases;

import java.util.Locale;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.malkusch.localized.LocalizedDAO;
import de.malkusch.localized.LocalizedProperty;
import de.malkusch.localized.configuration.ThreadLocalLocalizedConfiguration;
import de.malkusch.localized.exception.UnresolvedLocaleException;
import de.malkusch.localized.test.model.Book;
import de.malkusch.localized.test.rule.SessionRule;

public class TestDelete {

	private Book book;

	private Session session;

	private LocalizedDAO dao;

	private ThreadLocalLocalizedConfiguration localizedConfiguration;

	@Rule
	public final SessionRule sessionRule = new SessionRule();

	@Before
	public void before() {
		session = sessionRule.getSession();

		dao = sessionRule.getDao();

		localizedConfiguration = sessionRule.getLocalizedConfiguration();
		localizedConfiguration.setLocale(Locale.GERMAN);

		book = new Book();
		book.setAuthor("Irvine Welsh");
		book.setTitle("Drecksau");
		session.save(book);
		session.flush();
	}

	@Test
	public void testDelete() throws UnresolvedLocaleException {
		session.delete(book);
		session.flush();

		LocalizedProperty property = dao.find(book.getClass(), "title",
				localizedConfiguration.resolveLocale(session), book.getId());
		Assert.assertNull(property);
	}

}
