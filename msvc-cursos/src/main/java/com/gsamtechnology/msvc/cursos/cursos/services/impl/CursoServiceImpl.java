package com.gsamtechnology.msvc.cursos.cursos.services.impl;

import com.gsamtechnology.msvc.cursos.cursos.clients.UsuarioClientRest;
import com.gsamtechnology.msvc.cursos.cursos.models.Usuario;
import com.gsamtechnology.msvc.cursos.cursos.models.entities.Curso;
import com.gsamtechnology.msvc.cursos.cursos.models.entities.CursoUsuario;
import com.gsamtechnology.msvc.cursos.cursos.repositories.CursoRepository;
import com.gsamtechnology.msvc.cursos.cursos.services.CursoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {
  @Autowired
  private CursoRepository repository;
  @Autowired
  private UsuarioClientRest clientRest;
  @Override
  @Transactional(readOnly = true)
  public List<Curso> findAll() {
    return (List<Curso>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Curso> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public Curso save(Curso curso) {
    if(curso.getId() != null){
      Optional<Curso> cursoDB = findById(curso.getId());
      if (!cursoDB.isPresent()){
        throw new RuntimeException("Curso não encontrado!");
      }
      cursoDB.get().setNome(curso.getNome());
      return repository.save(cursoDB.get());
    }
    return repository.save(curso);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteCursoUsuarioById(Long id) {
    repository.deleteCursoUsuarioById(id);
  }

  @Override
  @Transactional
  public Optional<Usuario> associateUser(Usuario usuario, Long courseId) {
    Optional<Curso> curso = repository.findById(courseId);
    if (!curso.isPresent()){
      throw new RuntimeException("Erro");
    }
    try {
      Usuario usuarioMsvc = clientRest.getById(usuario.getId());
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioMsvc.getId());
      curso.get().addCursoUsuario(cursoUsuario);
      repository.save(curso.get());
      return Optional.of(usuarioMsvc);
    }catch (FeignException.FeignClientException exception){
      throw new RuntimeException("Erro de comunicação com cliente");
    }
  }

  @Override
  @Transactional
  public Optional<Usuario> disassociateUser(Usuario usuario, Long courseId) {
    Optional<Curso> curso = repository.findById(courseId);
    if (!curso.isPresent()){
      throw new RuntimeException("Erro");
    }
    try {
      Usuario usuarioMsvc = clientRest.getById(usuario.getId());
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioMsvc.getId());
      curso.get().removeCursoUsuario(cursoUsuario);
      repository.save(curso.get());
      return Optional.of(usuarioMsvc);
    }catch (FeignException.FeignClientException exception){
      throw new RuntimeException("Erro de comunicação com cliente");
    }
  }

  @Override
  @Transactional
  public Optional<Usuario> createUser(Usuario usuario, Long courseId) {
    Optional<Curso> curso = repository.findById(courseId);
    if (!curso.isPresent()){
      throw new RuntimeException("Erro");
    }
    try {
      Usuario usuarioMsvcNovo = clientRest.save(usuario);
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioMsvcNovo.getId());
      curso.get().addCursoUsuario(cursoUsuario);
      repository.save(curso.get());
      return Optional.of(usuarioMsvcNovo);
    }catch (FeignException.FeignClientException exception){
      throw new RuntimeException("Erro de comunicação com cliente: " + exception.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Curso> findAllUsersByIds(Long id) {
    Optional<Curso> curso = repository.findById(id);
    if (!curso.isPresent()){
      throw new RuntimeException("Erro em buscar todos usuarios");
    }
    if (!curso.get().getCursoUsuarios().isEmpty()){
      List<Long> ids = curso.get().getCursoUsuarios().stream()
              .map(cu -> cu.getUsuarioId()).collect(Collectors.toList());
      try {
        List<Usuario> usuarios = clientRest.getAllByIds(ids);
        curso.get().setUsuarios(usuarios);
      }catch (FeignException feignException){
        throw new RuntimeException("Erro de comunicação com cliente: " + feignException.getMessage());
      }
    }
    return curso;
  }
}
