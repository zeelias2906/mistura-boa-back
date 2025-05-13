package com.mistura_boa.mistura_boa.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mistura_boa.mistura_boa.models.entities.Usuario;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImplUsuarioRepository {
    
    private final EntityManager entityManager;

    public List<Usuario> search(String nome){
        var hql = new StringBuilder();
        hql.append("SELECT distinct u ");
        hql.append("FROM Usuario u ");

        if(nome != null && !nome.isEmpty() && !nome.isBlank()){
            hql.append("WHERE lower(u.pessoa.nome) LIKE lower(concat('%',:nome, '%')) ");
        }

        var query = entityManager.createQuery(hql.toString(), Usuario.class);
        if(nome != null && !nome.isEmpty() && !nome.isBlank()){
            query.setParameter("nome", nome);
        }

        return query.getResultList();
    }
}
