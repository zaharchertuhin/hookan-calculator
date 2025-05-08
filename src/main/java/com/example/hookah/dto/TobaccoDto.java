package com.example.hookah.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TobaccoDto {
    private Long id;
    private String name;
    private String brand;
    private String flavors;
    private Integer strength;
    private String description;
    private String imageUrl;
}
