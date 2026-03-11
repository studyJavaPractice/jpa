package com.jpa.project_jpa.persitence.repository;


import org.springframework.data.repository.ListCrudRepository;
import com.jpa.project_jpa.persitence.entity.PizzaEntity;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
}