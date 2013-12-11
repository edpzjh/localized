package de.malkusch.localized.dao;

import org.hibernate.StatelessSession;

/**
 * DAO for backed by a {@link StatelessSession}.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 * @since 0.2.8
 */
public class LocalizedStatelessSessionDAO extends LocalizedDAO<StatelessSession> {

	public LocalizedStatelessSessionDAO(StatelessSession session) {
		super(session);
	}

}
