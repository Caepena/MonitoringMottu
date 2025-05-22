package br.com.fiap.MonitoringMottu.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.MonitoringMottu.controller.MotoController.MotoFilter;
import br.com.fiap.MonitoringMottu.model.Moto;
import jakarta.persistence.criteria.Predicate;

public class MotoSpecification {

    public static Specification<Moto> withFilter(MotoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null && !filter.name().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("placa")), "%" + filter.name().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
