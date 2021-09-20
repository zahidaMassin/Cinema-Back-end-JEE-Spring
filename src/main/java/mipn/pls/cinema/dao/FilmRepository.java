package mipn.pls.cinema.dao;

import mipn.pls.cinema.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("*")
public interface FilmRepository extends JpaRepository<Film,Long> {
}
