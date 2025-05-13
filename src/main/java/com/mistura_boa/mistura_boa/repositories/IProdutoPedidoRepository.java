package com.mistura_boa.mistura_boa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.ProdutoPedido;

import jakarta.transaction.Transactional;

public interface IProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Long> {

    @Transactional
    @Modifying
    @Query ("delete from ProdutoPedido pp where pp.id = :id")
    public void deleteById(@Param("id") Long id);

}
