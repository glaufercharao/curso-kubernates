package com.gsamtechnology.msvc.usuarios.controllers;

import com.gsamtechnology.msvc.usuarios.models.entities.Usuario;
import com.gsamtechnology.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
      return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
      return ResponseEntity.status(HttpStatus.OK).body(service.findById(id).orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> edit(@PathVariable Long id, @RequestBody Usuario usuario){
        usuario.setId(id);
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> removeById(@PathVariable Long id){
      service.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
