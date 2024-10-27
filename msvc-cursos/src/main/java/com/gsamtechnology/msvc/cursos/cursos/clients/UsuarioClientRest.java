package com.gsamtechnology.msvc.cursos.cursos.clients;

import com.gsamtechnology.msvc.cursos.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuarios", url = "localhost:8002/v1/api/usuarios")
public interface UsuarioClientRest {

  @GetMapping("/{id}")
  Usuario getById(@PathVariable Long id);

  @PostMapping
  Usuario save(@RequestBody Usuario usuario);
}
