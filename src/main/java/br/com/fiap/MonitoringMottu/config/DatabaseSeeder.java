package br.com.fiap.MonitoringMottu.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.MonitoringMottu.model.Moto;
import br.com.fiap.MonitoringMottu.model.Patio;
import br.com.fiap.MonitoringMottu.model.Posicionamento;
import br.com.fiap.MonitoringMottu.model.StatusMoto;
import br.com.fiap.MonitoringMottu.repository.MotoRepository;
import br.com.fiap.MonitoringMottu.repository.PatioRepository;
import br.com.fiap.MonitoringMottu.repository.PosicionamentoRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PosicionamentoRepository posicionamentoRepository;

    @PostConstruct
    public void init() {
        // Criando patios
        var patios = List.of(
            Patio.builder().nome("Pátio Central").endereco("Rua Alfa, 123 - São Paulo").build(),
            Patio.builder().nome("Pátio Sul").endereco("Av. Beta, 456 - Santo André").build(),
            Patio.builder().nome("Pátio Norte").endereco("Rua Gama, 789 - São Bernardo").build(),
            Patio.builder().nome("Pátio Leste").endereco("Av. Delta, 101 - São Caetano").build()
        );
        patioRepository.saveAll(patios);

        // Criando motos sem posicionamento (ainda)
        var motos = List.of(
            Moto.builder()
                .modelo("Honda Elite 125")
                .placa("ABC1234")
                .sensor_iot("SENSOR-001")
                .statusMoto(StatusMoto.DISPONIVEL)
                .build(),

            Moto.builder()
                .modelo("Yamaha NMax 160")
                .placa("XYZ5678")
                .sensor_iot("SENSOR-002")
                .statusMoto(StatusMoto.QUEBRADA)
                .build(),

            Moto.builder()
                .modelo("Suzuki Burgman 125")
                .placa("LMN9101")
                .sensor_iot("SENSOR-003")
                .statusMoto(StatusMoto.PARADA)
                .build(),

            Moto.builder()
                .modelo("Kawasaki Z300")
                .placa("QRS2345")
                .sensor_iot("SENSOR-004")
                .statusMoto(StatusMoto.ALUGADA)
                .build()
        );
        motoRepository.saveAll(motos);

        // Criando posicionamentos relacionados a patios e motos
        var posicionamentos = List.of(
            Posicionamento.builder()
                .patio(patios.get(0))
                .pos_x(4)
                .pos_y(7)
                .data_hora(LocalDate.now().minusDays(1))
                .build(),

            Posicionamento.builder()
                .patio(patios.get(1))
                .pos_x(2)
                .pos_y(3)
                .data_hora(LocalDate.now())
                .build(),

            Posicionamento.builder()
                .patio(patios.get(0))
                .pos_x(5)
                .pos_y(8)
                .data_hora(LocalDate.now())
                .build(),

            Posicionamento.builder()
                .patio(patios.get(1))
                .pos_x(1)
                .pos_y(4)
                .data_hora(LocalDate.now())
                .build()
        );
        posicionamentoRepository.saveAll(posicionamentos);

        for (int i = 0; i < motos.size(); i++) {
            Moto moto = motos.get(i);
            moto.setPosicionamento(posicionamentos.get(i));
            motoRepository.save(moto);
        }
    }
}
