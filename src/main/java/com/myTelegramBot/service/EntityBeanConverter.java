package com.myTelegramBot.service;

import java.util.List;

public interface EntityBeanConverter {

    <E, D> D converterToDto(E entity, Class<D> dtoClass);

    <E, D> E converterToEntity(D dto, Class<E> entityClass);

    <E, D> List<D> converterToDtoList(Iterable<E> entities, Class<D> beanClass);
}
