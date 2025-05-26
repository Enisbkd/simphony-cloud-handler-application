package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.PointDeVenteAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.PointDeVente;
import mc.sbm.simphonycloud.repository.PointDeVenteRepository;
import mc.sbm.simphonycloud.service.dto.PointDeVenteDTO;
import mc.sbm.simphonycloud.service.mapper.PointDeVenteMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PointDeVenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointDeVenteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EST_ACTIF = false;
    private static final Boolean UPDATED_EST_ACTIF = true;

    private static final String DEFAULT_ETABLISSEMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT_REF = "BBBBBBBBBB";

    private static final String DEFAULT_HIER_REF = "AAAAAAAAAA";
    private static final String UPDATED_HIER_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-de-ventes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PointDeVenteRepository pointDeVenteRepository;

    @Autowired
    private PointDeVenteMapper pointDeVenteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointDeVenteMockMvc;

    private PointDeVente pointDeVente;

    private PointDeVente insertedPointDeVente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointDeVente createEntity() {
        return new PointDeVente()
            .nom(DEFAULT_NOM)
            .nomCourt(DEFAULT_NOM_COURT)
            .estActif(DEFAULT_EST_ACTIF)
            .etablissementRef(DEFAULT_ETABLISSEMENT_REF)
            .hierRef(DEFAULT_HIER_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointDeVente createUpdatedEntity() {
        return new PointDeVente()
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .estActif(UPDATED_EST_ACTIF)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF)
            .hierRef(UPDATED_HIER_REF);
    }

    @BeforeEach
    void initTest() {
        pointDeVente = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPointDeVente != null) {
            pointDeVenteRepository.delete(insertedPointDeVente);
            insertedPointDeVente = null;
        }
    }

    @Test
    @Transactional
    void createPointDeVente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);
        var returnedPointDeVenteDTO = om.readValue(
            restPointDeVenteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PointDeVenteDTO.class
        );

        // Validate the PointDeVente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPointDeVente = pointDeVenteMapper.toEntity(returnedPointDeVenteDTO);
        assertPointDeVenteUpdatableFieldsEquals(returnedPointDeVente, getPersistedPointDeVente(returnedPointDeVente));

        insertedPointDeVente = returnedPointDeVente;
    }

    @Test
    @Transactional
    void createPointDeVenteWithExistingId() throws Exception {
        // Create the PointDeVente with an existing ID
        pointDeVente.setId(1);
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointDeVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pointDeVente.setNom(null);

        // Create the PointDeVente, which fails.
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        restPointDeVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEtablissementRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pointDeVente.setEtablissementRef(null);

        // Create the PointDeVente, which fails.
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        restPointDeVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHierRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pointDeVente.setHierRef(null);

        // Create the PointDeVente, which fails.
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        restPointDeVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPointDeVentes() throws Exception {
        // Initialize the database
        insertedPointDeVente = pointDeVenteRepository.saveAndFlush(pointDeVente);

        // Get all the pointDeVenteList
        restPointDeVenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointDeVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].estActif").value(hasItem(DEFAULT_EST_ACTIF)))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)))
            .andExpect(jsonPath("$.[*].hierRef").value(hasItem(DEFAULT_HIER_REF)));
    }

    @Test
    @Transactional
    void getPointDeVente() throws Exception {
        // Initialize the database
        insertedPointDeVente = pointDeVenteRepository.saveAndFlush(pointDeVente);

        // Get the pointDeVente
        restPointDeVenteMockMvc
            .perform(get(ENTITY_API_URL_ID, pointDeVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointDeVente.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.estActif").value(DEFAULT_EST_ACTIF))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF))
            .andExpect(jsonPath("$.hierRef").value(DEFAULT_HIER_REF));
    }

    @Test
    @Transactional
    void getNonExistingPointDeVente() throws Exception {
        // Get the pointDeVente
        restPointDeVenteMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPointDeVente() throws Exception {
        // Initialize the database
        insertedPointDeVente = pointDeVenteRepository.saveAndFlush(pointDeVente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVente
        PointDeVente updatedPointDeVente = pointDeVenteRepository.findById(pointDeVente.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPointDeVente are not directly saved in db
        em.detach(updatedPointDeVente);
        updatedPointDeVente
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .estActif(UPDATED_EST_ACTIF)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF)
            .hierRef(UPDATED_HIER_REF);
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(updatedPointDeVente);

        restPointDeVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointDeVenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointDeVenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPointDeVenteToMatchAllProperties(updatedPointDeVente);
    }

    @Test
    @Transactional
    void putNonExistingPointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVente.setId(intCount.incrementAndGet());

        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointDeVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointDeVenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointDeVenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVente.setId(intCount.incrementAndGet());

        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointDeVenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVente.setId(intCount.incrementAndGet());

        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointDeVenteWithPatch() throws Exception {
        // Initialize the database
        insertedPointDeVente = pointDeVenteRepository.saveAndFlush(pointDeVente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVente using partial update
        PointDeVente partialUpdatedPointDeVente = new PointDeVente();
        partialUpdatedPointDeVente.setId(pointDeVente.getId());

        partialUpdatedPointDeVente
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .estActif(UPDATED_EST_ACTIF)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restPointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointDeVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPointDeVente))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPointDeVenteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPointDeVente, pointDeVente),
            getPersistedPointDeVente(pointDeVente)
        );
    }

    @Test
    @Transactional
    void fullUpdatePointDeVenteWithPatch() throws Exception {
        // Initialize the database
        insertedPointDeVente = pointDeVenteRepository.saveAndFlush(pointDeVente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVente using partial update
        PointDeVente partialUpdatedPointDeVente = new PointDeVente();
        partialUpdatedPointDeVente.setId(pointDeVente.getId());

        partialUpdatedPointDeVente
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .estActif(UPDATED_EST_ACTIF)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF)
            .hierRef(UPDATED_HIER_REF);

        restPointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointDeVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPointDeVente))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPointDeVenteUpdatableFieldsEquals(partialUpdatedPointDeVente, getPersistedPointDeVente(partialUpdatedPointDeVente));
    }

    @Test
    @Transactional
    void patchNonExistingPointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVente.setId(intCount.incrementAndGet());

        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointDeVenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pointDeVenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVente.setId(intCount.incrementAndGet());

        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pointDeVenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVente.setId(intCount.incrementAndGet());

        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pointDeVenteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointDeVente() throws Exception {
        // Initialize the database
        insertedPointDeVente = pointDeVenteRepository.saveAndFlush(pointDeVente);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pointDeVente
        restPointDeVenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointDeVente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pointDeVenteRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected PointDeVente getPersistedPointDeVente(PointDeVente pointDeVente) {
        return pointDeVenteRepository.findById(pointDeVente.getId()).orElseThrow();
    }

    protected void assertPersistedPointDeVenteToMatchAllProperties(PointDeVente expectedPointDeVente) {
        assertPointDeVenteAllPropertiesEquals(expectedPointDeVente, getPersistedPointDeVente(expectedPointDeVente));
    }

    protected void assertPersistedPointDeVenteToMatchUpdatableProperties(PointDeVente expectedPointDeVente) {
        assertPointDeVenteAllUpdatablePropertiesEquals(expectedPointDeVente, getPersistedPointDeVente(expectedPointDeVente));
    }
}
