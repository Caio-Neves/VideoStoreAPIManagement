package com.example.videolocadora.repositories;

import com.example.videolocadora.model.EstoqueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueModel, UUID> {

    boolean existsByProduto(String produto);

    Optional<EstoqueModel> findByProduto(String produto);

    void deleteByProduto(String produto);
}
