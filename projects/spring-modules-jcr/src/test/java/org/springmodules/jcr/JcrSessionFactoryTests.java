package org.springmodules.jcr;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springmodules.jcr.support.ListSessionHolderProviderManager;

import javax.jcr.NamespaceRegistry;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.observation.ObservationManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JcrSessionFactoryTests {

    private JcrSessionFactory factory;

    private Repository repo;
    private Session session;
    private Workspace ws;
    private NamespaceRegistry registry;

    @Before
    public void setUp() throws Exception {
        repo = EasyMock.createMock(Repository.class);
        session = createMock(Session.class);
        ws = createMock(Workspace.class);
        registry = createMock(NamespaceRegistry.class);

        factory = new JcrSessionFactory();
        factory.setRepository(repo);
    }

    @After
    public void tearDown() throws Exception {
        repo = null;
        session = null;
        ws = null;
        registry = null;
        factory = null;
    }

    /*
      * Test method for 'org.springmodules.jcr.JcrSessionFactory.getSession()'
      */
    @Test
    public void testGetSession() {
        try {

            expect(repo.login(null, null)).andReturn(null);
            replay(repo);
            factory.getSession();
            verify(repo);

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    /*
      * Test method for
      * 'org.springmodules.jcr.JcrSessionFactory.afterPropertiesSet'
      */
    @Test
    public void testAfterPropertiesSet() throws Exception {
        try {
            factory.setRepository(null);
            factory.afterPropertiesSet();
            fail("expected exception (session factory badly initialized");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testConstructor() {
        factory = new JcrSessionFactory(repo, "ws", null);
        assertEquals(repo, factory.getRepository());
        assertEquals("ws", factory.getWorkspaceName());
        assertNull(factory.getCredentials());

        factory.setWorkspaceName("ws");
        assertEquals(factory.getWorkspaceName(), "ws");
    }

    @Test
    public void testEquals() {
        assertEquals(factory.hashCode(), repo.hashCode() + 17 * 37);
        assertTrue(!factory.equals(null));
        assertEquals(factory, factory);

        Repository repo2 = createNiceMock(Repository.class);

        replay(repo, repo2);

        JcrSessionFactory fact2 = new JcrSessionFactory();
        fact2.setRepository(repo2);
        assertFalse(factory.equals(fact2));
        verify(repo, repo2);
    }

    @Test
    public void testAddListeners() throws RepositoryException {
        EventListenerDefinition def1 = new EventListenerDefinition();
        EventListenerDefinition def2 = new EventListenerDefinition();

        EventListenerDefinition listeners[] = new EventListenerDefinition[]{
                def1, def2};
        factory.setEventListeners(listeners);

        ObservationManager oManager = createMock(ObservationManager.class);

        expect(repo.login(null, null)).andReturn(session);
        expect(session.getWorkspace()).andReturn(ws);
        expect(ws.getObservationManager()).andReturn(oManager);

        oManager.addEventListener(def1.getListener(), def1.getEventTypes(),
                def1.getAbsPath(), def1.isDeep(), def1.getUuid(), def1
                        .getNodeTypeName(), def1.isNoLocal());
        oManager.addEventListener(def2.getListener(), def2.getEventTypes(),
                def2.getAbsPath(), def2.isDeep(), def2.getUuid(), def2
                        .getNodeTypeName(), def2.isNoLocal());

        replay(repo, session, ws, oManager);

        // coverage madness
        assertSame(listeners, factory.getEventListeners());
        Session sess = factory.getSession();
        assertSame(session, sess);

        verify(repo, session, ws, oManager);
    }

    @Test
    public void testRegisterNamespaces() throws Exception {
        Properties namespaces = new Properties();
        namespaces.put("foo", "bar");
        namespaces.put("hocus", "pocus");

        factory.setNamespaces(namespaces);

        // afterPropertiesSet
        expect(repo.login(null, null)).andReturn(session);
        expect(session.getWorkspace()).andReturn(ws);
        expect(ws.getNamespaceRegistry()).andReturn(registry);
        expect(registry.getPrefixes()).andReturn(new String[0]);

        // destroy
        registry.registerNamespace("foo", "bar");
        registry.registerNamespace("hocus", "pocus");

        session.logout();

        replay(registry, ws, session, repo);

        factory.afterPropertiesSet();

        factory.destroy();

        verify(registry, ws, session, repo);
    }

    @Test
    public void testForceRegistryNamespace() throws Exception {
        String foo = "foo";
        Properties namespaces = new Properties();
        namespaces.put(foo, "bar");
        namespaces.put("hocus", "pocus");

        factory.setNamespaces(namespaces);
        factory.setForceNamespacesRegistration(true);
        factory.setSkipExistingNamespaces(false);
        factory.setKeepNewNamespaces(false);


        // afterPropertiesSet
        expect(repo.login(null, null)).andReturn(session);
        expect(session.getWorkspace()).andReturn(ws);
        expect(ws.getNamespaceRegistry()).andReturn(registry);

        // destroy
        expect(repo.login(null, null)).andReturn(session);
        expect(session.getWorkspace()).andReturn(ws);
        expect(ws.getNamespaceRegistry()).andReturn(registry);

        // registry record
        String[] prefixes = new String[]{foo};
        String oldURI = "old bar";
        expect(registry.getPrefixes()).andReturn(prefixes);
        expect(registry.getURI(foo)).andReturn(oldURI);
        registry.unregisterNamespace(foo);

        registry.registerNamespace(foo, "bar");
        registry.registerNamespace("hocus", "pocus");

        registry.unregisterNamespace("foo");
        registry.unregisterNamespace("hocus");
        registry.registerNamespace(foo, oldURI);

        session.logout();

        replay(registry, ws, session, repo);

        factory.afterPropertiesSet();
        factory.destroy();

        verify(registry, ws, session, repo);
    }

    @Test
    public void testKeepRegistryNamespace() throws Exception {
        Properties namespaces = new Properties();
        namespaces.put("foo", "bar");
        namespaces.put("hocus", "pocus");

        factory.setNamespaces(namespaces);
        factory.setKeepNewNamespaces(true);


        // afterPropertiesSet
        expect(repo.login(null, null)).andReturn( session);
        expect(session.getWorkspace()).andReturn( ws);
        expect(ws.getNamespaceRegistry()).andReturn( registry);

        expect(registry.getPrefixes()).andReturn( new String[0]);

        registry.registerNamespace("foo", "bar");
        registry.registerNamespace("hocus", "pocus");

        session.logout();

        replay(registry, ws, session, repo);

        factory.afterPropertiesSet();

        factory.destroy();

        verify(registry, ws, session, repo);
    }

    @Test
    public void testSkipRegisteredNamespaces() throws Exception {
        Properties namespaces = new Properties();
        namespaces.put("foo", "bar");
        namespaces.put("hocus", "pocus");

        factory.setNamespaces(namespaces);
        factory.setSkipExistingNamespaces(false);

        // afterPropertiesSet
        expect(repo.login(null, null)).andReturn( session);
        expect(session.getWorkspace()).andReturn( ws);
        expect(ws.getNamespaceRegistry()).andReturn( registry);

        registry.registerNamespace("foo", "bar");
        registry.registerNamespace("hocus", "pocus");

        expect(registry.getPrefixes()).andReturn( new String[0]);
        session.logout();
        
        replay(registry, ws, session, repo);

        factory.afterPropertiesSet();

        factory.destroy();

        verify(registry, ws, session, repo);
    }
    

    @Test
    public void testDefaultSesionHolder() throws Exception {
        factory.afterPropertiesSet();
        Session session = factory.getSession();
        SessionHolder holder = factory.getSessionHolder(session);
        assertSame(SessionHolder.class, holder.getClass());
        // default session holder provider
        assertSame(SessionHolder.class, factory.getSessionHolder(null)
                .getClass());
    }

    @Test
    public void testSessionHolder() throws Exception {
        final String REPO_NAME = "hocus_pocus";

        expect(repo.getDescriptor(Repository.REP_NAME_DESC)).andReturn(
                REPO_NAME);


        expect(repo.login(null, null)).andReturn( session);

        replay(repo, session);

        List providers = new ArrayList();

        providers.add(new SessionHolderProvider() {

            /**
             * @see org.springmodules.jcr.SessionHolderProvider#acceptsRepository(java.lang.String)
             */
            public boolean acceptsRepository(String repositoryName) {
                return REPO_NAME.equals(repositoryName);
            }

            /**
             * @see org.springmodules.jcr.SessionHolderProvider#createSessionHolder(javax.jcr.Session)
             */
            public SessionHolder createSessionHolder(Session session) {
                return new CustomSessionHolder(session);
            }

        });

        ListSessionHolderProviderManager providerManager = new ListSessionHolderProviderManager();
        providerManager.setProviders(providers);

        factory.setSessionHolderProviderManager(providerManager);
        factory.afterPropertiesSet();

        Session sess = factory.getSession();
        assertSame(session, sess);
        assertSame(CustomSessionHolder.class, factory.getSessionHolder(sess)
                .getClass());

        verify(repo, session);
    }

    /**
     * Used for testing.
     *
     * @author Costin Leau
     */
    private class CustomSessionHolder extends SessionHolder {

        /**
         * @param session session
         */
        public CustomSessionHolder(Session session) {
            super(session);

        }

    }

}
