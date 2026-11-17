package com.hamburgueria.repository;

import com.hamburgueria.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByDisponivelTrue();

    boolean existsByCategoriaId(Long categoriaId);
}
