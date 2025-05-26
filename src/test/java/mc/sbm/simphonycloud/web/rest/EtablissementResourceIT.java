package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.EtablissementAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.Etablissement;
import mc.sbm.simphonycloud.repository.EtablissementRepository;
import mc.sbm.simphonycloud.service.dto.EtablissementDTO;
import mc.sbm.simphonycloud.service.mapper.EtablissementMapper;
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
 * Integration tests for the {@link EtablissementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtablissementResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EST_GROUPE = false;
    private static final Boolean UPDATED_EST_GROUPE = true;

    private static final String DEFAULT_SOURCE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIETE_REF = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETE_REF = "BBBBBBBBBB";

    private static final String DEFAULT_HIER_REF = "AAAAAAAAAA";
    private static final String UPDATED_HIER_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etablissements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EtablissementRepository etablissementRepository;

    @Autowired
    private EtablissementMapper etablissementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtablissementMockMvc;

    private Etablissement etablissement;

    private Etablissement insertedEtablissement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etablissement createEntity() {
        return new Etablissement()
            .nom(DEFAULT_NOM)
            .estGroupe(DEFAULT_EST_GROUPE)
            .sourceVersion(DEFAULT_SOURCE_VERSION)
            .societeRef(DEFAULT_SOCIETE_REF)
            .hierRef(DEFAULT_HIER_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etablissement createUpdatedEntity() {
        return new Etablissement()
            .nom(UPDATED_NOM)
            .estGroupe(UPDATED_EST_GROUPE)
            .sourceVersion(UPDATED_SOURCE_VERSION)
            .societeRef(UPDATED_SOCIETE_REF)
            .hierRef(UPDATED_HIER_REF);
    }

    @BeforeEach
    void initTest() {
        etablissement = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEtablissement != null) {
            etablissementRepository.delete(insertedEtablissement);
            insertedEtablissement = null;
        }
    }

    @Test
    @Transactional
    void createEtablissement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);
        var returnedEtablissementDTO = om.readValue(
            restEtablissementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissementDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EtablissementDTO.class
        );

        // Validate the Etablissement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEtablissement = etablissementMapper.toEntity(returnedEtablissementDTO);
        assertEtablissementUpdatableFieldsEquals(returnedEtablissement, getPersistedEtablissement(returnedEtablissement));

        insertedEtablissement = returnedEtablissement;
    }

    @Test
    @Transactional
    void createEtablissementWithExistingId() throws Exception {
        // Create the Etablissement with an existing ID
        etablissement.setId("existing_id");
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        etablissement.setNom(null);

        // Create the Etablissement, which fails.
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        restEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSocieteRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        etablissement.setSocieteRef(null);

        // Create the Etablissement, which fails.
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        restEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHierRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        etablissement.setHierRef(null);

        // Create the Etablissement, which fails.
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        restEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEtablissements() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        // Get all the etablissementList
        restEtablissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etablissement.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].estGroupe").value(hasItem(DEFAULT_EST_GROUPE)))
            .andExpect(jsonPath("$.[*].sourceVersion").value(hasItem(DEFAULT_SOURCE_VERSION)))
            .andExpect(jsonPath("$.[*].societeRef").value(hasItem(DEFAULT_SOCIETE_REF)))
            .andExpect(jsonPath("$.[*].hierRef").value(hasItem(DEFAULT_HIER_REF)));
    }

    @Test
    @Transactional
    void getEtablissement() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        // Get the etablissement
        restEtablissementMockMvc
            .perform(get(ENTITY_API_URL_ID, etablissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etablissement.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.estGroupe").value(DEFAULT_EST_GROUPE))
            .andExpect(jsonPath("$.sourceVersion").value(DEFAULT_SOURCE_VERSION))
            .andExpect(jsonPath("$.societeRef").value(DEFAULT_SOCIETE_REF))
            .andExpect(jsonPath("$.hierRef").value(DEFAULT_HIER_REF));
    }

    @Test
    @Transactional
    void getNonExistingEtablissement() throws Exception {
        // Get the etablissement
        restEtablissementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEtablissement() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etablissement
        Etablissement updatedEtablissement = etablissementRepository.findById(etablissement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEtablissement are not directly saved in db
        em.detach(updatedEtablissement);
        updatedEtablissement
            .nom(UPDATED_NOM)
            .estGroupe(UPDATED_EST_GROUPE)
            .sourceVersion(UPDATED_SOURCE_VERSION)
            .societeRef(UPDATED_SOCIETE_REF)
            .hierRef(UPDATED_HIER_REF);
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(updatedEtablissement);

        restEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etablissementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etablissementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEtablissementToMatchAllProperties(updatedEtablissement);
    }

    @Test
    @Transactional
    void putNonExistingEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(UUID.randomUUID().toString());

        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etablissementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etablissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(UUID.randomUUID().toString());

        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etablissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(UUID.randomUUID().toString());

        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtablissementWithPatch() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etablissement using partial update
        Etablissement partialUpdatedEtablissement = new Etablissement();
        partialUpdatedEtablissement.setId(etablissement.getId());

        restEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtablissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEtablissement))
            )
            .andExpect(status().isOk());

        // Validate the Etablissement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEtablissementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEtablissement, etablissement),
            getPersistedEtablissement(etablissement)
        );
    }

    @Test
    @Transactional
    void fullUpdateEtablissementWithPatch() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etablissement using partial update
        Etablissement partialUpdatedEtablissement = new Etablissement();
        partialUpdatedEtablissement.setId(etablissement.getId());

        partialUpdatedEtablissement
            .nom(UPDATED_NOM)
            .estGroupe(UPDATED_EST_GROUPE)
            .sourceVersion(UPDATED_SOURCE_VERSION)
            .societeRef(UPDATED_SOCIETE_REF)
            .hierRef(UPDATED_HIER_REF);

        restEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtablissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEtablissement))
            )
            .andExpect(status().isOk());

        // Validate the Etablissement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEtablissementUpdatableFieldsEquals(partialUpdatedEtablissement, getPersistedEtablissement(partialUpdatedEtablissement));
    }

    @Test
    @Transactional
    void patchNonExistingEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(UUID.randomUUID().toString());

        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etablissementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(etablissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(UUID.randomUUID().toString());

        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(etablissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(UUID.randomUUID().toString());

        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(etablissementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtablissement() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the etablissement
        restEtablissementMockMvc
            .perform(delete(ENTITY_API_URL_ID, etablissement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return etablissementRepository.count();
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

    protected Etablissement getPersistedEtablissement(Etablissement etablissement) {
        return etablissementRepository.findById(etablissement.getId()).orElseThrow();
    }

    protected void assertPersistedEtablissementToMatchAllProperties(Etablissement expectedEtablissement) {
        assertEtablissementAllPropertiesEquals(expectedEtablissement, getPersistedEtablissement(expectedEtablissement));
    }

    protected void assertPersistedEtablissementToMatchUpdatableProperties(Etablissement expectedEtablissement) {
        assertEtablissementAllUpdatablePropertiesEquals(expectedEtablissement, getPersistedEtablissement(expectedEtablissement));
    }
}
