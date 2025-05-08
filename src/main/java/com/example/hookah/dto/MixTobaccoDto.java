package com.example.hookah.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MixTobaccoDto {
    private Long tobaccoId;
    private String name;
    private String brand;
    private List<String> flavors;
    private Integer strength;
    private String description;
    private String imageUrl;
    private Integer proportion; // Пропорция табака в миксе
}