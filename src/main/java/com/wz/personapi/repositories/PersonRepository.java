package com.wz.personapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wz.personapi.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByCpf(String cpf);

}
