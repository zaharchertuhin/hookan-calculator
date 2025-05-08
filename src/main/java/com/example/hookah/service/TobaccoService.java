package com.example.hookah.service;

import com.example.hookah.dto.TobaccoDto;
import com.example.hookah.model.Tobacco;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TobaccoService {

    private final com.example.hookah.repository.TobaccoRepository tobaccoRepository;

    /**
     * Получить список всех табаков
     */
    public List<TobaccoDto> getAll() {
        return tobaccoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Найти табак по ID
     */
    public TobaccoDto getById(Long id) {
        Tobacco tobacco = tobaccoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tobacco not found with id: " + id));
        return toDto(tobacco);
    }

    /**
     * Создать новый табак
     */
    public TobaccoDto create(TobaccoDto dto) {
        Tobacco entity = toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        Tobacco saved = tobaccoRepository.save(entity);
        return toDto(saved);
    }

    /**
     * Обновить существующий табак
     */
    public TobaccoDto update(Long id, TobaccoDto dto) {
        Tobacco existing = tobaccoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tobacco not found with id: " + id));

        existing.setName(dto.getName());
        existing.setBrand(dto.getBrand());
        existing.setFlavors(dto.getFlavors());
        existing.setStrength(dto.getStrength());
        existing.setDescription(dto.getDescription());
        existing.setImageUrl(dto.getImageUrl());

        Tobacco updated = tobaccoRepository.save(existing);
        return toDto(updated);
    }

    /**
     * Удалить табак
     */
    public void delete(Long id) {
        if (!tobaccoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tobacco not found with id: " + id);
        }
        tobaccoRepository.deleteById(id);
    }

    // === Mapper methods ===

    private TobaccoDto toDto(Tobacco t) {
        return TobaccoDto.builder()
                .id(t.getId())
                .name(t.getName())
                .brand(t.getBrand())
                .flavors(t.getFlavors())
                .strength(t.getStrength())
                .description(t.getDescription())
                .imageUrl(t.getImageUrl())
                .build();
    }

    private Tobacco toEntity(TobaccoDto dto) {
        return Tobacco.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .flavors(dto.getFlavors())
                .strength(dto.getStrength())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();
    }

    public List<TobaccoDto> getByBrand(String brand) {
        return tobaccoRepository.findByBrandIgnoreCase(brand)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TobaccoDto> getByFlavor(String flavor) {
        return tobaccoRepository.findByFlavorsContainingIgnoreCase(flavor)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TobaccoDto> getByBrandAndFlavor(String brand, String flavor) {
        return tobaccoRepository.findByBrandIgnoreCaseAndFlavorsContainingIgnoreCase(brand, flavor)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}

