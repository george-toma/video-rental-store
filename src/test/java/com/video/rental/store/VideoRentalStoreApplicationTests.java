package com.video.rental.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.rental.store.api.model.inbound.RentInboundDto;
import com.video.rental.store.domain.Customer;
import com.video.rental.store.domain.Film;
import com.video.rental.store.domain.FilmType;
import com.video.rental.store.domain.Price;
import com.video.rental.store.graph.GraphDatabase;
import com.video.rental.store.repository.CustomerRepository;
import com.video.rental.store.repository.FilmRepository;
import org.assertj.core.util.Lists;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VideoRentalStoreApplicationTests {

    @Autowired
    private MockMvc videoRentalStore;

    private final ObjectMapper jsonConverter = new ObjectMapper();

    @ClassRule
    public static GraphDatabase graphDatabase = new GraphDatabase();

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Before
    public void setup() {
        graphDatabase.deleteAllNodesAndRelationships();
    }

    @After
    public void tearDown() {
        graphDatabase.deleteAllNodesAndRelationships();
    }

    @Test
    public void test_FindFilm_OnNotFoundFilm() throws Exception {

        //given
        //negative scenario

        //when, then
        videoRentalStore.perform(get("/store/rent/film?titles=not-found-film"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void test_FindFilm_OnSuccess() throws Exception {

        //given
        createFilm("Akira", FilmType.OLD_FILM, new Price(30.0D));

        //when, then
        videoRentalStore.perform(get("/store/rent/film?titles=Akira"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"title\":\"Akira\",\"price\":30.0,\"type\":\"OLD_FILM\"}]", true));
    }

    @Test
    public void test_RentFilm_OnSuccess() throws Exception {

        //given
        createFilm("Matrix 11", FilmType.NEW_RELEASE, new Price(40.0D));

        //when, then
        RentInboundDto rentInboundDto = createMatrix11RentRequest("Michael McPharsen", "Matrix 11", 5);

        videoRentalStore.perform(
                post("/store/rent/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConverter.writeValueAsBytes(rentInboundDto))
        )
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(FileUtil.readJsonResource("matrix11-to-rent-response"), true));
    }

    @Test
    public void test_RentFilm_OnNotFoundFilm() throws Exception {

        //given
        //negative scenario

        //when, then
        RentInboundDto rentInboundDto = createMatrix11RentRequest("Unknown",
                "not-found-movie", 5);

        videoRentalStore.perform(
                post("/store/rent/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConverter.writeValueAsBytes(rentInboundDto))
        )
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(FileUtil.readJsonResource("not-found-movie"), true));

        Customer customer = customerRepository.findByName("Unknown");

        Assert.assertNull("No customer saved in db", customer);
    }


    @Test
    @Ignore("Need more time to implement this")
    public void test_ReturnRent_OnSuccess() {
        //FIXME implement me
    }

    @Test
    @Ignore("Need more time to implement this")
    public void test_ReturnRent_WhenExtraCharge() {
        //FIXME implement me
    }


    private void createFilm(String title, FilmType newRelease, Price price) {
        Film film = new Film();
        film.setPrice(price);
        film.setTitle(title);
        film.setType(newRelease);

        filmRepository.save(film);
    }

    private RentInboundDto createMatrix11RentRequest(String customerName, String filmTitle, int rentPeriod) {
        RentInboundDto rentInboundDto = new RentInboundDto();
        rentInboundDto.setCustomerName(customerName);

        RentInboundDto.InboundFilmDescriptionDto filmDescriptionDto = new RentInboundDto.InboundFilmDescriptionDto();
        filmDescriptionDto.setTitle(filmTitle);
        filmDescriptionDto.setRentingPeriod(rentPeriod);
        rentInboundDto.setFilmsToRent(Lists.newArrayList(filmDescriptionDto));
        return rentInboundDto;
    }
}
