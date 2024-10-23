package com.gsamtechnology.msvc.cursos.cursos.models.entities;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb.cursos_usuarios")
public class CursoUsuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "usuario_id", unique = true)
  private Long usuarioId;

  public CursoUsuario() {
  }

  public CursoUsuario(Long id, Long usuarioId) {
    this.id = id;
    this.usuarioId = usuarioId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUsuarioId() {
    return usuarioId;
  }

  public void setUsuarioId(Long usuarioId) {
    this.usuarioId = usuarioId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CursoUsuario that = (CursoUsuario) o;
    return Objects.equals(id, that.id) && Objects.equals(usuarioId, that.usuarioId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, usuarioId);
  }
}
