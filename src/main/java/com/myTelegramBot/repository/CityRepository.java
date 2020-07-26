package com.myTelegramBot.repository;

import com.myTelegramBot.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
    CityEntity findByCity(String city);

    void deleteByCity(String city);
}
