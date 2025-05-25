package br.com.fiap.MonitoringMottu.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Posicionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPosicionamento;

    @ManyToOne
    private Patio patio;

    @Min(1)
    @Max(25)
    @Positive
    private int pos_x;

    @Min(1)
    @Max(25)
    @Positive
    private int pos_y;

    @NotNull(message = "A data é obrigatória")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate data_hora;
}
