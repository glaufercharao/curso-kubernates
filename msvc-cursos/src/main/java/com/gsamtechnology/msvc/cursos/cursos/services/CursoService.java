package com.gsamtechnology.msvc.cursos.cursos.services;

import com.gsamtechnology.msvc.cursos.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
  List<Curso> findAll();
  Optional<Curso> findById(Long id);
  Curso save(Curso curso);
  void delete(Long id);
}
