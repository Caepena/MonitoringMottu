package br.com.fiap.MonitoringMottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.MonitoringMottu.model.Patio;

public interface PatioRepository extends JpaRepository<Patio, Long>, JpaSpecificationExecutor<Patio> {

}
