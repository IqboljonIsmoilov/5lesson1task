package com.company.payload;

import com.company.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Long id;

    private Integer number;

    private String floor;

    private String size;

    private Hotel hotel;
}
