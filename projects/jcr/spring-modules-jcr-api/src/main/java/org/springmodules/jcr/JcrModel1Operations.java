package org.springmodules.jcr;

import org.xml.sax.ContentHandler;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.ValueFactory;
import javax.jcr.query.QueryResult;
import java.util.List;
import java.util.Map;

/**
 * Interface used for delimiting Jcr operations based on what the underlying
 * repository supports (in this case model 1 operations).
 * <p/>
 * Normally not used but useful for casting to restrict access in some
 * situations.
 *
 * @author Costin Leau
 */
public interface JcrModel1Operations {

    /**
     * @see javax.jcr.Session#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name);

    /**
     * @see javax.jcr.Session#getAttributeNames()
     */
    public String[] getAttributeNames();

    /**
     * @see javax.jcr.Session#getImportContentHandler(java.lang.String, int)
     */
    public ContentHandler getImportContentHandler(String parentAbsPath, int uuidBehavior);

    /**
     * @see javax.jcr.Session#getItem(java.lang.String)
     */
    public Item getItem(String absPath);

    /**
     * @see javax.jcr.Session#getNamespacePrefix(java.lang.String)
     */
    public String getNamespacePrefix(String uri);

    /**
     * @see javax.jcr.Session#getNamespacePrefixes()
     */
    public String[] getNamespacePrefixes();

    /**
     * @see javax.jcr.Session#getNamespaceURI(java.lang.String)
     */
    public String getNamespaceURI(String prefix);

    /**
     * @see javax.jcr.Session#getNodeByUUID(java.lang.String)
     */
    public Node getNodeByUUID(String uuid);

    /**
     * @see javax.jcr.Session#getRootNode();
     */
    public Node getRootNode();

    /**
     * @see javax.jcr.Session#getUserID()
     */
    public String getUserID();

    /**
     * @see javax.jcr.Session#getValueFactory()
     */
    public ValueFactory getValueFactory();

    /**
     * @see javax.jcr.Session#isLive()
     */
    public boolean isLive();

    /**
     * @see javax.jcr.Session#itemExists(java.lang.String)
     */
    public boolean itemExists(String absPath);

    /**
     * Execute a persistent query from the given node.
     *
     * @param node node to be dumped
     * @return query result
     * @see javax.jcr.query.QueryManager#getQuery(javax.jcr.Node)
     */
    public QueryResult query(Node node);

    /**
     * Execute a query with the given strings with XPATH as default language.
     * It's the same as #query(java.lang.String, java.lang.String)
     *
     * @param statement query statement
     * @return query result
     * @see javax.jcr.query.QueryManager#createQuery(java.lang.String,
     *      java.lang.String)
     */
    public QueryResult query(String statement);

    /**
     * Execute a query with the given strings.
     *
     * @param statement query statement
     * @param language  language statement
     * @return query result
     * @see javax.jcr.query.QueryManager#createQuery(java.lang.String,
     *      java.lang.String)
     */
    public QueryResult query(String statement, String language);

    /**
     * Default method for doing multiple queries. It assumes the language is
     * XPATH and that errors will not be ignored.
     *
     * @param list a list of queries that will be executed against the repository
     * @return a map containing the queries as keys and results as values
     */
    public Map<String, QueryResult> query(final List list);

    /**
     * Utility method for executing a list of queries against the repository.
     * Reads the queries given and returns the results in a map.
     *
     *
     * @param list         list of queries
     * @param language     language of the queries. If null XPATH is assumed.
     * @param ignoreErrors if true it will populate unfound nodes with null
     * @return a map containing the queries as keys and results as values
     */
    public Map<String, QueryResult> query(final List list, final String language,
			final boolean ignoreErrors);

}