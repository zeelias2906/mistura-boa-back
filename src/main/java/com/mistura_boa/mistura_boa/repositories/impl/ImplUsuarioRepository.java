package com.mistura_boa.mistura_boa.repositories.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.mistura_boa.mistura_boa.models.entities.Usuario;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImplUsuarioRepository {
    
    private final EntityManager entityManager;

    public Page<Usuario> search(FilterSimple filter, Pageable pageable) throws Exception{
        var hql = new StringBuilder();
        hql.append("SELECT distinct u ");
        montarQueryAndFilters(hql, filter);

        var query = entityManager.createQuery(hql.toString(), Usuario.class);
        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            query.setParameter("nome", filter.getNome());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        var result = query.getResultList();

        return new PageImpl<>(result, pageable, countTotalResults(filter));
    }

    public Long countTotalResults(FilterSimple filter) throws Exception {
		var hql = new StringBuilder();
		hql.append(" select count(u.id) ");
		montarQueryAndFilters(hql, filter);

        var query = entityManager.createQuery(hql.toString(), Long.class);
        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            query.setParameter("nome", filter.getNome());
        }

        return query.getSingleResult();
	}

    private void montarQueryAndFilters(StringBuilder hql, FilterSimple filter) {
        hql.append("FROM Usuario u ");

        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            hql.append("WHERE lower(u.pessoa.nome) LIKE lower(concat('%',:nome, '%')) ");
        }
    }


}
