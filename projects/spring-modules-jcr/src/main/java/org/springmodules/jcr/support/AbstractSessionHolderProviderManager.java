/**
 * Created on Nov 10, 2005
 *
 * $Id: AbstractSessionHolderProviderManager.java,v 1.3 2006/03/07 13:41:07 costin Exp $
 * $Revision: 1.3 $
 */
package org.springmodules.jcr.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springmodules.jcr.SessionHolderProvider;
import org.springmodules.jcr.SessionHolderProviderManager;

import javax.jcr.Repository;
import java.util.List;

/**
 * Base implementation for SessionHolderProviderManager that adds most of the
 * functionality needed by the interface. Usually interface implementations will
 * extends this class.
 * 
 * @author Costin Leau
 * 
 */
public abstract class AbstractSessionHolderProviderManager implements
		SessionHolderProviderManager {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionHolderProvider defaultProvider = new GenericSessionHolderProvider();

	/**
	 * Returns all the providers for this class. Subclasses have to implement
	 * this method.
	 * 
	 * @return sessionHolderProviders
	 */
	public abstract List getProviders();

	/**
	 * @see org.springmodules.jcr.SessionHolderProviderManager#getSessionProvider(Repository)
	 */
	public SessionHolderProvider getSessionProvider(Repository repository) {
		// graceful fallback
		if (repository == null)
			return defaultProvider;

		String key = repository.getDescriptor(Repository.REP_NAME_DESC);
		List providers = getProviders();

		// search the provider
		for (int i = 0; i < providers.size(); i++) {
			SessionHolderProvider provider = (SessionHolderProvider) providers
					.get(i);
			if (provider.acceptsRepository(key)) {
				if (logger.isDebugEnabled())
					logger.debug("specific SessionHolderProvider found for repository "
									+ key);
				return provider;
			}
		}

		// no provider found - return the default one
		if (logger.isDebugEnabled())
			logger.debug("no specific SessionHolderProvider found for repository "
					+ key + "; using the default one");
		return defaultProvider;
	}
}
