package br.com.fiap.MonitoringMottu.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.MonitoringMottu.controller.PatioController.PatioFilter;
import br.com.fiap.MonitoringMottu.model.Patio;
import jakarta.persistence.criteria.Predicate;

public class PatioSpecification {

    public static Specification<Patio> withFilter(PatioFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.id_patio() != null) {
                predicates.add(cb.equal(cb.lower(root.get("id_patio")), "%" + filter.id_patio() + "%"));
            }

            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            if (filter.endereco() != null && !filter.endereco().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("endereco")), "%" + filter.endereco().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
