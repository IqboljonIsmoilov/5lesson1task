package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "room")
public class Room extends AbsLongEntity {

    @Column(unique = true)
    private Integer number;

    private String floor;

    private String size;

    @ManyToOne
    private Hotel hotel;
}
