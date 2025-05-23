package com.mistura_boa.mistura_boa.repositories.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.mistura_boa.mistura_boa.models.entities.Categoria;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImplCategoriaRepository {

    private final EntityManager entityManager;

    public Page<Categoria> search(FilterSimple filter, Pageable pageable) throws Exception{
        var hql = new StringBuilder();
        hql.append("SELECT distinct c ");
        montarQueryAndFilters(hql, filter);

        var query = entityManager.createQuery(hql.toString(), Categoria.class);
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
		hql.append(" select count(c.id) ");
		montarQueryAndFilters(hql, filter);

        var query = entityManager.createQuery(hql.toString(), Long.class);
        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            query.setParameter("nome", filter.getNome());
        }

        return query.getSingleResult();
	}

    private void montarQueryAndFilters(StringBuilder hql, FilterSimple filter) {
        hql.append("FROM Categoria c ");

        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            hql.append("WHERE lower(c.nome) LIKE lower(concat('%',:nome, '%')) ");
        }

        hql.append("ORDER BY c.ordenacao ASC");
    }

}
