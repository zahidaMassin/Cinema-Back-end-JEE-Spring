package mipn.pls.cinema.web;

import lombok.Data;
import mipn.pls.cinema.dao.FilmRepository;
import mipn.pls.cinema.dao.TicketRepository;
import mipn.pls.cinema.entities.Film;
import mipn.pls.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(value = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws IOException {
        Film f = filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        File file = new File(System.getProperty("user.home") + "/Documents/imagesCINEMA/" + photoName + ".jpg");
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);

    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm) {
        List<Ticket> ticketList = new ArrayList<>();
        ticketForm.getTickets().forEach(idTickets -> {
            Ticket ticket = ticketRepository.findById(idTickets).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReserve(true);
            ticketRepository.save(ticket);
            ticketList.add(ticket);

        });
        return ticketList;
    }
}
    @Data
    class TicketForm {
       private String nomClient;
       private int codePayment;
       private List<Long> tickets = new ArrayList<>();
    }

