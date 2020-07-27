package com.myTelegramBot.controller;

import com.myTelegramBot.dto.CityDto;
import com.myTelegramBot.dto.PlaceDto;
import com.myTelegramBot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BotController {
    private BotService service;

    @Autowired
    public BotController(BotService service) {
        this.service = service;
    }

    public List<CityDto> getAllCity () {
        List<CityDto> cityDtos = service.getAllCity();
        return cityDtos;
    }

    public PlaceDto findPlaceFromCity (String city) {
        PlaceDto placeDto = service.findPlaceFromCity(city);
        return placeDto;
    }

    public void DeleteCity(String city) {
        service.deleteCity(city);
    }

    public void saveCity(String city) {
        service.saveCity(city);
    }

    public String addPlace(String msg) {
        return service.addPlace(msg);
    }
}
