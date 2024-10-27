package com.gsamtechnology.msvc.cursos.cursos.services;

import com.gsamtechnology.msvc.cursos.cursos.models.Usuario;
import com.gsamtechnology.msvc.cursos.cursos.models.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
  List<Curso> findAll();
  Optional<Curso> findById(Long id);
  Curso save(Curso curso);
  void delete(Long id);
  void deleteCursoUsuarioById(Long id);

  Optional<Usuario> associateUser(Usuario usuario, Long courseId);
  Optional<Usuario> disassociateUser(Usuario usuario, Long courseId);
  Optional<Usuario> createUser(Usuario usuario, Long courseId);
  Optional<Curso> findAllUsersByIds(Long id);
}
