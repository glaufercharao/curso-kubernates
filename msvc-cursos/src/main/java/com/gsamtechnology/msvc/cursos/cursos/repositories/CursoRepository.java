package com.gsamtechnology.msvc.cursos.cursos.repositories;

import com.gsamtechnology.msvc.cursos.cursos.models.entities.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository  extends CrudRepository<Curso, Long> {
  @Modifying
  @Query("DELETE FROM CursoUsuario cu WHERE cu.usuarioId=?1")
  void deleteCursoUsuarioById(Long id);
}
