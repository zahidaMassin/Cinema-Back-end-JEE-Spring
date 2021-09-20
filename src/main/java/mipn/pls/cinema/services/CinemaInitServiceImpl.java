package mipn.pls.cinema.services;

import mipn.pls.cinema.dao.*;
import mipn.pls.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionFilmRepository projectionFilmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void initVilles() {
        Stream.of("Paris","Lille","Marseille","Grenoble").forEach(v -> {
            Ville ville = new Ville();
            ville.setName(v);
            villeRepository.save(ville);
        });

    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v -> {
            Stream.of("Mk2", "UGC", "Pathe").forEach(nameCinema -> {
                Cinema cinema = new Cinema();
                cinema.setName(nameCinema);
                cinema.setVille(v);
                cinema.setNombreSalles(3+(int)(Math.random()*7));
                cinemaRepository.save(cinema);
            });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(v -> {
            for (int i=0 ; i<v.getNombreSalles() ; i++){
                Salle salle = new Salle();
                salle.setName("Salle"+(i+1));
                salle.setCinema(v);
                salle.setNombrePlaces(15+(int) (Math.random()*20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
     salleRepository.findAll().forEach(salle -> {
         for (int i=0 ; i<salle.getNombrePlaces();i++){
             Place place = new Place();
             place.setNumero( i+1);
             place.setSalle(salle);
             placeRepository.save(place);
         }

        });
    }


    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("14:00","15:00","19:00","21:00").forEach(s -> {
        Seance seance = new Seance();
        try {
            seance.setHeureDebut(dateFormat.parse(s));
            seanceRepository.save(seance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    });
    }

    @Override
    public void initCategories() {
        Stream.of("Action","Fiction","Drama","Fantasia").forEach(c -> {
            Categorie categorie = new Categorie();
            categorie.setName(c);
            categoriesRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        double[] durees = new double[] {1.0,1.5,2.5,1.4};
        List<Categorie> categories = categoriesRepository.findAll();
        Stream.of("1 9 1 7","Once Upon a time", "PARASITE","THE JOKER","The irish man").forEach(f -> {
            Film film = new Film();
            film.setTitre(f);
            film.setDuree(durees[ new  Random().nextInt(durees.length)]);
            film.setPhoto(f.replaceAll(" ",""));
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjectionFilms() {
        double[] prices = new double[] {6,5,4,10,7,8};
        List<Film> films = filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    Film film = films.get(index);
                    //filmRepository.findAll().forEach(film -> {
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionFilmRepository.save(projection);
                        });
                    });
                });
            });
    }

    @Override
    public void initTickets() {
     projectionFilmRepository.findAll().forEach(p -> {
          p.getSalle().getPlaces().forEach(place -> {
             Ticket ticket = new Ticket();
             ticket.setPlace(place);
             ticket.setPrix(p.getPrix());
             ticket.setProjection(p);
             ticket.setReserve(false);
             ticketRepository.save(ticket);
         });
     });
    }
}
