package com.example.hookah.service;

import com.example.hookah.dto.MixTobaccoDto;
import com.example.hookah.dto.TobaccoMixDto;
import com.example.hookah.model.MixTobacco;
import com.example.hookah.model.Tobacco;
import com.example.hookah.model.TobaccoMix;
import com.example.hookah.repository.TobaccoMixRepository;
import com.example.hookah.repository.TobaccoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TobaccoMixService {

    private final TobaccoMixRepository mixRepository;
    private final TobaccoRepository tobaccoRepository;

    public List<TobaccoMixDto> getAll() {
        return mixRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TobaccoMixDto getById(Long id) {
        TobaccoMix mix = mixRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mix not found with id: " + id));
        return toDto(mix);
    }

    public TobaccoMixDto create(TobaccoMixDto dto) {
        if (dto.getTobaccos().size() < 2 || dto.getTobaccos().size() > 4) {
            throw new IllegalArgumentException("Mix must include between 2 and 4 tobaccos.");
        }

        int totalProportion = dto.getTobaccos().stream()
                .mapToInt(MixTobaccoDto::getProportion)
                .sum();
        if (totalProportion != 100) {
            throw new IllegalArgumentException("Total proportion must be exactly 100%.");
        }

        TobaccoMix mix = TobaccoMix.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        List<MixTobacco> mixTobaccos = dto.getTobaccos().stream().map(tobaccoDto -> {
            Tobacco tobacco = tobaccoRepository.findById(tobaccoDto.getTobaccoId())
                    .orElseThrow(() -> new EntityNotFoundException("Tobacco not found: " + tobaccoDto.getTobaccoId()));

            return MixTobacco.builder()
                    .mix(mix)
                    .tobacco(tobacco)
                    .proportion(tobaccoDto.getProportion())
                    .build();
        }).collect(Collectors.toList());

        mix.setMixTobaccos(mixTobaccos);
        return toDto(mixRepository.save(mix));
    }

    private TobaccoMixDto toDto(TobaccoMix mix) {
        List<MixTobaccoDto> tobaccos = mix.getMixTobaccos().stream().map(mt -> {
            Tobacco t = mt.getTobacco();
            return MixTobaccoDto.builder()
                    .tobaccoId(t.getId())
                    .name(t.getName())
                    .brand(t.getBrand())
                    .flavors(List.of(t.getFlavors().split(","))) // Если flavors – строка в Entity
                    .strength(t.getStrength())
                    .description(t.getDescription())
                    .imageUrl(t.getImageUrl())
                    .proportion(mt.getProportion())
                    .build();
        }).collect(Collectors.toList());

        return TobaccoMixDto.builder()
                .id(mix.getId())
                .name(mix.getName())
                .description(mix.getDescription())
                .tobaccos(tobaccos)
                .build();
    }

    public TobaccoMixDto update(Long id, TobaccoMixDto dto) {
        TobaccoMix existingMix = mixRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mix not found with id: " + id));

        existingMix.setName(dto.getName());
        existingMix.setDescription(dto.getDescription());

        // Удалим старые связи и пересоздадим новые
        List<MixTobacco> newMixTobaccos = dto.getTobaccos().stream()
                .map(mixTobaccoDto -> {
                    Tobacco tobacco = tobaccoRepository.findById(mixTobaccoDto.getTobaccoId())
                            .orElseThrow(() -> new EntityNotFoundException("Tobacco not found: " + mixTobaccoDto.getTobaccoId()));
                    return MixTobacco.builder()
                            .mix(existingMix)
                            .tobacco(tobacco)
                            .proportion(mixTobaccoDto.getProportion())
                            .build();
                })
                .collect(Collectors.toList());

        existingMix.getMixTobaccos().clear();
        existingMix.getMixTobaccos().addAll(newMixTobaccos);

        TobaccoMix saved = mixRepository.save(existingMix);
        return toDto(saved);
    }

    public void delete(Long id) {
        if (!mixRepository.existsById(id)) {
            throw new EntityNotFoundException("Mix not found with id: " + id);
        }
        mixRepository.deleteById(id);
    }

}
