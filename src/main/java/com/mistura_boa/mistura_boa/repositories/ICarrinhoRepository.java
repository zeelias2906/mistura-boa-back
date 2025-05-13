package com.mistura_boa.mistura_boa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.Carrinho;

public interface ICarrinhoRepository extends JpaRepository<Carrinho, Long> {

    @Query("Select c from Carrinho c where c.usuario.id = :idUsuario")
    Carrinho findByIdUsuario(@Param("idUsuario") Long idUsuario);

}
