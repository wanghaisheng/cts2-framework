/*
 * Copyright: (c) 2004-2011 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
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
package edu.mayo.cts2.framework.webapp.rest.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import edu.mayo.cts2.framework.service.profile.Cts2Profile;
import edu.mayo.cts2.framework.service.provider.ServiceProvider;
import edu.mayo.cts2.framework.service.provider.ServiceProviderChangeObserver;
import edu.mayo.cts2.framework.service.provider.ServiceProviderFactory;

/**
 * The Class AbstractServiceAwareController.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class AbstractServiceAwareController extends
		AbstractMessageWrappingController implements InitializingBean, ServiceProviderChangeObserver {

	private static Log log = LogFactory
			.getLog(AbstractServiceAwareController.class);

	@Resource
	private ServiceProviderFactory serviceProviderFactory;

	/**
	 * The Interface Cts2Service.
	 * 
	 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
	 */
	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Cts2Service {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		this.serviceProviderFactory.registerListener(this);

		this.loadServices();
	}

	/**
	 * Load services.
	 */
	protected void loadServices() {
		
		ServiceProvider serviceProvider = this.serviceProviderFactory
				.getServiceProvider();


				for (Field field : this.getClass().getDeclaredFields()) {
					if (field.isAnnotationPresent(Cts2Service.class)) {

						@SuppressWarnings("unchecked")
						Class<? extends Cts2Profile> clazz = (Class<? extends Cts2Profile>) field
								.getType();

						Cts2Profile service = serviceProvider.getService(clazz);

						field.setAccessible(true);

						try {
							field.set(this, service);
						} catch (Exception e) {
							throw new IllegalStateException(e);
						}

						log.info("Setting service: " + field.getType()
								+ " on: " + this.getClass().getName());
					}
				}
			}

	@Override
	public void onServiceProviderChange() {
		this.loadServices();
	}
}