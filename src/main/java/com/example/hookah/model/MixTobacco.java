package com.example.hookah.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mix_tobaccos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MixTobacco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mix_id")
    private TobaccoMix mix;

    @ManyToOne
    @JoinColumn(name = "tobacco_id")
    private Tobacco tobacco;

    private Integer proportion; // Пропорция (в % или относительное значение)

    public TobaccoMix getMix() {
        return mix;
    }

    public void setMix(TobaccoMix mix) {
        this.mix = mix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tobacco getTobacco() {
        return tobacco;
    }

    public void setTobacco(Tobacco tobacco) {
        this.tobacco = tobacco;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }
}
