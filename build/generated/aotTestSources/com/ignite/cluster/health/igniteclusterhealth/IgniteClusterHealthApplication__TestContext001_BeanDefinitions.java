package com.ignite.cluster.health.igniteclusterhealth;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link IgniteClusterHealthApplication}
 */
public class IgniteClusterHealthApplication__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'igniteClusterHealthApplication'
   */
  public static BeanDefinition getIgniteClusterHealthApplicationBeanDefinition() {
    Class<?> beanType = IgniteClusterHealthApplication.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(IgniteClusterHealthApplication.class);
    beanDefinition.setInstanceSupplier(IgniteClusterHealthApplication$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
