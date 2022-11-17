package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

// MEDIATES BETWEEN JPA-REPOSITORY AND COFFEE-REPOSITORY SO THE METHODS IN JPA-REPOSITORY
// CAN BE USED BY OUR COFFEE-CONTROLLER------------------------------------>
public interface CoffeeRepository extends JpaRepository<Coffee,Long> {

}
