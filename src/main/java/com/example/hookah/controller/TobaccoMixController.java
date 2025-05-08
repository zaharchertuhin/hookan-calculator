package com.example.hookah.controller;

import com.example.hookah.dto.TobaccoMixDto;
import com.example.hookah.service.TobaccoMixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mixes")
@RequiredArgsConstructor
public class TobaccoMixController {

    private final TobaccoMixService tobaccoMixService;

    @GetMapping
    public ResponseEntity<List<TobaccoMixDto>> getAll() {
        return ResponseEntity.ok(tobaccoMixService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TobaccoMixDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tobaccoMixService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TobaccoMixDto> create(@RequestBody TobaccoMixDto dto) {
        return ResponseEntity.ok(tobaccoMixService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TobaccoMixDto> update(@PathVariable Long id, @RequestBody TobaccoMixDto dto) {
        return ResponseEntity.ok(tobaccoMixService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tobaccoMixService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
