package com.ignite.cluster.health.igniteclusterhealth;

import java.lang.Override;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

/**
 * {@link ApplicationContextInitializer} to restore an application context based on previous AOT processing.
 */
public class IgniteClusterHealthApplicationTests__TestContext001_ApplicationContextInitializer implements ApplicationContextInitializer<GenericApplicationContext> {
  @Override
  public void initialize(GenericApplicationContext applicationContext) {
    DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();
    beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
    beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
    new IgniteClusterHealthApplicationTests__TestContext001_BeanFactoryRegistrations().registerBeanDefinitions(beanFactory);
    new IgniteClusterHealthApplicationTests__TestContext001_BeanFactoryRegistrations().registerAliases(beanFactory);
  }
}
