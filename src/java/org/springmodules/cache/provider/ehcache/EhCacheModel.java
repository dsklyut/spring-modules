/* 
 * Created on Oct 29, 2004
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2004 the original author or authors.
 */

package org.springmodules.cache.provider.ehcache;

import org.springmodules.cache.provider.CacheModel;
import org.springmodules.util.Strings;

/**
 * <p>
 * Configuration options needed to access EHCache.
 * 
 * @author Alex Ruiz
 * 
 * @version $Revision$ $Date$
 */
public class EhCacheModel implements CacheModel {

  private static final long serialVersionUID = 3762529035888112945L;

  /**
   * Name of the EHCache cache.
   */
  private String cacheName;

  public EhCacheModel() {
    super();
  }

  public EhCacheModel(String cacheName) {
    this();
    setCacheName(cacheName);
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof EhCacheModel)) {
      return false;
    }

    final EhCacheModel cacheModel = (EhCacheModel) obj;

    if (cacheName != null ? !cacheName.equals(cacheModel.cacheName)
        : cacheModel.cacheName != null) {
      return false;
    }

    return true;
  }

  public final String getCacheName() {
    return cacheName;
  }

  /**
   * @see Object#hashCode()
   */
  public int hashCode() {
    int multiplier = 31;
    int hash = 7;
    hash = multiplier * hash + (cacheName != null ? cacheName.hashCode() : 0);
    return hash;
  }

  public final void setCacheName(String newCacheName) {
    cacheName = newCacheName;
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer(getClass().getName());
    buffer.append("@" + System.identityHashCode(this) + "[");
    buffer.append("cacheName=" + Strings.quote(cacheName) + "]");
    return buffer.toString();
  }
}