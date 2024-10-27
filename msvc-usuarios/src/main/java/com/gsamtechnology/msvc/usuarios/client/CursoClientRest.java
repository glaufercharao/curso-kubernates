package com.gsamtechnology.msvc.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "localhost:8001//v1/api/cursos")
public interface CursoClientRest {

  @DeleteMapping("deletar-usuario/{id}")
  void deleteCursoUsuarioById(@PathVariable Long id);
}

