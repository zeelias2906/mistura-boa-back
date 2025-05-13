package com.mistura_boa.mistura_boa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("Select count(c.id)>0 from Categoria c where lower(c.nome) = lower(:nome) and (:id IS NULL OR c.id != :id)")
    public boolean existsCategoriaByNome(@Param("nome") String nome, @Param("id") Long id);
}
