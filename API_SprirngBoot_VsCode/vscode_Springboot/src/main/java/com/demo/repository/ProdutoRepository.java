package com.demo.repository;

import com.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findBySku(String sku);

    List<Produto> findByAtivoTrue();

    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:nome%")
    List<Produto> buscarPorNome(@Param("nome") String nome);

    List<Produto> findByAtivoTrueOrderByDataCriacaoDesc();
}
