package mipn.pls.cinema.dao;

import mipn.pls.cinema.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("*")
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
