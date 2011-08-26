package com.macys.platform.util.spring.nested;

import java.util.Collection;


/**
 * Registry for export and import services by name with a constraint by 
 * an interface.   
 * The singleton bean implements this interface instantiated by the root context and available 
 * for the nested children context via intrinsic Spring feature "parent context". 
 */
public interface Registry {
	/** 
	 * export the given service reference
	 * */
	Void export(ExportRef ref);
	/**
	 * imports a service by name
	 * @param name key for find a service. usually camel case name used during export
	 * @param clazz expected interface for the service. should be match with the interface used during an export
	 * @return proxy for the requested service
	 * */
	<T> T lookup(final String name, final Class<T> clazz);
	/**
	 * allows to enumerate services for an interface
	 * @param clazz requested interface
	 * @return collection of services' keys which declares a requested interface
	 * @deprecated it was used for handler mapper v1 shouldn't be used now */
	<T> Collection<String> lookupByInterface(Class<T> clazz);
}