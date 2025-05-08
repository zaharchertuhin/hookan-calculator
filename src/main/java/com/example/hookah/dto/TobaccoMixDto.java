package com.example.hookah.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TobaccoMixDto {
    private Long id;
    private String name;
    private String description;
    private List<MixTobaccoDto> tobaccos;
}
