package com.example.videolocadora.services;

import com.example.videolocadora.model.EstoqueModel;
import com.example.videolocadora.repositories.EstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstoqueService {

    final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public EstoqueModel save(EstoqueModel estoqueModel){
        return estoqueRepository.save(estoqueModel);
    }

    public boolean existsByProduto(String produto){
        return estoqueRepository.existsByProduto(produto);
    }

    public List<EstoqueModel> findAll(){return estoqueRepository.findAll();}

    public Optional<EstoqueModel> findById(UUID id){
        return estoqueRepository.findById(id);
    }

    public Optional<EstoqueModel> findByProduto(String produto) {return estoqueRepository.findByProduto(produto);}

    @Transactional
    public void deleteByProduto(String produto){
        estoqueRepository.deleteByProduto(produto);
    }
}
