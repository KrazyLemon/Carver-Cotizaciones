package com.example.carver.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    //SELECT * FROM usuario WHERE id = ?
    @Override
    Optional<Usuario> findById(Integer UserId);
    //SELECT * FROM usuario WHERE email = ?
    Optional<Usuario> findByEmail(String UserEmail);

}
