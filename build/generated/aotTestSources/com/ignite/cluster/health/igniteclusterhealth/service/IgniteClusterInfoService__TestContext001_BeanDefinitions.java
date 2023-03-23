package com.ignite.cluster.health.igniteclusterhealth.service;

import java.lang.Class;
import org.apache.ignite.Ignite;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link IgniteClusterInfoService}
 */
public class IgniteClusterInfoService__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'igniteClusterInfoService'.
   */
  private static BeanInstanceSupplier<IgniteClusterInfoService> getIgniteClusterInfoServiceInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<IgniteClusterInfoService>forConstructor(Ignite.class)
            .withGenerator((registeredBean, args) -> new IgniteClusterInfoService(args.get(0)));
  }

  /**
   * Get the bean definition for 'igniteClusterInfoService'
   */
  public static BeanDefinition getIgniteClusterInfoServiceBeanDefinition() {
    Class<?> beanType = IgniteClusterInfoService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getIgniteClusterInfoServiceInstanceSupplier());
    return beanDefinition;
  }
}
