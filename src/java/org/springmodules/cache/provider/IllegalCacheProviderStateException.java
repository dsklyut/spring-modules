/* 
 * Created on Nov 4, 2004
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

package org.springmodules.cache.provider;

/**
 * <p>
 * Exception thrown when an implementation of
 * <code>{@link CacheProviderFacade}</code> has not been configured properly.
 * </p>
 * 
 * @author Alex Ruiz
 * 
 * @version $Revision$ $Date$
 */
public class IllegalCacheProviderStateException extends CacheException {

  private static final long serialVersionUID = -5778844057206602130L;

  public IllegalCacheProviderStateException(String detailMessage) {
    super(detailMessage);
  }

  public IllegalCacheProviderStateException(String detailMessage,
      Throwable nestedException) {
    super(detailMessage, nestedException);
  }

}