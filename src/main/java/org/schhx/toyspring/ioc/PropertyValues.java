package org.schhx.toyspring.ioc;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shanchao
 * @date 2018-09-30
 */
@Getter
public class PropertyValues {

    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<>();
    }

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }
}
