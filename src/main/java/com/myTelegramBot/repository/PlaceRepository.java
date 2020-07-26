package com.myTelegramBot.repository;

import com.myTelegramBot.entities.CityEntity;
import com.myTelegramBot.entities.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, Integer> {
    PlaceEntity findByCity (CityEntity city);

}
