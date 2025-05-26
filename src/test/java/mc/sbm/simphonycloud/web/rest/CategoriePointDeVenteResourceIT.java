package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.CategoriePointDeVenteAsserts.*;
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
import mc.sbm.simphonycloud.domain.CategoriePointDeVente;
import mc.sbm.simphonycloud.repository.CategoriePointDeVenteRepository;
import mc.sbm.simphonycloud.service.dto.CategoriePointDeVenteDTO;
import mc.sbm.simphonycloud.service.mapper.CategoriePointDeVenteMapper;
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
 * Integration tests for the {@link CategoriePointDeVenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriePointDeVenteResourceIT {

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ETABLISSEMENT_REF = 1;
    private static final Integer UPDATED_ETABLISSEMENT_REF = 2;

    private static final String ENTITY_API_URL = "/api/categorie-point-de-ventes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CategoriePointDeVenteRepository categoriePointDeVenteRepository;

    @Autowired
    private CategoriePointDeVenteMapper categoriePointDeVenteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriePointDeVenteMockMvc;

    private CategoriePointDeVente categoriePointDeVente;

    private CategoriePointDeVente insertedCategoriePointDeVente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriePointDeVente createEntity() {
        return new CategoriePointDeVente().categorie(DEFAULT_CATEGORIE).etablissementRef(DEFAULT_ETABLISSEMENT_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriePointDeVente createUpdatedEntity() {
        return new CategoriePointDeVente().categorie(UPDATED_CATEGORIE).etablissementRef(UPDATED_ETABLISSEMENT_REF);
    }

    @BeforeEach
    void initTest() {
        categoriePointDeVente = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCategoriePointDeVente != null) {
            categoriePointDeVenteRepository.delete(insertedCategoriePointDeVente);
            insertedCategoriePointDeVente = null;
        }
    }

    @Test
    @Transactional
    void createCategoriePointDeVente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CategoriePointDeVente
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(categoriePointDeVente);
        var returnedCategoriePointDeVenteDTO = om.readValue(
            restCategoriePointDeVenteMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriePointDeVenteDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CategoriePointDeVenteDTO.class
        );

        // Validate the CategoriePointDeVente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCategoriePointDeVente = categoriePointDeVenteMapper.toEntity(returnedCategoriePointDeVenteDTO);
        assertCategoriePointDeVenteUpdatableFieldsEquals(
            returnedCategoriePointDeVente,
            getPersistedCategoriePointDeVente(returnedCategoriePointDeVente)
        );

        insertedCategoriePointDeVente = returnedCategoriePointDeVente;
    }

    @Test
    @Transactional
    void createCategoriePointDeVenteWithExistingId() throws Exception {
        // Create the CategoriePointDeVente with an existing ID
        categoriePointDeVente.setId(1);
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(categoriePointDeVente);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriePointDeVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriePointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriePointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategoriePointDeVentes() throws Exception {
        // Initialize the database
        insertedCategoriePointDeVente = categoriePointDeVenteRepository.saveAndFlush(categoriePointDeVente);

        // Get all the categoriePointDeVenteList
        restCategoriePointDeVenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriePointDeVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)));
    }

    @Test
    @Transactional
    void getCategoriePointDeVente() throws Exception {
        // Initialize the database
        insertedCategoriePointDeVente = categoriePointDeVenteRepository.saveAndFlush(categoriePointDeVente);

        // Get the categoriePointDeVente
        restCategoriePointDeVenteMockMvc
            .perform(get(ENTITY_API_URL_ID, categoriePointDeVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriePointDeVente.getId().intValue()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF));
    }

    @Test
    @Transactional
    void getNonExistingCategoriePointDeVente() throws Exception {
        // Get the categoriePointDeVente
        restCategoriePointDeVenteMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategoriePointDeVente() throws Exception {
        // Initialize the database
        insertedCategoriePointDeVente = categoriePointDeVenteRepository.saveAndFlush(categoriePointDeVente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categoriePointDeVente
        CategoriePointDeVente updatedCategoriePointDeVente = categoriePointDeVenteRepository
            .findById(categoriePointDeVente.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCategoriePointDeVente are not directly saved in db
        em.detach(updatedCategoriePointDeVente);
        updatedCategoriePointDeVente.categorie(UPDATED_CATEGORIE).etablissementRef(UPDATED_ETABLISSEMENT_REF);
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(updatedCategoriePointDeVente);

        restCategoriePointDeVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriePointDeVenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriePointDeVenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategoriePointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCategoriePointDeVenteToMatchAllProperties(updatedCategoriePointDeVente);
    }

    @Test
    @Transactional
    void putNonExistingCategoriePointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriePointDeVente.setId(intCount.incrementAndGet());

        // Create the CategoriePointDeVente
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(categoriePointDeVente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriePointDeVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriePointDeVenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriePointDeVenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriePointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoriePointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriePointDeVente.setId(intCount.incrementAndGet());

        // Create the CategoriePointDeVente
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(categoriePointDeVente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriePointDeVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriePointDeVenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriePointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoriePointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriePointDeVente.setId(intCount.incrementAndGet());

        // Create the CategoriePointDeVente
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(categoriePointDeVente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriePointDeVenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriePointDeVenteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriePointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriePointDeVenteWithPatch() throws Exception {
        // Initialize the database
        insertedCategoriePointDeVente = categoriePointDeVenteRepository.saveAndFlush(categoriePointDeVente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categoriePointDeVente using partial update
        CategoriePointDeVente partialUpdatedCategoriePointDeVente = new CategoriePointDeVente();
        partialUpdatedCategoriePointDeVente.setId(categoriePointDeVente.getId());

        partialUpdatedCategoriePointDeVente.etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCategoriePointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriePointDeVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategoriePointDeVente))
            )
            .andExpect(status().isOk());

        // Validate the CategoriePointDeVente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategoriePointDeVenteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCategoriePointDeVente, categoriePointDeVente),
            getPersistedCategoriePointDeVente(categoriePointDeVente)
        );
    }

    @Test
    @Transactional
    void fullUpdateCategoriePointDeVenteWithPatch() throws Exception {
        // Initialize the database
        insertedCategoriePointDeVente = categoriePointDeVenteRepository.saveAndFlush(categoriePointDeVente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categoriePointDeVente using partial update
        CategoriePointDeVente partialUpdatedCategoriePointDeVente = new CategoriePointDeVente();
        partialUpdatedCategoriePointDeVente.setId(categoriePointDeVente.getId());

        partialUpdatedCategoriePointDeVente.categorie(UPDATED_CATEGORIE).etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCategoriePointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriePointDeVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategoriePointDeVente))
            )
            .andExpect(status().isOk());

        // Validate the CategoriePointDeVente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategoriePointDeVenteUpdatableFieldsEquals(
            partialUpdatedCategoriePointDeVente,
            getPersistedCategoriePointDeVente(partialUpdatedCategoriePointDeVente)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCategoriePointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriePointDeVente.setId(intCount.incrementAndGet());

        // Create the CategoriePointDeVente
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(categoriePointDeVente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriePointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriePointDeVenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categoriePointDeVenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriePointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoriePointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriePointDeVente.setId(intCount.incrementAndGet());

        // Create the CategoriePointDeVente
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(categoriePointDeVente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriePointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categoriePointDeVenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriePointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoriePointDeVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriePointDeVente.setId(intCount.incrementAndGet());

        // Create the CategoriePointDeVente
        CategoriePointDeVenteDTO categoriePointDeVenteDTO = categoriePointDeVenteMapper.toDto(categoriePointDeVente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriePointDeVenteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(categoriePointDeVenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriePointDeVente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoriePointDeVente() throws Exception {
        // Initialize the database
        insertedCategoriePointDeVente = categoriePointDeVenteRepository.saveAndFlush(categoriePointDeVente);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the categoriePointDeVente
        restCategoriePointDeVenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoriePointDeVente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return categoriePointDeVenteRepository.count();
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

    protected CategoriePointDeVente getPersistedCategoriePointDeVente(CategoriePointDeVente categoriePointDeVente) {
        return categoriePointDeVenteRepository.findById(categoriePointDeVente.getId()).orElseThrow();
    }

    protected void assertPersistedCategoriePointDeVenteToMatchAllProperties(CategoriePointDeVente expectedCategoriePointDeVente) {
        assertCategoriePointDeVenteAllPropertiesEquals(
            expectedCategoriePointDeVente,
            getPersistedCategoriePointDeVente(expectedCategoriePointDeVente)
        );
    }

    protected void assertPersistedCategoriePointDeVenteToMatchUpdatableProperties(CategoriePointDeVente expectedCategoriePointDeVente) {
        assertCategoriePointDeVenteAllUpdatablePropertiesEquals(
            expectedCategoriePointDeVente,
            getPersistedCategoriePointDeVente(expectedCategoriePointDeVente)
        );
    }
}
