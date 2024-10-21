package com.gsamtechnology.msvc.usuarios.services.impl;

import com.gsamtechnology.msvc.usuarios.models.entities.Usuario;
import com.gsamtechnology.msvc.usuarios.repositories.UsuarioRepository;
import com.gsamtechnology.msvc.usuarios.services.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
  private UsuarioRepository repository;
  @Override
  @Transactional(readOnly = true)
  public List<Usuario> findAll() {
    return (List<Usuario>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Usuario> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public Usuario save(Usuario usuario) {
    return repository.save(usuario);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }
}