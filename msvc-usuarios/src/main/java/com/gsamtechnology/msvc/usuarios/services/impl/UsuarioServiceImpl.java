package com.gsamtechnology.msvc.usuarios.services.impl;

import com.gsamtechnology.msvc.usuarios.client.CursoClientRest;
import com.gsamtechnology.msvc.usuarios.models.entities.Usuario;
import com.gsamtechnology.msvc.usuarios.repositories.UsuarioRepository;
import com.gsamtechnology.msvc.usuarios.services.UsuarioService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
  @Autowired
  private UsuarioRepository repository;
  @Autowired
  private CursoClientRest clientRest;
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
  @Transactional(readOnly = true)
  public Optional<Usuario> findByEmail(String email) {
    return repository.findByEmail(email);
  }

  @Override
  @Transactional
  public Usuario save(Usuario usuario) {
    if(usuario.getId() != null){
      Optional<Usuario> usuarioDB = findById(usuario.getId());
      if (!usuarioDB.isPresent()){
        throw new RuntimeException("Usuario não encontrado!");
      }
      usuarioDB.get().setNome(usuario.getNome());
      usuarioDB.get().setEmail(usuario.getEmail());
      usuarioDB.get().setPassword(usuario.getPassword());
      return repository.save(usuarioDB.get());
    }
    return repository.save(usuario);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
    try {
      clientRest.deleteCursoUsuarioById(id);
    }catch (FeignException feignException){
      throw new RuntimeException("Erro ao remover usuario no serviço cursos.");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<Usuario> findAllByIds(Iterable<Long> ids) {
    return (List<Usuario>) repository.findAllById(ids);
  }
}
