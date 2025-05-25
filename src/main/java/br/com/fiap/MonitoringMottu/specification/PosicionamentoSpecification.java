package br.com.fiap.MonitoringMottu.specification;

import java.util.ArrayList;
import java.util.List;
// import java.util.function.Predicate; // Removed incorrect import
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.MonitoringMottu.controller.PosicionamentoController.PosicionamentoFilter;
import br.com.fiap.MonitoringMottu.model.Posicionamento;

public class PosicionamentoSpecification {
    
    public static Specification<Posicionamento> withFilters(PosicionamentoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.id_posicionamento() != null) {
                predicates.add(cb.equal(root.get("id_posicionamento"), filter.id_posicionamento()));
            }

            if (filter.id_moto() != null) {
                predicates.add(cb.equal(root.get("id_moto"), filter.id_moto()));
            }

            if (filter.id_patio() != null) {
                predicates.add(cb.equal(root.get("id_patio"), filter.id_patio()));
            }

            if (filter.data_hora() != null) {
                predicates.add(cb.equal(cb.lower(root.get("data_hora")), "%" + filter.data_hora() + "%"));
            }

            if (filter.pos_x() != null) {
                predicates.add(cb.equal(cb.lower(root.get("pos_x")), "%" + filter.pos_x() + "%"));
            }

            if (filter.pos_y() != null) {
                predicates.add(cb.equal(cb.lower(root.get("pos_y")), "%" + filter.pos_y() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
