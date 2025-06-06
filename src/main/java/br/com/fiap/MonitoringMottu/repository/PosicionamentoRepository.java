package br.com.fiap.MonitoringMottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.MonitoringMottu.model.Posicionamento;

public interface PosicionamentoRepository extends JpaRepository<Posicionamento, Long>, JpaSpecificationExecutor<Posicionamento> {

}
