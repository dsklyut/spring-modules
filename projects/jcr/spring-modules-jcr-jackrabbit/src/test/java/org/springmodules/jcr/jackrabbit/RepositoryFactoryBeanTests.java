package org.springmodules.jcr.jackrabbit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import javax.jcr.Repository;

public class RepositoryFactoryBeanTests {

	RepositoryFactoryBean factory;

    @Before
	public void setUp() throws Exception {
		factory = new RepositoryFactoryBean();
	}


	/*
	 * Test method for
	 * 'org.springmodules.jcr.jeceira.RepositoryFactoryBean.resolveConfigurationResource()'
	 */
    @Test
	public void testResolveConfigurationResource() throws Exception {

		factory.resolveConfigurationResource();
		DefaultResourceLoader loader = new DefaultResourceLoader();
		Resource res = loader.getResource("/repository.xml");
		assertEquals(res, factory.getConfiguration());
		assertEquals(".", factory.getHomeDir().getFilename());

	}

	/*
	 * Test method for
	 * 'org.springmodules.jcr.jeceira.RepositoryFactoryBean.createRepository()'
	 */
    @Test
	public void testCreateRepository() throws Exception {
		factory.afterPropertiesSet();
		Repository rep = (Repository) factory.getObject();
		assertEquals(rep.getDescriptor("jcr.repository.name"), "Jackrabbit");

		assertEquals(true, factory.getObject() instanceof Repository);
		assertEquals(true, factory.isSingleton());
		assertEquals(Repository.class, factory.getObjectType());
		factory.destroy();

	}
}
