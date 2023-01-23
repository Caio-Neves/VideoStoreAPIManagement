package com.example.videolocadora.repositories;

import com.example.videolocadora.model.LocadoraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface LocadoraRepository extends JpaRepository<LocadoraModel, UUID> {

    boolean existsByNome(String nome);

    Optional<LocadoraModel> findByNome(String nome);

    Optional<LocadoraModel> findByProduto(String produto);

    void deleteByProduto(String produto);
}
