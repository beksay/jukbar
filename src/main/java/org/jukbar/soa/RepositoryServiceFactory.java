package org.jukbar.soa;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.jukbar.singleton.Configuration;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class RepositoryServiceFactory {
	
	private static RepositoryServiceFactory factory;
	private final RepositoryService service;
	
	private RepositoryServiceFactory() throws Exception {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	    Configuration configuration = Configuration.getInstance();

	    String host = configuration.getProperty("host");
	    String port = configuration.getProperty("port");
	    String serviceName = configuration.getProperty("serviceName");

	    if ((host == null) || (port == null) || (serviceName == null)) throw new Exception();

	    factory.setServiceClass(RepositoryService.class);
	    factory.setAddress("http://" + host + ":" + port + "/" + serviceName + "/" + RepositoryService.class.getSimpleName());
	    try
	    {
	      this.service = ((RepositoryService)factory.create());
	      
	      /*org.apache.cxf.endpoint.Client client = ClientProxy.getClient(service);
	      HTTPConduit httpConduit = (HTTPConduit)client.getConduit();
	      HTTPClientPolicy policy = httpConduit.getClient();
	      policy.setReceiveTimeout(0);
	      policy.setConnectionRequestTimeout(1000000);
	      policy.setConnectionTimeout(1000000);*/
	    } catch (Exception localException) {
	      throw new Exception();
	    }
	}
	
	public static RepositoryServiceFactory getInstance() throws Exception {
		if(factory == null) factory = new RepositoryServiceFactory();
		
		return factory;
	}
	
	public RepositoryService getService() {
		return service;
	}
	
}
