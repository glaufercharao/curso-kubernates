package com.gsamtechnology.msvc.cursos.cursos.services.impl;

import com.gsamtechnology.msvc.cursos.cursos.models.entities.Curso;
import com.gsamtechnology.msvc.cursos.cursos.repositories.CursoRepository;
import com.gsamtechnology.msvc.cursos.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CursoServiceImpl implements CursoService {
  @Autowired
  private CursoRepository repository;
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
}
