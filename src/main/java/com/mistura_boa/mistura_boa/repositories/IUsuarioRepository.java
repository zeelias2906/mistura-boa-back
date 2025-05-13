package com.mistura_boa.mistura_boa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.mistura_boa.mistura_boa.models.entities.Usuario;


public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    public UserDetails findByEmail(String email);

    @Query("Select case when count(u.id)>0 then true else false end from Usuario u where lower(u.email) = lower(:email) and (:id IS NULL OR u.id != :id)")
    public boolean isEmailUnique(@Param("email") String email, @Param("id") Long id);

}
