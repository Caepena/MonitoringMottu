package br.com.fiap.MonitoringMottu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_patio;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 50, message = "Nome não pode conter mais que 50 caracteres")
    private String nome;

    @NotBlank(message = "A localização não pode estar em branco")
    @Size(min = 10, message = "A localização deve conter mais de 10 caracteres")
    private String endereco;
}
