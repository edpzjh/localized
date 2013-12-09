package de.malkusch.localized.dao;

import org.hibernate.StatelessSession;

/**
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class LocalizedStatelessSessionDAO extends LocalizedDAO<StatelessSession> {

	public LocalizedStatelessSessionDAO(StatelessSession session) {
		super(session);
	}

}
