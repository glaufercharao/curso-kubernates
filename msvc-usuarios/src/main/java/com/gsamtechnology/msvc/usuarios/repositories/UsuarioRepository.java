package com.gsamtechnology.msvc.usuarios.repositories;

import com.gsamtechnology.msvc.usuarios.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
  Optional<Usuario> findUsuarioByEmail(String email);
}
