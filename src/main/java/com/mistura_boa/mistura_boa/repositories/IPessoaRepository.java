package com.mistura_boa.mistura_boa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mistura_boa.mistura_boa.models.entities.Pessoa;

public interface IPessoaRepository  extends JpaRepository<Pessoa, Long> {

    public Pessoa findByCpf(String cpf);

    @Query("Select count(p.id)>0 from Pessoa p where lower(p.cpf) = lower(:cpf) and (:id IS NULL OR p.id != :id)")
    public boolean isCpfUnique(@Param("cpf") String cpf, @Param("id") Long id);

}
