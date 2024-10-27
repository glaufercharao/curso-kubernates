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
@Service
public class CursoServiceImpl implements CursoService {
  @Autowired
  private CursoRepository repository;
  @Autowired
  private UsuarioClientRest clientRest;
  @Override
  public List<Curso> findAll() {
    return (List<Curso>) repository.findAll();
  }

  @Override
  public Optional<Curso> findById(Long id) {
    return repository.findById(id);
  }

  @Override
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
  public void delete(Long id) {
    repository.deleteById(id);
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
      throw new RuntimeException("Erro de comunicação com cliente: "+exception.getMessage());
    }
  }
}
