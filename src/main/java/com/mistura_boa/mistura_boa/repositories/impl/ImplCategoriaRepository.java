package com.mistura_boa.mistura_boa.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mistura_boa.mistura_boa.models.entities.Categoria;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImplCategoriaRepository {

    private final EntityManager entityManager;

    public List<Categoria> search(String nome){
        var hql = new StringBuilder();
        hql.append("SELECT distinct c ");
        hql.append("FROM Categoria c ");

        if(nome != null && !nome.isEmpty() && !nome.isBlank()){
            hql.append("WHERE lower(c.nome) LIKE lower(concat('%',:nome, '%')) ");
        }

        var query = entityManager.createQuery(hql.toString(), Categoria.class);
        if(nome != null && !nome.isEmpty() && !nome.isBlank()){
            query.setParameter("nome", nome);
        }

        return query.getResultList();
    }

}
