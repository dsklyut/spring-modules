package org.springmodules.jcr.support;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;
import org.springmodules.jcr.JcrTemplate;
import org.springmodules.jcr.SessionFactory;
import org.springmodules.jcr.SessionFactoryUtils;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Convenient class for accessing Jcr objects.
 *
 * @author Costin Leau
 * @author Guillaume Bort <guillaume.bort@zenexity.fr>
 */
public abstract class JcrDaoSupport extends DaoSupport {

    private JcrTemplate template;

    /**
     * Set the JCR SessionFactory to be used by this DAO. Will automatically
     * create a JcrTemplate for the given SessionFactory.
     *
     * @param sessionFactory
     * @see #setTemplate
     */
    public final void setSessionFactory(SessionFactory sessionFactory) {
        this.template = new JcrTemplate(sessionFactory);
    }

    /**
     * Return the Jcr SessionFactory used by this DAO.
     * @return
     */
    public final SessionFactory getSessionFactory() {
        return (template != null ? template.getSessionFactory() : null);
    }

    /**
     * Set the JcrTemplate for this DAO explicitly, as an alternative to
     * specifying a SessionFactory.
     *
     * @param jcrTemplate
     * @see #setSessionFactory
     */
    public final void setTemplate(JcrTemplate jcrTemplate) {
        this.template = jcrTemplate;
    }

    /**
     * Return the JcrTemplate for this DAO, pre-initialized with the
     * SessionFactory or set explicitly.
     */
    public final JcrTemplate getTemplate() {
        return template;
    }

    protected final void checkDaoConfig() {
        Assert.notNull(template, "sessionFactory or jcrTemplate is required");
    }

    /**
     * Get a JCR Session, either from the current transaction or a new one. The
     * latter is only allowed if the "allowCreate" setting of this bean's
     * JcrTemplate is true.
     *
     * @return the JCR Session
     * @throws DataAccessResourceFailureException
     *                               if the Session couldn't be created
     * @throws IllegalStateException if no thread-bound Session found and allowCreate false
     * @see org.springmodules.jcr.SessionFactoryUtils#getSession
     */
    protected final Session getSession() {
        return getSession(this.template.isAllowCreate());
    }

    /**
     * Get a JCR Session, either from the current transaction or a new one. The
     * latter is only allowed if "allowCreate" is true.
     *
     * @param allowCreate if a non-transactional Session should be created when no
     *                    transactional Session can be found for the current thread
     * @return the JCR Session
     * @throws DataAccessResourceFailureException
     *                               if the Session couldn't be created
     * @throws IllegalStateException if no thread-bound Session found and allowCreate false
     * @see org.springmodules.jcr.SessionFactoryUtils#getSession
     */
    protected final Session getSession(boolean allowCreate)
            throws DataAccessResourceFailureException, IllegalStateException {
        return SessionFactoryUtils.getSession(getSessionFactory(), allowCreate);
    }

    /**
     * Convert the given JCRException to an appropriate exception from the
     * org.springframework.dao hierarchy.
     * <p/>
     * Delegates to the convertJCRAccessException method of this DAO's
     * JCRTemplate.
     *
     * @param ex JCRException that occured
     * @return the corresponding DataAccessException instance
     * @see #setTemplate
     * @see org.springmodules.jcr.JcrTemplate#convertJcrAccessException
     */
    protected final DataAccessException convertJcrAccessException(
            RepositoryException ex) {
        return this.template.convertJcrAccessException(ex);
    }

    /**
     * Close the given JCR Session, created via this DAO's SessionFactory, if it
     * isn't bound to the thread.
     *
     * @param session Session to close
     * @see org.springmodules.jcr.SessionFactoryUtils#releaseSession
     */
	protected final void releaseSession(Session session) {
		SessionFactoryUtils.releaseSession(session, getSessionFactory());
	}

}
