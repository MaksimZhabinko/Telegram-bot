package com.myTelegramBot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "place")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "place")
    private String place;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_city_id", referencedColumnName = "id")
    private CityEntity city;
}
