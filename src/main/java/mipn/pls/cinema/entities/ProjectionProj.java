package mipn.pls.cinema.entities;

import org.springframework.data.rest.core.config.Projection;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.Date;

@Projection(name = "p1",types = mipn.pls.cinema.entities.Projection.class)
public interface ProjectionProj {
    public Long getId();
    public Date getDateProjection();
    public Film getFilm();
    public  double getPrix();
    public Salle getSalle();
    public Collection<Ticket> getTickets();
    public Seance getSeance();



}
