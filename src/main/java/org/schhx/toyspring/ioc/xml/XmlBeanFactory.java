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
        bean = applyBeanPostProcessorsBeforeInitialization(bean, definition.getBeanName());
        processInit(bean);
        bean = applyBeanPostProcessorsAfterInitialization(bean, definition.getBeanName());
        definition.setBean(bean);
        return bean;
    }

    private void processProperties(Object bean, BeanDefinition definition) throws Exception {
        for (PropertyValue pv : definition.getPropertyValues().getPropertyValueList()) {
            Object value = pv.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }

            try {
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + pv.getName().substring(0, 1).toUpperCase()
                                + pv.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);

                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                Field declaredField = bean.getClass().getDeclaredField(pv.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }
    }

    private void processAware(Object bean) throws Exception {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }

    private void processInit(Object bean) throws Exception {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
    }

    private Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws Exception {
        for (BeanPostProcessor processor : beanPostProcessors) {
            bean = processor.postProcessBeforeInitialization(bean, beanName);
        }
        return bean;
    }

    private Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws Exception {
        for (BeanPostProcessor processor : beanPostProcessors) {
            bean = processor.postProcessAfterInitialization(bean, beanName);
        }
        return bean;
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
