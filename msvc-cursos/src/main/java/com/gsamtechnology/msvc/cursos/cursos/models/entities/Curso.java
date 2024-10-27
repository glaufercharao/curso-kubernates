package com.gsamtechnology.msvc.cursos.cursos.models.entities;

import com.gsamtechnology.msvc.cursos.cursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb.cursos")
public class Curso implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty
  private String nome;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "curso_id")
  private List<CursoUsuario> cursoUsuarios = new ArrayList<>();
  @Transient
  private List<Usuario> usuarios = new ArrayList<>();

  public Curso() {
  }

  public Curso(Long id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public List<CursoUsuario> getCursoUsuarios() {
    return cursoUsuarios;
  }

  public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
    this.cursoUsuarios = cursoUsuarios;
  }
  public void addCursoUsuario(CursoUsuario cursoUsuario){
    cursoUsuarios.add(cursoUsuario);
  }
  public void removeCursoUsuario(CursoUsuario cursoUsuario){
    cursoUsuarios.remove(cursoUsuario);
  }

  public List<Usuario> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
  }
}
