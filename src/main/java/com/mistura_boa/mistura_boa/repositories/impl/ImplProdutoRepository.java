package com.mistura_boa.mistura_boa.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mistura_boa.mistura_boa.models.entities.Produto;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;
import com.mistura_boa.mistura_boa.models.grids.ProdutoCategoriaGrid;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImplProdutoRepository {

    private final EntityManager entityManager;

    public List<Produto> search(FilterSimple filter){
        var hql = new StringBuilder();
        hql.append("SELECT distinct p ");
        hql.append("FROM Produto p ");
        hql.append("WHERE 1=1");

        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            hql.append("AND lower(p.nome) LIKE lower(concat('%',:nome, '%')) ");
        }

        if(filter.getIdsCategoria() != null && !filter.getIdsCategoria().isEmpty()){
            hql.append("AND p.categoria.id in (:idsCategoria) ");
        }


        var query = entityManager.createQuery(hql.toString(), Produto.class);
        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            query.setParameter("nome", filter.getNome());
        }

        if(filter.getIdsCategoria() != null && !filter.getIdsCategoria().isEmpty()){
            query.setParameter("idsCategoria", filter.getIdsCategoria());
        }

        return query.getResultList();
    }

    public List<Produto> searchActive(FilterSimple filter){
        var hql = new StringBuilder();
        hql.append("SELECT distinct p ");
        hql.append("FROM Produto p ");
        hql.append("WHERE 1=1");

        hql.append(" AND p.dataExclusao is null ");

        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            hql.append("AND lower(p.nome) LIKE lower(concat('%',:nome, '%')) ");
        }

        if(filter.getIdsCategoria() != null && !filter.getIdsCategoria().isEmpty()){
            hql.append("AND p.categoria.id in (:idsCategoria) ");
        }


        var query = entityManager.createQuery(hql.toString(), Produto.class);
        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            query.setParameter("nome", filter.getNome());
        }

        if(filter.getIdsCategoria() != null && !filter.getIdsCategoria().isEmpty()){
            query.setParameter("idsCategoria", filter.getIdsCategoria());
        }

        return query.getResultList();
    }

    public List<ProdutoCategoriaGrid> searchGridProdCat(FilterSimple filter){
        var hql = new StringBuilder();
        hql.append("SELECT distinct new com.mistura_boa.mistura_boa.models.grids.ProdutoCategoriaGrid( ");
        hql.append(" p.id, ");
        hql.append(" p.descricao, ");
        hql.append(" p.nome, ");
        hql.append(" p.imgProduto, ");
        hql.append(" p.valor, ");
        hql.append(" c.id, ");
        hql.append(" c.descricao, ");
        hql.append(" c.nome, ");
        hql.append(" c.icone )");
        hql.append("FROM Produto p ");
        hql.append("INNER JOIN Categoria c ON p.categoria.id = c.id ");
        hql.append("WHERE 1=1 ");


        hql.append(" AND c.dataExclusao is null AND p.dataExclusao is null ");

        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            hql.append("AND lower(p.nome) LIKE lower(concat('%',:nome, '%')) ");
        }

        if(filter.getIdsCategoria() != null && !filter.getIdsCategoria().isEmpty()){
            hql.append("AND p.categoria.id in (:idsCategoria) ");
        }


        var query = entityManager.createQuery(hql.toString(), ProdutoCategoriaGrid.class);
        if(filter.getNome() != null && !filter.getNome().isEmpty() && !filter.getNome().isBlank()){
            query.setParameter("nome", filter.getNome());
        }

        if(filter.getIdsCategoria() != null && !filter.getIdsCategoria().isEmpty()){
            query.setParameter("idsCategoria", filter.getIdsCategoria());
        }

        return query.getResultList();
    }

}
