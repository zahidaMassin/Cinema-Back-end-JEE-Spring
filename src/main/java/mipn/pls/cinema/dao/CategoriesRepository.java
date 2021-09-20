package mipn.pls.cinema.dao;

import mipn.pls.cinema.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("*")
public interface CategoriesRepository extends JpaRepository<Categorie, Long> {
}
