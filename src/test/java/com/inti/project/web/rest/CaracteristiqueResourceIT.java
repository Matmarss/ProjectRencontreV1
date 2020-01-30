package com.inti.project.web.rest;

import com.inti.project.RencontreV1App;
import com.inti.project.domain.Caracteristique;
import com.inti.project.repository.CaracteristiqueRepository;
import com.inti.project.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.inti.project.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.inti.project.domain.enumeration.Caracteristiques;
/**
 * Integration tests for the {@link CaracteristiqueResource} REST controller.
 */
@SpringBootTest(classes = RencontreV1App.class)
public class CaracteristiqueResourceIT {

    private static final Caracteristiques DEFAULT_CARAC = Caracteristiques.JOYEUX;
    private static final Caracteristiques UPDATED_CARAC = Caracteristiques.AMICAL;

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCaracteristiqueMockMvc;

    private Caracteristique caracteristique;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaracteristiqueResource caracteristiqueResource = new CaracteristiqueResource(caracteristiqueRepository);
        this.restCaracteristiqueMockMvc = MockMvcBuilders.standaloneSetup(caracteristiqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caracteristique createEntity(EntityManager em) {
        Caracteristique caracteristique = new Caracteristique()
            .carac(DEFAULT_CARAC);
        return caracteristique;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caracteristique createUpdatedEntity(EntityManager em) {
        Caracteristique caracteristique = new Caracteristique()
            .carac(UPDATED_CARAC);
        return caracteristique;
    }

    @BeforeEach
    public void initTest() {
        caracteristique = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaracteristique() throws Exception {
        int databaseSizeBeforeCreate = caracteristiqueRepository.findAll().size();

        // Create the Caracteristique
        restCaracteristiqueMockMvc.perform(post("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristique)))
            .andExpect(status().isCreated());

        // Validate the Caracteristique in the database
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Caracteristique testCaracteristique = caracteristiqueList.get(caracteristiqueList.size() - 1);
        assertThat(testCaracteristique.getCarac()).isEqualTo(DEFAULT_CARAC);
    }

    @Test
    @Transactional
    public void createCaracteristiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caracteristiqueRepository.findAll().size();

        // Create the Caracteristique with an existing ID
        caracteristique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristiqueMockMvc.perform(post("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristique)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristique in the database
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCaracteristiques() throws Exception {
        // Initialize the database
        caracteristiqueRepository.saveAndFlush(caracteristique);

        // Get all the caracteristiqueList
        restCaracteristiqueMockMvc.perform(get("/api/caracteristiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristique.getId().intValue())))
            .andExpect(jsonPath("$.[*].carac").value(hasItem(DEFAULT_CARAC.toString())));
    }
    
    @Test
    @Transactional
    public void getCaracteristique() throws Exception {
        // Initialize the database
        caracteristiqueRepository.saveAndFlush(caracteristique);

        // Get the caracteristique
        restCaracteristiqueMockMvc.perform(get("/api/caracteristiques/{id}", caracteristique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caracteristique.getId().intValue()))
            .andExpect(jsonPath("$.carac").value(DEFAULT_CARAC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaracteristique() throws Exception {
        // Get the caracteristique
        restCaracteristiqueMockMvc.perform(get("/api/caracteristiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaracteristique() throws Exception {
        // Initialize the database
        caracteristiqueRepository.saveAndFlush(caracteristique);

        int databaseSizeBeforeUpdate = caracteristiqueRepository.findAll().size();

        // Update the caracteristique
        Caracteristique updatedCaracteristique = caracteristiqueRepository.findById(caracteristique.getId()).get();
        // Disconnect from session so that the updates on updatedCaracteristique are not directly saved in db
        em.detach(updatedCaracteristique);
        updatedCaracteristique
            .carac(UPDATED_CARAC);

        restCaracteristiqueMockMvc.perform(put("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaracteristique)))
            .andExpect(status().isOk());

        // Validate the Caracteristique in the database
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeUpdate);
        Caracteristique testCaracteristique = caracteristiqueList.get(caracteristiqueList.size() - 1);
        assertThat(testCaracteristique.getCarac()).isEqualTo(UPDATED_CARAC);
    }

    @Test
    @Transactional
    public void updateNonExistingCaracteristique() throws Exception {
        int databaseSizeBeforeUpdate = caracteristiqueRepository.findAll().size();

        // Create the Caracteristique

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristiqueMockMvc.perform(put("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristique)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristique in the database
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaracteristique() throws Exception {
        // Initialize the database
        caracteristiqueRepository.saveAndFlush(caracteristique);

        int databaseSizeBeforeDelete = caracteristiqueRepository.findAll().size();

        // Delete the caracteristique
        restCaracteristiqueMockMvc.perform(delete("/api/caracteristiques/{id}", caracteristique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
