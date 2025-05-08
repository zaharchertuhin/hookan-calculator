package com.example.hookah.repository;

import com.example.hookah.model.Tobacco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TobaccoRepository extends JpaRepository<Tobacco, Long> {

    List<Tobacco> findByBrandIgnoreCase(String brand);

    List<Tobacco> findByFlavorsContainingIgnoreCase(String flavor);

    List<Tobacco> findByBrandIgnoreCaseAndFlavorsContainingIgnoreCase(String brand, String flavor);
}