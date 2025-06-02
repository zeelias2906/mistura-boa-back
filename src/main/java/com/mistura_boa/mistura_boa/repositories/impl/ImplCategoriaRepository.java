package com.mistura_boa.mistura_boa.repositories.impl;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.mistura_boa.mistura_boa.models.entities.Categoria;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;
import com.mistura_boa.mistura_boa.models.grids.CategoriaGrid;

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

    public List<CategoriaGrid> getAllActiveForGrid(){
        var hql = new StringBuilder();
        hql.append("SELECT distinct new com.mistura_boa.mistura_boa.models.grids.CategoriaGrid( ");
        hql.append(" c.id, ");
        hql.append(" c.descricao, ");
        hql.append(" c.nome, ");
        hql.append(" c.icone, ");
        hql.append(" c.ordenacao) ");
        montarQuerySearchGrid(hql);

        var query = entityManager.createQuery(hql.toString(), CategoriaGrid.class);
        return query.getResultList();
    }

    private void montarQuerySearchGrid(StringBuilder hql ){
        hql.append("FROM Categoria c ");
        hql.append("WHERE 1=1 ");
        hql.append(" AND c.dataExclusao is null ");
        hql.append(" AND ( ");
        hql.append(" SELECT count(p.id) FROM Produto p where p.categoria.id = c.id ) > 0  ");
        hql.append("ORDER BY c.ordenacao ASC ");
    }


    private void montarQueryAndFilters(StringBuilder hql, FilterSimple filter) {
        hql.append("FROM Categoria c ");

        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            hql.append("WHERE lower(c.nome) LIKE lower(concat('%',:nome, '%')) ");
        }

        hql.append("ORDER BY c.ordenacao ASC");
    }

}
