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

            if (filter.placa() != null && !filter.placa().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("placa")), "%" + filter.placa().toLowerCase() + "%"));
            }

            if (filter.modelo() != null && !filter.modelo().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("modelo")), "%" + filter.modelo().toLowerCase() + "%"));
            }

            if (filter.sensor_iot() != null && !filter.sensor_iot().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("sensor_iot")), "%" + filter.sensor_iot().toLowerCase() + "%"));
            }

            if (filter.statusMoto() != null && !filter.statusMoto().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("statusMoto")), filter.statusMoto().toLowerCase()));
            }

            if (filter.id() != null) {
                predicates.add(cb.equal(root.get("id"), Long.valueOf(filter.id())));
            }

            if (filter.patio() != null && !filter.patio().isBlank()) {
                predicates.add(cb.equal(root.get("patio").get("id"), Long.valueOf(filter.patio())));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
