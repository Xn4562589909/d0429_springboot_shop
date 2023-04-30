package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.Property;
import com.iweb.d0429_springboot_shop.entity.PropertyValue;
import com.iweb.d0429_springboot_shop.mapper.PropertyMapper;
import com.iweb.d0429_springboot_shop.mapper.PropertyValueMapper;
import com.iweb.d0429_springboot_shop.service.PropertyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/3 21:41
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Resource
    private PropertyMapper propertyMapper;
    @Resource
    private PropertyValueMapper propertyValueMapper;

    @Override
    public List<Property> list(int cid) {
        return propertyMapper.listByCid(cid);
    }

    @Override
    public void add(Property property) {
        propertyMapper.add(property);
    }

    @Override
    public Property edit(int id) {
        return  propertyMapper.get(id);
    }

    @Override
    public void update(Property property) {
        propertyMapper.update(property);
    }

    @Override
    public void delete(int id) {
        List<PropertyValue> pvs = propertyValueMapper.listByPtid(id);
        for (PropertyValue pv:pvs) {
            propertyValueMapper.delete(pv.getId());
        }
        propertyMapper.delete(id);
    }

    @Override
    public List<Property> list() {
        return propertyMapper.list();
    }

    @Override
    public Property get(int id) {
        return propertyMapper.get(id);
    }

    @Override
    public int getTotalByCid(int cid) {
        return propertyMapper.getTotalByCid(cid);
    }
}
