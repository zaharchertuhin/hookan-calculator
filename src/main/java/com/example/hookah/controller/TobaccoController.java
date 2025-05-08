package com.example.hookah.controller;

import com.example.hookah.dto.TobaccoDto;
import com.example.hookah.service.TobaccoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tobacco")
@RequiredArgsConstructor
public class TobaccoController {

    private final TobaccoService tobaccoService;

    /**
     * Получить все табаки, с фильтрами по бренду и вкусу
     */
    @Operation(summary = "Получить все табаки", description = "Получить список табаков с фильтрами по бренду и вкусу")
    @GetMapping
    public List<TobaccoDto> getAll(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String flavor
    ) {
        if (brand != null && flavor != null) {
            return tobaccoService.getByBrandAndFlavor(brand, flavor);
        } else if (brand != null) {
            return tobaccoService.getByBrand(brand);
        } else if (flavor != null) {
            return tobaccoService.getByFlavor(flavor);
        } else {
            return tobaccoService.getAll();
        }
    }

    /**
     * Получить табак по ID
     */
    @Operation(summary = "Получить табак по ID", description = "Получить табак по уникальному идентификатору")
    @GetMapping("/{id}")
    public TobaccoDto getById(@PathVariable Long id) {
        return tobaccoService.getById(id);
    }

    /**
     * Создать табак
     */
    @Operation(summary = "Создать новый табак", description = "Создайте новый табак в базе данных")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TobaccoDto create(@RequestBody TobaccoDto dto) {
        return tobaccoService.create(dto);
    }

    /**
     * Обновить табак
     */
    @Operation(summary = "Обновить табак", description = "Обновить данные табака по его ID")
    @PutMapping("/{id}")
    public TobaccoDto update(@PathVariable Long id, @RequestBody TobaccoDto dto) {
        return tobaccoService.update(id, dto);
    }

    /**
     * Удалить табак
     */
    @Operation(summary = "Удалить табак", description = "Удалить табак по его ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tobaccoService.delete(id);
    }
}
