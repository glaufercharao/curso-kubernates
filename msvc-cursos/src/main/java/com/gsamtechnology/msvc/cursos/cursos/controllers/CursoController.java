package com.gsamtechnology.msvc.cursos.cursos.controllers;

import com.gsamtechnology.msvc.cursos.cursos.models.entities.Curso;
import com.gsamtechnology.msvc.cursos.cursos.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> getAll(){
      return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getById(@PathVariable Long id){
      return ResponseEntity.status(HttpStatus.OK).body(service.findById(id).orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Curso curso, BindingResult result){
      if(result.hasErrors()){
        return ResponseEntity.badRequest().body(listErrors(result));
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody Curso curso, BindingResult result){
      if(result.hasErrors()){
        return ResponseEntity.badRequest().body(listErrors(result));
      }
        curso.setId(id);
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> removeById(@PathVariable Long id){
      service.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Map<String, String> listErrors(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach( fieldError -> errors.put(fieldError.getField(), "O campo "+ fieldError.getField()+ " "+ fieldError.getDefaultMessage()));
      return errors;
    }
}
