package com.mistura_boa.mistura_boa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.ProdutoCarrinho;

import jakarta.transaction.Transactional;

public interface IProdutoCarrinhoRepository extends JpaRepository<ProdutoCarrinho, Long> {

    @Transactional
    @Modifying
    @Query ("delete from ProdutoCarrinho pc where pc.id = :id")
    public void deleteById(@Param("id") Long id);
}
