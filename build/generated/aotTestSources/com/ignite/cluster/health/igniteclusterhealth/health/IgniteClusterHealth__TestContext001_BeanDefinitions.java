package com.ignite.cluster.health.igniteclusterhealth.health;

import com.ignite.cluster.health.igniteclusterhealth.service.IgniteClusterInfoService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link IgniteClusterHealth}
 */
public class IgniteClusterHealth__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'igniteClusterHealth'.
   */
  private static BeanInstanceSupplier<IgniteClusterHealth> getIgniteClusterHealthInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<IgniteClusterHealth>forConstructor(IgniteClusterInfoService.class)
            .withGenerator((registeredBean, args) -> new IgniteClusterHealth(args.get(0)));
  }

  /**
   * Get the bean definition for 'igniteClusterHealth'
   */
  public static BeanDefinition getIgniteClusterHealthBeanDefinition() {
    Class<?> beanType = IgniteClusterHealth.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getIgniteClusterHealthInstanceSupplier());
    return beanDefinition;
  }
}
