/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springmodules.jcr.jackrabbit.ocm;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springmodules.jcr.jackrabbit.ocm.test.components.ArticleService;
import org.springmodules.jcr.jackrabbit.ocm.test.components.NewsService;
import org.springmodules.jcr.jackrabbit.ocm.test.model.Article;
import org.springmodules.jcr.jackrabbit.ocm.test.model.News;

import java.util.Date;
import java.util.Iterator;

/**
 * Test Mapper
 *
 * @author <a href="mailto:christophe.lombart@sword-technologies.com">Christophe
 *         Lombart</a>
 */
@ContextConfiguration(locations = {"classpath:applicationContext-repository.xml"})
public class SimpleTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void testComponents() {
        try {
            System.out.println("Init Spring");

            System.out.println("Add article");
            ArticleService service = (ArticleService) applicationContext
                    .getBean("org.springmodules.jcr.jackrabbit.ocm.test.components.ArticleService");
            Article article = new Article();
            article.setPath("/article");
            article.setAuthor("Christophe");
            article.setContent("This is an interesting content");
            article.setCreationDate(new Date());
            article.setDescription("This is the article description");
            article.setTitle("Article Title");

            service.createArticle(article);

            System.out.println("Check News");
            NewsService newsService = (NewsService) applicationContext
                    .getBean("org.springmodules.jcr.jackrabbit.ocm.test.components.NewsService");
            Iterator news = newsService.getNews().iterator();
            while (news.hasNext()) {
                News newsFound = (News) news.next();
                System.out.println("News found : " + newsFound.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }
}