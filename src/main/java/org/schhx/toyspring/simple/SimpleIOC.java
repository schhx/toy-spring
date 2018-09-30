package org.schhx.toyspring.simple;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public class SimpleIOC {

    /**
     * 存放 bean 定义
     */
    private Map<String, Element> beanDefinitions = new HashMap<>();

    /**
     * 存放 bean 的实例化
     */
    private Map<String, Object> beanMap = new HashMap<>();

    public SimpleIOC(String location) throws Exception {
        loadBeanDefinitions(location);
    }

    private void loadBeanDefinitions(String location) throws Exception {
        // 加载 xml 配置文件
        InputStream inputStream = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();

        // 遍历 <bean></bean> 标签
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String id = ele.getAttribute("id");
                beanDefinitions.put(id, ele);
            }
        }
    }

    public Object getBean(String name) throws Exception {
        Object bean = beanMap.get(name);
        if (bean == null) {
            bean = loadBean(name);
        }

        return bean;
    }

    private Object loadBean(String beanName) throws Exception {
        Element ele = beanDefinitions.get(beanName);
        if (ele == null) {
            throw new IllegalArgumentException("there is no bean with name " + beanName);
        }

        String id = ele.getAttribute("id");
        String className = ele.getAttribute("class");
        Class beanClass = null;
        try {
            beanClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        Object bean = beanClass.newInstance();

        // 处理 bean property 属性
        NodeList propertyNodes = ele.getElementsByTagName("property");
        for (int j = 0; j < propertyNodes.getLength(); j++) {
            Node propertyNode = propertyNodes.item(j);
            if (propertyNode instanceof Element) {
                Element propertyElement = (Element) propertyNode;
                String name = propertyElement.getAttribute("name");
                String value = propertyElement.getAttribute("value");

                Field declaredField = bean.getClass().getDeclaredField(name);
                declaredField.setAccessible(true);

                if (value != null && value.length() > 0) {
                    declaredField.set(bean, value);
                } else {
                    String ref = propertyElement.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("ref config error");
                    }

                    declaredField.set(bean, getBean(ref));
                }

                // 将 bean 注册到 bean 容器中
                registerBean(id, bean);
            }
        }

        return bean;
    }

    private void registerBean(String id, Object bean) {
        beanMap.put(id, bean);
    }
}
