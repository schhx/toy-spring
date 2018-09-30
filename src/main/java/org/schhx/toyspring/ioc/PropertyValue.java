package org.schhx.toyspring.ioc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author shanchao
 * @date 2018-09-30
 */
@AllArgsConstructor
@Getter
public class PropertyValue {

    private final String name;

    private final Object value;

}
