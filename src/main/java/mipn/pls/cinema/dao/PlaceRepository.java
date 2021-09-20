package mipn.pls.cinema.dao;

import mipn.pls.cinema.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("*")
public interface PlaceRepository extends JpaRepository<Place,Long> {
}
