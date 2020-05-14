package com.pk.numberparser.repositories;


import com.pk.numberparser.enteties.Number;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface PhoneNumberRepository extends JpaRepository<Number, Long> {
}