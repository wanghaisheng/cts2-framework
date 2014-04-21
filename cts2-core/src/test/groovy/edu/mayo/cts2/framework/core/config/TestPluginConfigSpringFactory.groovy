package edu.mayo.cts2.framework.core.config

import java.io.File;

import org.springframework.beans.factory.FactoryBean

import edu.mayo.cts2.framework.core.config.option.OptionHolder;
import edu.mayo.cts2.framework.core.plugin.PluginConfig;

class TestPluginConfigSpringFactory implements FactoryBean {
	
	private OptionHolder options;
	
	private File workDirectory;
	
	private ServerContext serverContext = new TestServerContext();

	@Override
	public Object getObject() throws Exception {
		new PluginConfig(options, workDirectory, serverContext)
	}

	@Override
	public Class getObjectType() {
		PluginConfig.class
	}

	@Override
	public boolean isSingleton() {
		true
	}		

}