package com.example.hookah.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tobacco_mix")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TobaccoMix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Название подборки
    private String description;

    @ManyToMany
    @JoinTable(
            name = "mix_tobaccos",
            joinColumns = @JoinColumn(name = "mix_id"),
            inverseJoinColumns = @JoinColumn(name = "tobacco_id")
    )
    private List<Tobacco> tobaccos;

    @OneToMany(mappedBy = "mix", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MixTobacco> mixTobaccos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tobacco> getTobaccos() {
        return tobaccos;
    }

    public void setTobaccos(List<Tobacco> tobaccos) {
        this.tobaccos = tobaccos;
    }

    public List<MixTobacco> getMixTobaccos() {
        return mixTobaccos;
    }

    public void setMixTobaccos(List<MixTobacco> mixTobaccos) {
        this.mixTobaccos = mixTobaccos;
    }
}
