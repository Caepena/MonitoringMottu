package br.com.fiap.MonitoringMottu.dto;

import br.com.fiap.MonitoringMottu.model.StatusMoto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoDto {
    private String modelo;
    private String placa;
    private String sensor_iot;
    private StatusMoto statusMoto;
    private Long idPosicionamento;
}