package br.com.fiap.MonitoringMottu.controller;

import java.time.LocalDate;

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
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.MonitoringMottu.model.Posicionamento;
import br.com.fiap.MonitoringMottu.repository.PosicionamentoRepository;
import br.com.fiap.MonitoringMottu.specification.PosicionamentoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("posicionamentos")
@Slf4j
public class PosicionamentoController {

    public record PosicionamentoFilter(Long id_posicionamento, Long id_moto, Long id_patio, LocalDate data_hora,
            Integer pos_x, Integer pos_y) {
    }

    @Autowired
    private PosicionamentoRepository repository;

    @GetMapping
    @Cacheable("posicionamentos")
    @Operation(description = "Listar todos os posicionamentos", summary = "List all positions", tags = "posições", responses = {
            @ApiResponse(responseCode = "200", description = "Posicionamentos encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum posicionamento encontrado") })
    public Page<Posicionamento> index(PosicionamentoFilter filter,
            @PageableDefault(size = 3, sort = "idPosicionamento", direction = Direction.DESC) Pageable pageable) {
        var specification = PosicionamentoSpecification.withFilters(filter);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "posicionamentos", allEntries = true)
    @Operation(description = "Criar novo posicionamento", summary = "Create new position", tags = "posições", responses = {
            @ApiResponse(responseCode = "201", description = "Posicionamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar posicionamento") })
    public Posicionamento create(@RequestBody @Valid Posicionamento posicionamento) {
        log.info("Criando novo posicionamento: " + posicionamento.getIdPosicionamento());
        return repository.save(posicionamento);
    }

    @Operation(description = "Visualizar posicionamento", summary = "Get position by id", tags = "posições", responses = {
            @ApiResponse(responseCode = "200", description = "Posicionamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Posicionamento não encontrado") })
    @GetMapping("{id_posicionamento}")
    public Posicionamento get(@PathVariable Long id_posicionamento) {
        log.info("Buscando posicionamento: ", +id_posicionamento);
        return getPosicionamento(id_posicionamento);
    }

    @DeleteMapping("{id_posicionamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "posicionamentos", allEntries = true)
    @Operation(description = "Deletar posicionamento", summary = "Delete position by id", tags = "posições", responses = {
            @ApiResponse(responseCode = "204", description = "Posicionamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Posicionamento não encontrado") })
    public void delete(@PathVariable Long id_posicionamento) {
        log.info("Deletando posicionamento: ", +id_posicionamento);
        repository.delete(getPosicionamento(id_posicionamento));
    }

    @PutMapping("{id_posicionamento}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = "posicionamentos", allEntries = true)
    @Operation(description = "Atualizar posicionamento", summary = "Update position by id", tags = "posições", responses = {
            @ApiResponse(responseCode = "200", description = "Posicionamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Posicionamento não encontrado") })
    public Posicionamento update(@PathVariable Long id_posicionamento,
            @RequestBody @Valid Posicionamento posicionamento) {
        log.info("Atualizando posicionamento: ", +id_posicionamento);
        getPosicionamento(id_posicionamento);
        posicionamento.setIdPosicionamento(id_posicionamento);
        return repository.save(posicionamento);
    }

    private Posicionamento getPosicionamento(Long id_posicionamento) {
        return repository.findById(id_posicionamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Posicionamento não encontrado"));
    }
}
