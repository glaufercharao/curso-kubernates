package com.gsamtechnology.msvc.usuarios.controllers;

import com.gsamtechnology.msvc.usuarios.models.entities.Usuario;
import com.gsamtechnology.msvc.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
      return ResponseEntity.ok(service.findAll());
    }
  @GetMapping("/por-curso")
  public ResponseEntity<List<Usuario>> getAllByIds(@RequestParam List<Long> ids){
      return ResponseEntity.ok(service.findAllByIds(ids));
  }

  @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
      return ResponseEntity.status(HttpStatus.OK).body(service.findById(id).orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario, BindingResult result){
      if(service.findByEmail(usuario.getEmail()).isPresent()){
        return ResponseEntity.badRequest().body(Collections.singletonMap("Menssagem", "E-mail já cadastrado"));
      }

      if(result.hasErrors()){
        return ResponseEntity.badRequest().body(listErrors(result));
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result){
      if(result.hasErrors()){
        return ResponseEntity.badRequest().body(listErrors(result));
      }
        usuario.setId(id);
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> removeById(@PathVariable Long id){
      service.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private Map<String, String> listErrors(BindingResult result){
      Map<String, String> errors = new HashMap<>();
      result.getFieldErrors().forEach( fieldError -> errors.put(fieldError.getField(), "O campo "+ fieldError.getField()+ " "+ fieldError.getDefaultMessage()));
      return errors;
    }
}
