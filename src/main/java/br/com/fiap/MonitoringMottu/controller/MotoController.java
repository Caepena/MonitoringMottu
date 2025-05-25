package br.com.fiap.MonitoringMottu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.MonitoringMottu.dto.MotoDto;
import br.com.fiap.MonitoringMottu.model.Moto;
import br.com.fiap.MonitoringMottu.model.Posicionamento;
import br.com.fiap.MonitoringMottu.repository.MotoRepository;
import br.com.fiap.MonitoringMottu.repository.PosicionamentoRepository;
import br.com.fiap.MonitoringMottu.specification.MotoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/motos")
@Slf4j
public class MotoController {

    public record MotoFilter(Long id, String modelo, String placa, String sensor_iot, String statusMoto, String patio) {
    }

    @Autowired
    private MotoRepository repository;

    @Autowired
    private PosicionamentoRepository posicionamentoRepository;

    @GetMapping
    @Cacheable("motos")
    @Operation(description = "Listar todas as motos", summary = "List all motorycles", tags = "Motos", responses = {
            @ApiResponse(responseCode = "200", description = "Motos encontradas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma moto encontrada")
    })
    public Page<Moto> index(MotoFilter filter,
            @PageableDefault(size = 3, sort = "placa", direction = Direction.DESC) Pageable pageable) {
        var specification = MotoSpecification.withFilter(filter);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "motos", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Criar uma nova moto", summary = "Create a new motorycle", tags = "Motos", responses = {
            @ApiResponse(responseCode = "201", description = "Moto criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar moto")
    })
    public Moto create(@RequestBody @Valid MotoDto dto) {

        log.info("Recebido para criar moto: " + dto);

        // Busca o posicionamento já existente no banco
        Posicionamento posicionamento = posicionamentoRepository.findById(dto.getIdPosicionamento())
                .orElseThrow(() -> new RuntimeException("Posicionamento não encontrado"));

        // Converte o DTO para a entidade Moto
        Moto moto = new Moto();
        moto.setModelo(dto.getModelo());
        moto.setPlaca(dto.getPlaca());
        moto.setSensor_iot(dto.getSensor_iot());
        moto.setStatusMoto(dto.getStatusMoto());
        moto.setPosicionamento(posicionamento);

        return repository.save(moto);
    }

    @GetMapping("{id}")
    @Operation(description = "Buscar uma moto por ID", summary = "Get a motorycle by ID", tags = "Motos", responses = {
            @ApiResponse(responseCode = "200", description = "Moto encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    public Moto get(Long id) {
        log.info("Buscando moto: " + id);
        return getMoto(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    @CacheEvict(value = "motos", allEntries = true)
    @Operation(description = "Deletar uma moto", summary = "Delete a motorycle", tags = "Motos", responses = {
            @ApiResponse(responseCode = "204", description = "Moto deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    public void delete(@PathVariable Long id) {
        log.info("Deletando moto: " + id);
        repository.delete(getMoto(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "motos", allEntries = true)
    @Operation(description = "Atualizar uma moto", summary = "Update a motorycle", tags = "Motos", responses = {
            @ApiResponse(responseCode = "200", description = "Moto atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    public Moto update(@PathVariable Long id, @RequestBody @Valid Moto moto) {
        log.info("Atualizando moto: " + id);
        getMoto(id);
        moto.setId(id);
        return repository.save(moto);
    }

    private Moto getMoto(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Moto não encontrada"));
    }
}
