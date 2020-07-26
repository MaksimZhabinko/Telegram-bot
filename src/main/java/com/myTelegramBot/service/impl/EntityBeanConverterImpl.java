package com.myTelegramBot.service.impl;


import com.myTelegramBot.service.EntityBeanConverter;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityBeanConverterImpl implements EntityBeanConverter {

    private Mapper mapper;

    @Autowired
    public EntityBeanConverterImpl(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <E, D> D converterToDto(E entity, Class<D> dtoClass) {
        D dto = mapper.map(entity, dtoClass);
        return dto;
    }

    @Override
    public <E, D> E converterToEntity(D dto, Class<E> entityClass) {
        E entity = mapper.map(dto, entityClass);
        return entity;
    }

    @Override
    public <E, D> List<D> converterToDtoList(Iterable<E> entities, Class<D> dtoClass) {
        List<D> dtos = new ArrayList<>();

        for (E entity : entities) {
            D dto = converterToDto(entity, dtoClass);
            dtos.add(dto);
        }

        return dtos;
    }
}

