package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.Property;
import com.iweb.d0429_springboot_shop.entity.PropertyValue;
import com.iweb.d0429_springboot_shop.mapper.PropertyValueMapper;
import com.iweb.d0429_springboot_shop.service.PropertyValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/4 2:28
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Resource
    private PropertyValueMapper propertyValueMapper;

    @Override
    public List<PropertyValue> list(int pid) {
        return propertyValueMapper.listByPid(pid);
    }

    @Override
    public PropertyValue get(int id) {
        return propertyValueMapper.get(id);
    }

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.update(pv);
    }

    @Override
    public void add(PropertyValue pv) {
        propertyValueMapper.add(pv);
    }

    @Override
    public List<Property> listNotAddPt(List<Property> properties, List<PropertyValue> propertyValues) {
        List<Property> properties1 = new ArrayList<>();
        for (PropertyValue pv:propertyValues) {
            properties1.add(pv.getProperty());
        }
        properties.removeAll(properties1);
        return properties;
    }

    @Override
    public void delete(int id) {
        propertyValueMapper.delete(id);
    }
}
