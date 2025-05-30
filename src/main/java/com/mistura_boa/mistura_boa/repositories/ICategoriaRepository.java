package com.mistura_boa.mistura_boa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.Categoria;
import com.mistura_boa.mistura_boa.models.grids.OptionsSelects;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("Select count(c.id)>0 from Categoria c where lower(c.nome) = lower(:nome) and (:id IS NULL OR c.id != :id)")
    public boolean existsCategoriaByNome(@Param("nome") String nome, @Param("id") Long id);

    @Query("Select new com.mistura_boa.mistura_boa.models.grids.OptionsSelects(c.id, c.nome) from Categoria c order by c.ordenacao")
    public List<OptionsSelects> getOptionsSelects();
}
