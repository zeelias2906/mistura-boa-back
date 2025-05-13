package com.mistura_boa.mistura_boa.repositories.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mistura_boa.mistura_boa.models.entities.Pedido;
import com.mistura_boa.mistura_boa.models.filters.PedidoFilter;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImplPedidoRepository {
    
    private final EntityManager entityManager;


    public List<Pedido> search(PedidoFilter filter){
        LocalDate hoje = LocalDate.now();
        LocalDateTime startOfDay = hoje.atStartOfDay();
        LocalDateTime endOfDay = hoje.atTime(LocalTime.MAX);

        var hql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        
        hql.append("SELECT distinct p ");
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

        var query = entityManager.createQuery(hql.toString(), Pedido.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

}
