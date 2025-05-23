package com.mistura_boa.mistura_boa.repositories.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.mistura_boa.mistura_boa.models.entities.Pedido;
import com.mistura_boa.mistura_boa.models.filters.PedidoFilter;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImplPedidoRepository {
    
    private final EntityManager entityManager;


    public Page<Pedido> search(PedidoFilter filter, Pageable pageable) throws Exception{
        var hql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        hql.append("SELECT distinct p ");
        montarQueryAndFilters(hql, filter, params);

        var query = entityManager.createQuery(hql.toString(), Pedido.class);
        params.forEach(query::setParameter);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        var result = query.getResultList();
        return new PageImpl<>(result, pageable, countTotalResults(filter));
    }

    public Long countTotalResults(PedidoFilter filter) throws Exception {
		var hql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

		hql.append(" select count(p.id) ");
		montarQueryAndFilters(hql, filter, params);

        var query = entityManager.createQuery(hql.toString(), Long.class);
        params.forEach(query::setParameter);

        return query.getSingleResult();
	}

    private void montarQueryAndFilters(StringBuilder hql, PedidoFilter filter,  Map<String, Object> params) {
        LocalDate hoje = LocalDate.now();
        LocalDateTime startOfDay = hoje.atStartOfDay();
        LocalDateTime endOfDay = hoje.atTime(LocalTime.MAX);
        
        hql.append("FROM Pedido p ");
        hql.append("WHERE ");
        hql.append("    p.dataPedido NOT BETWEEN :startOfDay AND :endOfDay ");
        params.put("startOfDay", startOfDay);
        params.put("endOfDay", endOfDay);

        if(filter.getDataInicio() != null){
            hql.append("AND p.dataPedido >= :dataInicio ");
            params.put("dataInicio", LocalDateTime.of(filter.getDataInicio(), LocalTime.MIN));
        }

        if(filter.getDataFim() != null){
            hql.append("AND p.dataPedido <= :dataFim ");
            params.put("dataFim", LocalDateTime.of(filter.getDataFim(), LocalTime.MAX));
        }

        hql.append(" ORDER BY p.dataPedido DESC");
    }

}
