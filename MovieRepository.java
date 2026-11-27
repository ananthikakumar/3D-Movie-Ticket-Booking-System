
package com.cineverse.cineverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cineverse.cineverse.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
