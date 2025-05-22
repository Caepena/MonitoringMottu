package br.com.fiap.MonitoringMottu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.MonitoringMottu.model.Moto;
import br.com.fiap.MonitoringMottu.repository.MotoRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/motos")
@Slf4j
public class MotoController {

    public record MotoFilter(String name) {
    }

    @Autowired
    private MotoRepository repository;

    public List<Moto> index() {
        return null;
    }
}
