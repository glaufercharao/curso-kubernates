package com.gsamtechnology.msvc.cursos.cursos.repositories;

import com.gsamtechnology.msvc.cursos.cursos.models.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository  extends CrudRepository<Curso, Long> {
}
