package br.com.fiap.MonitoringMottu.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Long id_posicionamento;

    @OneToOne
    @JsonIgnore
    private Moto moto;

    @ManyToOne
    @JsonIgnore
    private Patio patio;

    @NotBlank(message = "A posição horizontal não pode estar em branco.")
    @Size(min = 1, max = 2, message = "A posição horizontal deve conter um ou dois números apenas")
    private int pos_x;

    @NotBlank(message = "A posição vertical não pode estar em branco.")
    @Size(min = 1, max = 2, message = "A posição vertical deve conter um ou dois números apenas")
    private int pos_y;

    @NotNull(message = "A data é obrigatória")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate data_hora;
}
