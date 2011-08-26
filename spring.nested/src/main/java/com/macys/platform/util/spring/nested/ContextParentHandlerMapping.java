package com.macys.platform.util.spring.nested;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @deprecated obsolete solution for map handlers from the nested contexts
 * use {@link ContextParentAnnotationHandlerMapping} instead 
 */
public class ContextParentHandlerMapping extends BeanNameUrlHandlerMapping {
	
	private Registry registry;
	private List<Class<?>> handlerClasses = Arrays.asList(HttpRequestHandler.class, Controller.class);

	protected void detectHandlers()
			throws org.springframework.beans.BeansException {
		for (Class<?> clazz : handlerClasses) {
			for (String controllerName : registry.lookupByInterface(clazz)) {
				String[] urls = determineUrlsForHandler(controllerName);
				if (!ObjectUtils.isEmpty(urls)) {
					// URL paths found: Let's consider it a handler.
					registerHandler(urls,  registry.lookup(
							controllerName, clazz));
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Rejected bean name '" + controllerName
								+ "': no URL paths identified");
					}
				}
			}
		}
	}

	private void registerHandler(String[] urlPaths, Object lookup) {
		Assert.notNull(urlPaths, "URL path array must not be null");
		for (int j = 0; j < urlPaths.length; j++) {
			registerHandler(urlPaths[j], lookup);
		}
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry rootContext) {
		this.registry = rootContext;
	}

	public List<Class<?>> getHandlerClasses() {
		return handlerClasses;
	}

	public void setHandlerClasses(List<Class<?>> handlerClasses) {
		this.handlerClasses = handlerClasses;
	};
	
}
