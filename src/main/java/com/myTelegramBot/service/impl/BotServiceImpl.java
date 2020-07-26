package com.myTelegramBot.service.impl;

import com.myTelegramBot.dto.CityDto;
import com.myTelegramBot.dto.PlaceDto;
import com.myTelegramBot.entities.CityEntity;
import com.myTelegramBot.entities.PlaceEntity;
import com.myTelegramBot.repository.CityRepository;
import com.myTelegramBot.repository.PlaceRepository;
import com.myTelegramBot.service.BotService;
import com.myTelegramBot.service.EntityBeanConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BotServiceImpl implements BotService {
    private CityRepository cityRepository;
    private PlaceRepository placeRepository;
    private EntityBeanConverter converter;

    @Autowired
    public BotServiceImpl(CityRepository cityRepository, PlaceRepository placeRepository,
                          EntityBeanConverter converter) {
        this.cityRepository = cityRepository;
        this.placeRepository = placeRepository;
        this.converter = converter;
    }

    @Override
    public CityDto findCity(String city) {
        CityEntity cityEntity = cityRepository.findByCity(city);
        if (cityEntity != null) {
            CityDto cityDto = converter.converterToDto(cityEntity, CityDto.class);
            return cityDto;
        }
        return null;
    }

    @Override
    public PlaceDto findPlaceFromCity(String city) {
        CityEntity cityEntity = cityRepository.findByCity(city);
        PlaceEntity placeEntity = placeRepository.findByCity(cityEntity);
        if (placeEntity != null) {
            PlaceDto placeDto = converter.converterToDto(placeEntity, PlaceDto.class);
            return placeDto;
        }
        return null;
    }

    @Override
    public List<CityDto> getAllCity() {
        List<CityEntity> cityEntities = cityRepository.findAll();
        if (cityEntities != null) {
            List<CityDto> cityDtos = converter.converterToDtoList(cityEntities, CityDto.class);
            return cityDtos;
        }
        return null;
    }

    @Override
    public void deleteCity(String city) {
        cityRepository.deleteByCity(city);
    }

    @Override
    public void saveCity(String city) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setCity(city);
        cityRepository.save(cityEntity);
    }

    @Override
    public void savePlace(CityDto cityDto, String place) {
        CityEntity cityEntity = converter.converterToEntity(cityDto, CityEntity.class);
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setPlace(place);
        placeEntity.setCity(cityEntity);
        placeRepository.save(placeEntity);
    }

}
