package br.com.fiap.MonitoringMottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.MonitoringMottu.model.Moto;

public interface MotoRepository extends JpaRepository<Moto, Long>, JpaSpecificationExecutor<Moto> {
    
}