/*
 * Copyright 2002-2006 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springmodules.jcr.jackrabbit.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springmodules.jcr.jackrabbit.LocalTransactionManager;
import org.springmodules.jcr.jackrabbit.RepositoryFactoryBean;

/**
 * 
 * @author Costin Leau
 */
public class JackrabbitNamespaceHandlerTests {

	private BeanDefinitionRegistry beanFactory;

    @Before
    public void before() {
        this.beanFactory = new XmlBeanFactory(
				new ClassPathResource(
                        "/org/springmodules/jcr/jackrabbit/config/jackrabbitNamespaceHandlerTests.xml",
						getClass()));
    }

	private void assertPropertyValue(GenericBeanDefinition beanDefinition,
			String propertyName, Object expectedValue) {
		assertEquals("Property [" + propertyName + "] incorrect.",
				expectedValue, beanDefinition.getPropertyValues()
						.getPropertyValue(propertyName).getValue());
	}

    @Test
	public void testMinimalDefinition() throws Exception {
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) this.beanFactory
				.getBeanDefinition("minimal");
		assertSame(RepositoryFactoryBean.class, beanDefinition.getBeanClass());
		assertPropertyValue(beanDefinition, "configuration",
				"classpath:config.xml");
	}

    @Test
	public void testExtendedDefinition() throws Exception {
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) this.beanFactory
				.getBeanDefinition("extended");
		assertSame(RepositoryFactoryBean.class, (beanDefinition.getBeanClass()));
		assertPropertyValue(beanDefinition, "configuration", "file:config.xml");
		assertPropertyValue(beanDefinition, "homeDir", "file:///homeDir");
	}

    @Test
	public void testFullDefinition() throws Exception {
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) this.beanFactory
				.getBeanDefinition("full");
		assertSame(RepositoryFactoryBean.class, (beanDefinition.getBeanClass()));
		assertPropertyValue(beanDefinition, "homeDir", "file:///homeDir");
		assertPropertyValue(beanDefinition, "repositoryConfig", "repoCfg");
	}

    @Test
	public void testTransactionManager() throws Exception {
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) this.beanFactory
				.getBeanDefinition("transactionManager");
		assertSame(LocalTransactionManager.class, (beanDefinition
				.getBeanClass()));
		assertPropertyValue(beanDefinition, "sessionFactory",
				"jcrSessionFactory");
	}
}
