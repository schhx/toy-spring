package org.schhx.toyspring.ioc.xml;

import org.schhx.toyspring.ioc.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public Map<String, BeanDefinition> loadBeanDefinitions(String location) throws Exception {
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
                beanDefinitions.put(id, build(ele));
            }
        }

        return beanDefinitions;
    }

    private BeanDefinition build(Element ele) {
        String beanName = ele.getAttribute("id");
        String className = ele.getAttribute("class");

        PropertyValues propertyValues = new PropertyValues();

        NodeList propertyNodes = ele.getElementsByTagName("property");
        for (int j = 0; j < propertyNodes.getLength(); j++) {
            Node propertyNode = propertyNodes.item(j);
            if (propertyNode instanceof Element) {
                Element propertyElement = (Element) propertyNode;
                String name = propertyElement.getAttribute("name");
                String value = propertyElement.getAttribute("value");

                // value
                if(value != null && !value.isEmpty()) {
                    propertyValues.addPropertyValue(new PropertyValue(name, value));
                } else {
                    String ref = propertyElement.getAttribute("ref");
                    propertyValues.addPropertyValue(new PropertyValue(name, new BeanReference(ref)));
                }


            }
        }

        return new BeanDefinition()
                .setBeanName(beanName)
                .setBeanClass(className)
                .setPropertyValues(propertyValues);
    }

}
