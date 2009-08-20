package org.springmodules.jcr.support;

import org.springmodules.jcr.SessionHolderProvider;
import org.springmodules.jcr.SessionHolder;

import javax.jcr.Session;

/**
 * Created by IntelliJ IDEA.
 * User: dsklyut
 * Date: Aug 20, 2009
 * Time: 3:37:48 PM
 */
public class NoOpSessionHolderProvider implements SessionHolderProvider {
    public SessionHolder createSessionHolder(Session session) {
        return null;
    }

    public boolean acceptsRepository(String repositoryName) {
        return "NoOp".equals(repositoryName);
    }
}
