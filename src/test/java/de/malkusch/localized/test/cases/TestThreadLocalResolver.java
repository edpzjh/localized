package de.malkusch.localized.test.cases;

import java.util.Locale;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import de.malkusch.localized.exception.UnresolvedLocaleException;
import de.malkusch.localized.localeResolver.ThreadLocalLocaleResolver;

/**
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class TestThreadLocalResolver {

	private Session session;
	
	@Test(expected = UnresolvedLocaleException.class)
	public void testUnsetLocale() throws UnresolvedLocaleException {
		ThreadLocalLocaleResolver resolver = new ThreadLocalLocaleResolver();
		resolver.resolveLocale(session);
	}
	
	@Test
	public void testsetLocale() throws UnresolvedLocaleException {
		ThreadLocalLocaleResolver resolver = new ThreadLocalLocaleResolver();
		
		resolver.setLocale(Locale.GERMAN);
		Assert.assertEquals(Locale.GERMAN, resolver.resolveLocale(session));
		
		resolver.setLocale(Locale.ENGLISH);
		Assert.assertEquals(Locale.ENGLISH, resolver.resolveLocale(session));
	}
	
	@Test
	public void testThreads() throws UnresolvedLocaleException, InterruptedException {
		final ThreadLocalLocaleResolver resolver = new ThreadLocalLocaleResolver();
		
		resolver.setLocale(Locale.GERMAN);
		
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				try {
					resolver.setLocale(Locale.ENGLISH);
					Assert.assertEquals(Locale.ENGLISH, resolver.resolveLocale(session));
					
				} catch (UnresolvedLocaleException e) {
					Assert.fail(e.getMessage());
					
				}
			};
			
		};
		thread.start();
		thread.join();
		Assert.assertEquals(Locale.GERMAN, resolver.resolveLocale(session));
	}

}
