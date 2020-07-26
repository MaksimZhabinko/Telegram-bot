package com.myTelegramBot.service;

import com.myTelegramBot.dto.CityDto;
import com.myTelegramBot.dto.PlaceDto;

import java.util.List;

public interface BotService {
    /**
     * This method is used for get city from the base.
     * @param city information about city name.
     */
    CityDto findCity(String city);

    /**
     * This method is used for get place from the base.
     * @param city information about city name.
     */
    PlaceDto findPlaceFromCity(String city);

    /**
     * This method is used to get all cities from the base.
     */
    List<CityDto> getAllCity();

    /**
     * This method is used for delete city from the database.
     * @param city information about city name.
     */
    void deleteCity(String city);

    /**
     * This method is used to save the city to the database.
     * @param city information about city name.
     */
    void saveCity(String city);

    /**
     * This method is used to save the place to the database.
     * @param cityDto information about city.
     * @param place information about place.
     */
    void savePlace(CityDto cityDto, String place);

}
