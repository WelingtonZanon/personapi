package com.wz.personapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wz.personapi.entities.Person;

public interface PersonTepository extends JpaRepository<Person, Long>{

}
