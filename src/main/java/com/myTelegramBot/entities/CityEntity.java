package com.myTelegramBot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "city")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city")
    private String city;

    @OneToOne(mappedBy = "city", fetch = FetchType.LAZY)
    private PlaceEntity place;
}
