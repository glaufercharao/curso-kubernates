package com.gsamtechnology.msvc.cursos.cursos.controllers;

import com.gsamtechnology.msvc.cursos.cursos.models.entity.Curso;
import com.gsamtechnology.msvc.cursos.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Curso> save(@RequestBody Curso curso){
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> edit(@PathVariable Long id, @RequestBody Curso curso){
        curso.setId(id);
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> removeById(@PathVariable Long id){
      service.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
