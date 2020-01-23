package com.crud.src.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crud.src.entity.Person;
import com.crud.src.repository.PersonRepository;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository _personRepository;
	
	@GetMapping("/pessoa")
	public ResponseEntity<List<Person>> Get() {
		return new ResponseEntity<List<Person>>(_personRepository.findAllAndSort(), HttpStatus.OK);
	}
	
	@GetMapping("/pessoa/{id}")
	public ResponseEntity<Person> GetById (@PathVariable(value = "id") long id) {
		
		Optional<Person> pessoa = _personRepository.findById(id);
		if(pessoa.isPresent())
			return new ResponseEntity<Person>(pessoa.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/pessoa")
	public Person Post (@Valid @RequestBody Person person) {
		return _personRepository.save(person);
	}
	
	@PutMapping("/pessoa/{id}")
	public ResponseEntity<Person> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Person person) {
		Optional<Person> oldPerson = _personRepository.findById(id);
		
		if(oldPerson.isPresent()) {
			Person newPerson = oldPerson.get();
			
			newPerson.setName(person.getName());
			
			_personRepository.save(newPerson);
			return new ResponseEntity<Person>(newPerson, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
		Optional<Person> person = _personRepository.findById(id);
		
		if(person.isPresent()) {
			_personRepository.delete(person.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
