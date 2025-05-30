package com.mistura_boa.mistura_boa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.Produto;
import com.mistura_boa.mistura_boa.models.grids.OptionsSelects;

public interface IProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("Select count(p.id)>0 from Produto p where lower(p.nome) = lower(:nome) and (:id IS NULL OR p.id != :id)")
    public boolean existsProdutoByNome(@Param("nome") String nome, @Param("id") Long id);

    @Query("Select p from Produto p where p.categoria.id = :idCategoria")
    public List<Produto> findAllByIdCategoria(@Param("idCategoria") Long idCategoria);

    @Query("SELECT new com.mistura_boa.mistura_boa.models.grids.OptionsSelects(p.id, p.nome) FROM Produto p where p.categoria.id = :idCategoria ORDER BY p.ordenacao")
    public List<OptionsSelects> getOptionsSelectsByIdCategoria(@Param("idCategoria") Long idCategoria);

}
