package com.ust.pos.service;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public abstract class BaseService {

    protected <T> Example<T> buildGlobalSearchExample(
            Class<T> clazz,
            String keyword) {

        try {

            T probe = clazz.getDeclaredConstructor().newInstance();
            BeanWrapper beanWrapper = new BeanWrapperImpl(probe);

            for (PropertyDescriptor propertyDescriptor : beanWrapper.getPropertyDescriptors()) {

                if (beanWrapper.isWritableProperty(propertyDescriptor.getName())
                        && propertyDescriptor.getPropertyType().equals(String.class)) {

                    beanWrapper.setPropertyValue(propertyDescriptor.getName(), keyword);
                }
            }

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withIgnoreNullValues()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnorePaths(
                            "id",
                            "createdOn",
                            "modifiedOn",
                            "createdBy",
                            "modifiedBy",
                            "status",
                            "deleted"
                    );

            return Example.of(probe, matcher);

        } catch (NoSuchMethodException
                 | InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException e) {
            throw new IllegalStateException("Failed to build global search example", e);
        }
    }
}