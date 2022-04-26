package com.finnegan.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface OwnerRepository extends CrudRepository<Owner, Long> {
    // fetch owner by email
    List<Owner> findByEmail(@Param("email") String email);


}
