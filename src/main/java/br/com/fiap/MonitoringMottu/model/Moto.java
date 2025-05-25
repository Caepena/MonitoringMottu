package br.com.fiap.MonitoringMottu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O modelo não pode estar em branco")
    private String modelo;

    @NotBlank(message = "A placa não pode estar em branco")
    @Size(min = 7, max = 7, message = "A placa deve conter exatamente 7 caracteres")
    @Pattern(regexp = "^[A-Z0-9]{7}$", message = "A placa deve conter apenas letras maiúsculas e números")
    private String placa;

    @NotBlank(message = "O sensor IoT não pode estar em branco")
    private String sensor_iot;

    @OneToOne
    @JoinColumn(name = "idPosicionamento")
    private Posicionamento posicionamento;

    @Transient
    @JsonIgnore
    private Long idPosicionamento;

    @NotNull(message = "A moto deve conter um status")
    @Enumerated(EnumType.STRING)
    private StatusMoto statusMoto;
}
