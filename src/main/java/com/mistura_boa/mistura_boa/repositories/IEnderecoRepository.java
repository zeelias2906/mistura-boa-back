package com.mistura_boa.mistura_boa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.Endereco;

public interface IEnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("Select e from Endereco e where e.dataExclusao is null AND e.usuario.id = :idUsuario")
    public List<Endereco> findAllByIdUsuario(@Param("idUsuario") Long idUsuario);

}
