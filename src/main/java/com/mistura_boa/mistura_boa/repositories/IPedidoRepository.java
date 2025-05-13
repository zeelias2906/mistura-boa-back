package com.mistura_boa.mistura_boa.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.Pedido;
import com.mistura_boa.mistura_boa.models.enums.StatusPedidoEnum;

public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("Select p from Pedido p where p.usuario.id = :idUsuario order by p.dataPedido desc")
    List<Pedido> findByIdUsuario(@Param("idUsuario") Long idUsuario);

    @Query("Select p from Pedido p where p.dataPedido between :startOfDay and :endOfDay and p.statusPedido = :status order by p.dataPedido asc")
    List<Pedido> findAllTodayByStatus(@Param("startOfDay")LocalDateTime startOfDay, @Param("endOfDay")LocalDateTime endOfDay, @Param("status") StatusPedidoEnum status);

}
