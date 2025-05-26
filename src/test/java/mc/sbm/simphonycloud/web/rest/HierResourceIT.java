package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.HierAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.Hier;
import mc.sbm.simphonycloud.repository.HierRepository;
import mc.sbm.simphonycloud.service.dto.HierDTO;
import mc.sbm.simphonycloud.service.mapper.HierMapper;
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
 * Integration tests for the {@link HierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HierResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_HIER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_HIER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HierRepository hierRepository;

    @Autowired
    private HierMapper hierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHierMockMvc;

    private Hier hier;

    private Hier insertedHier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hier createEntity() {
        return new Hier().nom(DEFAULT_NOM).parentHierId(DEFAULT_PARENT_HIER_ID).unitId(DEFAULT_UNIT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hier createUpdatedEntity() {
        return new Hier().nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);
    }

    @BeforeEach
    void initTest() {
        hier = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedHier != null) {
            hierRepository.delete(insertedHier);
            insertedHier = null;
        }
    }

    @Test
    @Transactional
    void createHier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hier
        HierDTO hierDTO = hierMapper.toDto(hier);
        var returnedHierDTO = om.readValue(
            restHierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HierDTO.class
        );

        // Validate the Hier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHier = hierMapper.toEntity(returnedHierDTO);
        assertHierUpdatableFieldsEquals(returnedHier, getPersistedHier(returnedHier));

        insertedHier = returnedHier;
    }

    @Test
    @Transactional
    void createHierWithExistingId() throws Exception {
        // Create the Hier with an existing ID
        hier.setId("existing_id");
        HierDTO hierDTO = hierMapper.toDto(hier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hier.setNom(null);

        // Create the Hier, which fails.
        HierDTO hierDTO = hierMapper.toDto(hier);

        restHierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHiers() throws Exception {
        // Initialize the database
        insertedHier = hierRepository.saveAndFlush(hier);

        // Get all the hierList
        restHierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hier.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].parentHierId").value(hasItem(DEFAULT_PARENT_HIER_ID)))
            .andExpect(jsonPath("$.[*].unitId").value(hasItem(DEFAULT_UNIT_ID)));
    }

    @Test
    @Transactional
    void getHier() throws Exception {
        // Initialize the database
        insertedHier = hierRepository.saveAndFlush(hier);

        // Get the hier
        restHierMockMvc
            .perform(get(ENTITY_API_URL_ID, hier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hier.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.parentHierId").value(DEFAULT_PARENT_HIER_ID))
            .andExpect(jsonPath("$.unitId").value(DEFAULT_UNIT_ID));
    }

    @Test
    @Transactional
    void getNonExistingHier() throws Exception {
        // Get the hier
        restHierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHier() throws Exception {
        // Initialize the database
        insertedHier = hierRepository.saveAndFlush(hier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hier
        Hier updatedHier = hierRepository.findById(hier.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHier are not directly saved in db
        em.detach(updatedHier);
        updatedHier.nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);
        HierDTO hierDTO = hierMapper.toDto(updatedHier);

        restHierMockMvc
            .perform(put(ENTITY_API_URL_ID, hierDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierDTO)))
            .andExpect(status().isOk());

        // Validate the Hier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHierToMatchAllProperties(updatedHier);
    }

    @Test
    @Transactional
    void putNonExistingHier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hier.setId(UUID.randomUUID().toString());

        // Create the Hier
        HierDTO hierDTO = hierMapper.toDto(hier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierMockMvc
            .perform(put(ENTITY_API_URL_ID, hierDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hier.setId(UUID.randomUUID().toString());

        // Create the Hier
        HierDTO hierDTO = hierMapper.toDto(hier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hier.setId(UUID.randomUUID().toString());

        // Create the Hier
        HierDTO hierDTO = hierMapper.toDto(hier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHierWithPatch() throws Exception {
        // Initialize the database
        insertedHier = hierRepository.saveAndFlush(hier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hier using partial update
        Hier partialUpdatedHier = new Hier();
        partialUpdatedHier.setId(hier.getId());

        partialUpdatedHier.nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID);

        restHierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHier))
            )
            .andExpect(status().isOk());

        // Validate the Hier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHierUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHier, hier), getPersistedHier(hier));
    }

    @Test
    @Transactional
    void fullUpdateHierWithPatch() throws Exception {
        // Initialize the database
        insertedHier = hierRepository.saveAndFlush(hier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hier using partial update
        Hier partialUpdatedHier = new Hier();
        partialUpdatedHier.setId(hier.getId());

        partialUpdatedHier.nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);

        restHierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHier))
            )
            .andExpect(status().isOk());

        // Validate the Hier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHierUpdatableFieldsEquals(partialUpdatedHier, getPersistedHier(partialUpdatedHier));
    }

    @Test
    @Transactional
    void patchNonExistingHier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hier.setId(UUID.randomUUID().toString());

        // Create the Hier
        HierDTO hierDTO = hierMapper.toDto(hier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hierDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hier.setId(UUID.randomUUID().toString());

        // Create the Hier
        HierDTO hierDTO = hierMapper.toDto(hier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hier.setId(UUID.randomUUID().toString());

        // Create the Hier
        HierDTO hierDTO = hierMapper.toDto(hier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHier() throws Exception {
        // Initialize the database
        insertedHier = hierRepository.saveAndFlush(hier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hier
        restHierMockMvc
            .perform(delete(ENTITY_API_URL_ID, hier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hierRepository.count();
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

    protected Hier getPersistedHier(Hier hier) {
        return hierRepository.findById(hier.getId()).orElseThrow();
    }

    protected void assertPersistedHierToMatchAllProperties(Hier expectedHier) {
        assertHierAllPropertiesEquals(expectedHier, getPersistedHier(expectedHier));
    }

    protected void assertPersistedHierToMatchUpdatableProperties(Hier expectedHier) {
        assertHierAllUpdatablePropertiesEquals(expectedHier, getPersistedHier(expectedHier));
    }
}
