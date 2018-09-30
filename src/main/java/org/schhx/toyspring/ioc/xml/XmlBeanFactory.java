package org.schhx.toyspring.ioc.xml;

import org.schhx.toyspring.ioc.*;
import org.schhx.toyspring.ioc.factory.BeanFactory;
import org.schhx.toyspring.ioc.factory.BeanFactoryAware;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public class XmlBeanFactory implements BeanFactory {

    private BeanDefinitionReader beanDefinitionReader;

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private Map<String, BeanDefinition> beanDefinitions;

    public XmlBeanFactory(String location) throws Exception {
        beanDefinitionReader = new XmlBeanDefinitionReader();
        loadBeanDefinitions(location);
    }


    @Override
    public Object getBean(String name) throws Exception {
        if (!beanDefinitions.containsKey(name)) {
            throw new IllegalArgumentException("there is no bean with name " + name);
        }
        BeanDefinition definition = beanDefinitions.get(name);
        Object bean = definition.getBean();

        return bean != null ? bean : loadBean(definition);
    }

    private Object loadBean(BeanDefinition definition) throws Exception {
        Object bean = definition.getBeanClass().newInstance();
        processProperties(bean, definition);
        processAware(bean);
        beforeInit(bean, definition.getBeanName());
        processInit(bean);
        afterInit(bean, definition.getBeanName());
        definition.setBean(bean);
        return bean;
    }

    private void processProperties(Object bean, BeanDefinition definition) throws Exception {
        for (PropertyValue pv : definition.getPropertyValues().getPropertyValueList()) {
            String fieldName = pv.getName();
            Field field = bean.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (pv.getValue() instanceof BeanReference) {
                BeanReference ref = (BeanReference) pv.getValue();
                field.set(bean, getBean(ref.getBeanName()));
            } else {
                field.set(bean, pv.getValue());
            }
        }
    }

    private void processAware(Object bean) throws Exception {
        if (bean instanceof BeanFactoryAware) {
            Method method = bean.getClass().getDeclaredMethod("setBeanFactory", BeanFactory.class);
            method.invoke(bean, this);
        }
    }

    private void processInit(Object bean) throws Exception {
        if (bean instanceof InitializingBean) {
            Method method = bean.getClass().getDeclaredMethod("afterPropertiesSet");
            method.invoke(bean);
        }
    }

    private void beforeInit(Object bean, String beanName) throws Exception {
        for (BeanPostProcessor processor : beanPostProcessors) {
            processor.postProcessBeforeInitialization(bean, beanName);
        }
    }

    private void afterInit(Object bean, String beanName) throws Exception {
        for (BeanPostProcessor processor : beanPostProcessors) {
            processor.postProcessAfterInitialization(bean, beanName);
        }
    }


    private void loadBeanDefinitions(String location) throws Exception {
        this.beanDefinitions = beanDefinitionReader.loadBeanDefinitions(location);
        registerBeanPostProcessor();
    }

    public void registerBeanPostProcessor() throws Exception {
        List beans = getBeansByType(BeanPostProcessor.class);
        for (Object bean : beans) {
            addBeanPostProcessor((BeanPostProcessor) bean);
        }
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    public List getBeansByType(Class type) throws Exception {
        List beans = new ArrayList<>();

        for(Map.Entry<String, BeanDefinition> entry : beanDefinitions.entrySet()) {
            if(type.isAssignableFrom(entry.getValue().getBeanClass())) {
                beans.add(getBean(entry.getKey()));
            }
        }

        return beans;
    }
}
