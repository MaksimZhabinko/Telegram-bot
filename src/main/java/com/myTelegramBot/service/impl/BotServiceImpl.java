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
    public String addPlace(String msg) {
        int findChar = msg.indexOf('-');
        StringBuffer msgBuffer = new StringBuffer(msg);
        int startIndexForCity = 1;
        int endIndexForCity = findChar - 1;
        char[] bufferCity = new char[endIndexForCity - startIndexForCity];
        msgBuffer.getChars(startIndexForCity, endIndexForCity, bufferCity, 0);
        String city = new String(bufferCity);
        CityEntity cityEntity = cityRepository.findByCity(city.trim());

        if (cityEntity.getCity() != null) {
            int startIndexForPlace = findChar + 2;
            int endIndexForPlace = msgBuffer.length();
            char[] bufferPlace = new char[endIndexForPlace - startIndexForPlace];
            msgBuffer.getChars(startIndexForPlace, endIndexForPlace, bufferPlace, 0);
            String place = new String(bufferPlace);
            PlaceEntity placeEntity = placeRepository.findByCity(cityEntity);
            if (placeEntity == null) {
                PlaceEntity placeEntity1 = new PlaceEntity();
                placeEntity1.setPlace(place.trim());
                placeEntity1.setCity(cityEntity);
                placeRepository.save(placeEntity1);
                return "Ok";
            } else {
                return "Bad";
            }
        }
        return "Bad";
    }
}
