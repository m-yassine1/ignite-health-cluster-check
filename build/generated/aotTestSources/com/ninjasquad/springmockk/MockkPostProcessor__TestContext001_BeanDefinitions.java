package com.ninjasquad.springmockk;

import java.lang.Class;
import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link MockkPostProcessor}
 */
public class MockkPostProcessor__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'com.ninjasquad.springmockk.MockkPostProcessor'.
   */
  private static BeanInstanceSupplier<MockkPostProcessor> getMockkPostProcessorInstanceSupplier() {
    return BeanInstanceSupplier.<MockkPostProcessor>forConstructor(Set.class)
            .withGenerator((registeredBean, args) -> new MockkPostProcessor(args.get(0)));
  }

  /**
   * Get the bean definition for 'mockkPostProcessor'
   */
  public static BeanDefinition getMockkPostProcessorBeanDefinition() {
    Class<?> beanType = MockkPostProcessor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, Collections.emptySet());
    beanDefinition.setInstanceSupplier(getMockkPostProcessorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link MockkPostProcessor.SpyPostProcessor}
   */
  public static class SpyPostProcessor__BeanDefinitions {
    /**
     * Get the bean instance supplier for 'com.ninjasquad.springmockk.MockkPostProcessor$SpyPostProcessor'.
     */
    private static BeanInstanceSupplier<MockkPostProcessor.SpyPostProcessor> getSpyPostProcessorInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<MockkPostProcessor.SpyPostProcessor>forConstructor(MockkPostProcessor.class)
              .withGenerator((registeredBean, args) -> new MockkPostProcessor.SpyPostProcessor(args.get(0)));
    }

    /**
     * Get the bean definition for 'spyPostProcessor'
     */
    public static BeanDefinition getSpyPostProcessorBeanDefinition() {
      Class<?> beanType = MockkPostProcessor.SpyPostProcessor.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
      beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, new RuntimeBeanReference("com.ninjasquad.springmockk.MockkPostProcessor"));
      beanDefinition.setInstanceSupplier(getSpyPostProcessorInstanceSupplier());
      return beanDefinition;
    }
  }
}
