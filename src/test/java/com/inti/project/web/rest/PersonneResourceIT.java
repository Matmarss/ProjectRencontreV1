package com.inti.project.web.rest;

import com.inti.project.RencontreV1App;
import com.inti.project.domain.Personne;
import com.inti.project.repository.PersonneRepository;
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
 * Integration tests for the {@link PersonneResource} REST controller.
 */
@SpringBootTest(classes = RencontreV1App.class)
public class PersonneResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final String DEFAULT_MOT_DE_PASSE = "AAAAAAAAAA";
    private static final String UPDATED_MOT_DE_PASSE = "BBBBBBBBBB";

    private static final String DEFAULT_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_NAISSANCE = "BBBBBBBBBB";

    private static final Caracteristiques DEFAULT_LIST_CARAC = Caracteristiques.JOYEUX;
    private static final Caracteristiques UPDATED_LIST_CARAC = Caracteristiques.AMICAL;

    @Autowired
    private PersonneRepository personneRepository;

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

    private MockMvc restPersonneMockMvc;

    private Personne personne;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonneResource personneResource = new PersonneResource(personneRepository);
        this.restPersonneMockMvc = MockMvcBuilders.standaloneSetup(personneResource)
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
    public static Personne createEntity(EntityManager em) {
        Personne personne = new Personne()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .mail(DEFAULT_MAIL)
            .genre(DEFAULT_GENRE)
            .motDePasse(DEFAULT_MOT_DE_PASSE)
            .naissance(DEFAULT_NAISSANCE)
            .listCarac(DEFAULT_LIST_CARAC);
        return personne;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createUpdatedEntity(EntityManager em) {
        Personne personne = new Personne()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .mail(UPDATED_MAIL)
            .genre(UPDATED_GENRE)
            .motDePasse(UPDATED_MOT_DE_PASSE)
            .naissance(UPDATED_NAISSANCE)
            .listCarac(UPDATED_LIST_CARAC);
        return personne;
    }

    @BeforeEach
    public void initTest() {
        personne = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne
        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonne.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testPersonne.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testPersonne.getMotDePasse()).isEqualTo(DEFAULT_MOT_DE_PASSE);
        assertThat(testPersonne.getNaissance()).isEqualTo(DEFAULT_NAISSANCE);
        assertThat(testPersonne.getListCarac()).isEqualTo(DEFAULT_LIST_CARAC);
    }

    @Test
    @Transactional
    public void createPersonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne with an existing ID
        personne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneRepository.findAll().size();
        // set the field null
        personne.setNom(null);

        // Create the Personne, which fails.

        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneRepository.findAll().size();
        // set the field null
        personne.setPrenom(null);

        // Create the Personne, which fails.

        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneRepository.findAll().size();
        // set the field null
        personne.setMail(null);

        // Create the Personne, which fails.

        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenreIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneRepository.findAll().size();
        // set the field null
        personne.setGenre(null);

        // Create the Personne, which fails.

        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotDePasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneRepository.findAll().size();
        // set the field null
        personne.setMotDePasse(null);

        // Create the Personne, which fails.

        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneRepository.findAll().size();
        // set the field null
        personne.setNaissance(null);

        // Create the Personne, which fails.

        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get all the personneList
        restPersonneMockMvc.perform(get("/api/personnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].motDePasse").value(hasItem(DEFAULT_MOT_DE_PASSE)))
            .andExpect(jsonPath("$.[*].naissance").value(hasItem(DEFAULT_NAISSANCE)))
            .andExpect(jsonPath("$.[*].listCarac").value(hasItem(DEFAULT_LIST_CARAC.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.motDePasse").value(DEFAULT_MOT_DE_PASSE))
            .andExpect(jsonPath("$.naissance").value(DEFAULT_NAISSANCE))
            .andExpect(jsonPath("$.listCarac").value(DEFAULT_LIST_CARAC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        Personne updatedPersonne = personneRepository.findById(personne.getId()).get();
        // Disconnect from session so that the updates on updatedPersonne are not directly saved in db
        em.detach(updatedPersonne);
        updatedPersonne
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .mail(UPDATED_MAIL)
            .genre(UPDATED_GENRE)
            .motDePasse(UPDATED_MOT_DE_PASSE)
            .naissance(UPDATED_NAISSANCE)
            .listCarac(UPDATED_LIST_CARAC);

        restPersonneMockMvc.perform(put("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonne)))
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonne.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testPersonne.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testPersonne.getMotDePasse()).isEqualTo(UPDATED_MOT_DE_PASSE);
        assertThat(testPersonne.getNaissance()).isEqualTo(UPDATED_NAISSANCE);
        assertThat(testPersonne.getListCarac()).isEqualTo(UPDATED_LIST_CARAC);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Create the Personne

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMockMvc.perform(put("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Delete the personne
        restPersonneMockMvc.perform(delete("/api/personnes/{id}", personne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
