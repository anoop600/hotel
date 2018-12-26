package com.example.databaseProject.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.example.databaseProject.model.Hotel;

@RepositoryRestResource
public interface HotelRepository extends JpaRepository<Hotel, String>{

}
