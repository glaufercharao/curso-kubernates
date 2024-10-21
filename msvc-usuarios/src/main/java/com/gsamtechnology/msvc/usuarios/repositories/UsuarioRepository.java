package com.gsamtechnology.msvc.usuarios.repositories;

import com.gsamtechnology.msvc.usuarios.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
