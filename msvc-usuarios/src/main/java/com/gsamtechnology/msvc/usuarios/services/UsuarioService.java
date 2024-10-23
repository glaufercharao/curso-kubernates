package com.gsamtechnology.msvc.usuarios.services;

import com.gsamtechnology.msvc.usuarios.models.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
  List<Usuario> findAll();
  Optional<Usuario> findById(Long id);
  Optional<Usuario> findByEmail(String email);
  Usuario save(Usuario usuario);
  void delete(Long id);
}
