package com.example.videolocadora.services;

import com.example.videolocadora.model.EstoqueModel;
import com.example.videolocadora.model.LocadoraModel;
import com.example.videolocadora.repositories.LocadoraRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocadoraService {

    final LocadoraRepository locadoraRepository;

    public LocadoraService(LocadoraRepository locadoraRepository) {
        this.locadoraRepository = locadoraRepository;
    }

    @Transactional
    public LocadoraModel save(LocadoraModel locadoraModel){return locadoraRepository.save(locadoraModel);}

    public boolean existsByNome(String nome){return locadoraRepository.existsByNome(nome);}

    public List<LocadoraModel> findAll(){return locadoraRepository.findAll();}

    public Optional<LocadoraModel> findById(UUID id){return locadoraRepository.findById(id);}

    public Optional<LocadoraModel> findByNome(String nome){return locadoraRepository.findByNome(nome);}

    public Optional<LocadoraModel> findByProduto(String produto){return locadoraRepository.findByProduto(produto);}

    public void deleteById(UUID id){locadoraRepository.deleteById(id);}
}
