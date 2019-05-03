package com.engine.beanFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanFactoryHelper implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;
	
	private BeanFactoryHelper(){}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanFactoryHelper.applicationContext = applicationContext;
	}

	public static BeanFactory getBeanfactory() {
		return applicationContext;
	}
	
	public static Object getBean(String serviceName)
	{
		if(applicationContext == null)
		{
			getBeanfactory();
		}
		return applicationContext.getBean(serviceName);
	}
}
