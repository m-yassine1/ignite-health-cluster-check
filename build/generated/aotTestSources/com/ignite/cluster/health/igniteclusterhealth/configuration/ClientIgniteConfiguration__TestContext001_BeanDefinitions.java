package com.ignite.cluster.health.igniteclusterhealth.configuration;

import java.lang.Class;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link ClientIgniteConfiguration}
 */
public class ClientIgniteConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'clientIgniteConfiguration'
   */
  public static BeanDefinition getClientIgniteConfigurationBeanDefinition() {
    Class<?> beanType = ClientIgniteConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(ClientIgniteConfiguration.class);
    beanDefinition.setInstanceSupplier(ClientIgniteConfiguration$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'igniteClientConfiguration'.
   */
  private static BeanInstanceSupplier<IgniteConfiguration> getIgniteClientConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<IgniteConfiguration>forFactoryMethod(ClientIgniteConfiguration.class, "igniteClientConfiguration")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(ClientIgniteConfiguration.class).igniteClientConfiguration());
  }

  /**
   * Get the bean definition for 'igniteClientConfiguration'
   */
  public static BeanDefinition getIgniteClientConfigurationBeanDefinition() {
    Class<?> beanType = IgniteConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getIgniteClientConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'igniteClient'.
   */
  private static BeanInstanceSupplier<Ignite> getIgniteClientInstanceSupplier() {
    return BeanInstanceSupplier.<Ignite>forFactoryMethod(ClientIgniteConfiguration.class, "igniteClient", IgniteConfiguration.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ClientIgniteConfiguration.class).igniteClient(args.get(0)));
  }

  /**
   * Get the bean definition for 'igniteClient'
   */
  public static BeanDefinition getIgniteClientBeanDefinition() {
    Class<?> beanType = Ignite.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setDestroyMethodNames("close");
    beanDefinition.setInstanceSupplier(getIgniteClientInstanceSupplier());
    return beanDefinition;
  }
}
