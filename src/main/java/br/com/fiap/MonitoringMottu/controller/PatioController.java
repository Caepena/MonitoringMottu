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

import br.com.fiap.MonitoringMottu.model.Patio;
import br.com.fiap.MonitoringMottu.repository.PatioRepository;
import br.com.fiap.MonitoringMottu.specification.PatioSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/patios")
@Slf4j
public class PatioController {

        public record PatioFilter(Long id_patio, String nome, String endereco) {
        }

        @Autowired
        private PatioRepository repository;

        @GetMapping
        @Cacheable("patios")
        @Operation(description = "Listar todos os patios", tags = "patios", summary = "List all patios", responses = {
                        @ApiResponse(responseCode = "200", description = "Patios listados com sucesso"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        public Page<Patio> index(PatioFilter filter,
                        @PageableDefault(size = 3, sort = "nome", direction = Direction.DESC) Pageable pageable) {
                var specification = PatioSpecification.withFilter(filter);
                return repository.findAll(specification, pageable);
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        @CacheEvict(value = "patios", allEntries = true)
        @Operation(description = "Criar um novo patio", tags = "patios", summary = "Create a new patio", responses = {
                        @ApiResponse(responseCode = "201", description = "Patio criado com sucesso"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        public Patio create(@Valid @RequestBody Patio patio) {
                log.info("Criando patio: " + patio.getNome());
                return repository.save(patio);
        }

        @GetMapping("{id_patio}")
        @ResponseStatus(HttpStatus.OK)
        @Operation(description = "Buscar um patio por ID", tags = "patios", summary = "Get a patio by ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Patio encontrado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Patio não encontrado")
        })
        public Patio get(Long id) {
                log.info("Buscando patio: " + id);
                return getPatio(id);
        }

        @DeleteMapping("{id_patio}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @CacheEvict(value = "patios", allEntries = true)
        @Operation(description = "Deletar um patio por ID", tags = "patios", summary = "Delete a patio by ID", responses = {
                        @ApiResponse(responseCode = "204", description = "Patio deletado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Patio não encontrado")
        })
        public void delete(@PathVariable Long id) {
                log.info("Deletando patio: " + id);
                repository.delete(getPatio(id));
        }

        @CacheEvict(value = "patios", allEntries = true)
        @PutMapping("{id_patio}")
        @ResponseStatus(HttpStatus.OK)
        @Operation(description = "Atualizar um patio por ID", tags = "patios", summary = "Update a patio by ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Patio atualizado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Patio não encontrado")
        })
        public Patio update(@PathVariable Long id, @RequestBody @Valid Patio patio) {
                log.info("Atualizando game: " + id);

                getPatio(id);
                patio.setId_patio(id);
                return repository.save(patio);
        }

        private Patio getPatio(Long id) {
                return repository.findById(id).orElseThrow(() -> new RuntimeException("Patio não encontrado"));
        }
}
