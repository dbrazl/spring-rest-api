package com.crud.src.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crud.src.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	@Query("select p from Person p order by id")
	List<Person> findAllAndSort();
}
