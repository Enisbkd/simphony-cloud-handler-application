package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.SocieteAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.Societe;
import mc.sbm.simphonycloud.repository.SocieteRepository;
import mc.sbm.simphonycloud.service.dto.SocieteDTO;
import mc.sbm.simphonycloud.service.mapper.SocieteMapper;
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
 * Integration tests for the {@link SocieteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocieteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/societes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SocieteRepository societeRepository;

    @Autowired
    private SocieteMapper societeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocieteMockMvc;

    private Societe societe;

    private Societe insertedSociete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createEntity() {
        return new Societe().nom(DEFAULT_NOM).nomCourt(DEFAULT_NOM_COURT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createUpdatedEntity() {
        return new Societe().nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);
    }

    @BeforeEach
    void initTest() {
        societe = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSociete != null) {
            societeRepository.delete(insertedSociete);
            insertedSociete = null;
        }
    }

    @Test
    @Transactional
    void createSociete() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Societe
        SocieteDTO societeDTO = societeMapper.toDto(societe);
        var returnedSocieteDTO = om.readValue(
            restSocieteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SocieteDTO.class
        );

        // Validate the Societe in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSociete = societeMapper.toEntity(returnedSocieteDTO);
        assertSocieteUpdatableFieldsEquals(returnedSociete, getPersistedSociete(returnedSociete));

        insertedSociete = returnedSociete;
    }

    @Test
    @Transactional
    void createSocieteWithExistingId() throws Exception {
        // Create the Societe with an existing ID
        societe.setId("existing_id");
        SocieteDTO societeDTO = societeMapper.toDto(societe);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocietes() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        // Get all the societeList
        restSocieteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societe.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)));
    }

    @Test
    @Transactional
    void getSociete() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        // Get the societe
        restSocieteMockMvc
            .perform(get(ENTITY_API_URL_ID, societe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societe.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT));
    }

    @Test
    @Transactional
    void getNonExistingSociete() throws Exception {
        // Get the societe
        restSocieteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSociete() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societe
        Societe updatedSociete = societeRepository.findById(societe.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSociete are not directly saved in db
        em.detach(updatedSociete);
        updatedSociete.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);
        SocieteDTO societeDTO = societeMapper.toDto(updatedSociete);

        restSocieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSocieteToMatchAllProperties(updatedSociete);
    }

    @Test
    @Transactional
    void putNonExistingSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(UUID.randomUUID().toString());

        // Create the Societe
        SocieteDTO societeDTO = societeMapper.toDto(societe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(UUID.randomUUID().toString());

        // Create the Societe
        SocieteDTO societeDTO = societeMapper.toDto(societe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(societeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(UUID.randomUUID().toString());

        // Create the Societe
        SocieteDTO societeDTO = societeMapper.toDto(societe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocieteWithPatch() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societe using partial update
        Societe partialUpdatedSociete = new Societe();
        partialUpdatedSociete.setId(societe.getId());

        partialUpdatedSociete.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);

        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSociete))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocieteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSociete, societe), getPersistedSociete(societe));
    }

    @Test
    @Transactional
    void fullUpdateSocieteWithPatch() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societe using partial update
        Societe partialUpdatedSociete = new Societe();
        partialUpdatedSociete.setId(societe.getId());

        partialUpdatedSociete.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);

        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSociete))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocieteUpdatableFieldsEquals(partialUpdatedSociete, getPersistedSociete(partialUpdatedSociete));
    }

    @Test
    @Transactional
    void patchNonExistingSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(UUID.randomUUID().toString());

        // Create the Societe
        SocieteDTO societeDTO = societeMapper.toDto(societe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(societeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(UUID.randomUUID().toString());

        // Create the Societe
        SocieteDTO societeDTO = societeMapper.toDto(societe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(societeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(UUID.randomUUID().toString());

        // Create the Societe
        SocieteDTO societeDTO = societeMapper.toDto(societe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(societeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSociete() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the societe
        restSocieteMockMvc
            .perform(delete(ENTITY_API_URL_ID, societe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return societeRepository.count();
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

    protected Societe getPersistedSociete(Societe societe) {
        return societeRepository.findById(societe.getId()).orElseThrow();
    }

    protected void assertPersistedSocieteToMatchAllProperties(Societe expectedSociete) {
        assertSocieteAllPropertiesEquals(expectedSociete, getPersistedSociete(expectedSociete));
    }

    protected void assertPersistedSocieteToMatchUpdatableProperties(Societe expectedSociete) {
        assertSocieteAllUpdatablePropertiesEquals(expectedSociete, getPersistedSociete(expectedSociete));
    }
}
