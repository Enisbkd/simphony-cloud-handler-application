package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.ModePaiementAsserts.*;
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
import mc.sbm.simphonycloud.domain.ModePaiement;
import mc.sbm.simphonycloud.repository.ModePaiementRepository;
import mc.sbm.simphonycloud.service.dto.ModePaiementDTO;
import mc.sbm.simphonycloud.service.mapper.ModePaiementMapper;
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
 * Integration tests for the {@link ModePaiementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModePaiementResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MSTR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MSTR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ETABLISSEMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mode-paiements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ModePaiementRepository modePaiementRepository;

    @Autowired
    private ModePaiementMapper modePaiementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModePaiementMockMvc;

    private ModePaiement modePaiement;

    private ModePaiement insertedModePaiement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePaiement createEntity() {
        return new ModePaiement()
            .nom(DEFAULT_NOM)
            .nomCourt(DEFAULT_NOM_COURT)
            .nomMstr(DEFAULT_NOM_MSTR)
            .type(DEFAULT_TYPE)
            .etablissementRef(DEFAULT_ETABLISSEMENT_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePaiement createUpdatedEntity() {
        return new ModePaiement()
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .type(UPDATED_TYPE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
    }

    @BeforeEach
    void initTest() {
        modePaiement = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedModePaiement != null) {
            modePaiementRepository.delete(insertedModePaiement);
            insertedModePaiement = null;
        }
    }

    @Test
    @Transactional
    void createModePaiement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ModePaiement
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);
        var returnedModePaiementDTO = om.readValue(
            restModePaiementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ModePaiementDTO.class
        );

        // Validate the ModePaiement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedModePaiement = modePaiementMapper.toEntity(returnedModePaiementDTO);
        assertModePaiementUpdatableFieldsEquals(returnedModePaiement, getPersistedModePaiement(returnedModePaiement));

        insertedModePaiement = returnedModePaiement;
    }

    @Test
    @Transactional
    void createModePaiementWithExistingId() throws Exception {
        // Create the ModePaiement with an existing ID
        modePaiement.setId(1);
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModePaiementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEtablissementRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        modePaiement.setEtablissementRef(null);

        // Create the ModePaiement, which fails.
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);

        restModePaiementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllModePaiements() throws Exception {
        // Initialize the database
        insertedModePaiement = modePaiementRepository.saveAndFlush(modePaiement);

        // Get all the modePaiementList
        restModePaiementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modePaiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].nomMstr").value(hasItem(DEFAULT_NOM_MSTR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)));
    }

    @Test
    @Transactional
    void getModePaiement() throws Exception {
        // Initialize the database
        insertedModePaiement = modePaiementRepository.saveAndFlush(modePaiement);

        // Get the modePaiement
        restModePaiementMockMvc
            .perform(get(ENTITY_API_URL_ID, modePaiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modePaiement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.nomMstr").value(DEFAULT_NOM_MSTR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF));
    }

    @Test
    @Transactional
    void getNonExistingModePaiement() throws Exception {
        // Get the modePaiement
        restModePaiementMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModePaiement() throws Exception {
        // Initialize the database
        insertedModePaiement = modePaiementRepository.saveAndFlush(modePaiement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiement
        ModePaiement updatedModePaiement = modePaiementRepository.findById(modePaiement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedModePaiement are not directly saved in db
        em.detach(updatedModePaiement);
        updatedModePaiement
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .type(UPDATED_TYPE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(updatedModePaiement);

        restModePaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modePaiementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(modePaiementDTO))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedModePaiementToMatchAllProperties(updatedModePaiement);
    }

    @Test
    @Transactional
    void putNonExistingModePaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiement.setId(intCount.incrementAndGet());

        // Create the ModePaiement
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modePaiementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(modePaiementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModePaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiement.setId(intCount.incrementAndGet());

        // Create the ModePaiement
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(modePaiementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModePaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiement.setId(intCount.incrementAndGet());

        // Create the ModePaiement
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModePaiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModePaiementWithPatch() throws Exception {
        // Initialize the database
        insertedModePaiement = modePaiementRepository.saveAndFlush(modePaiement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiement using partial update
        ModePaiement partialUpdatedModePaiement = new ModePaiement();
        partialUpdatedModePaiement.setId(modePaiement.getId());

        partialUpdatedModePaiement
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .type(UPDATED_TYPE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModePaiement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModePaiement))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModePaiementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedModePaiement, modePaiement),
            getPersistedModePaiement(modePaiement)
        );
    }

    @Test
    @Transactional
    void fullUpdateModePaiementWithPatch() throws Exception {
        // Initialize the database
        insertedModePaiement = modePaiementRepository.saveAndFlush(modePaiement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiement using partial update
        ModePaiement partialUpdatedModePaiement = new ModePaiement();
        partialUpdatedModePaiement.setId(modePaiement.getId());

        partialUpdatedModePaiement
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .type(UPDATED_TYPE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModePaiement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModePaiement))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModePaiementUpdatableFieldsEquals(partialUpdatedModePaiement, getPersistedModePaiement(partialUpdatedModePaiement));
    }

    @Test
    @Transactional
    void patchNonExistingModePaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiement.setId(intCount.incrementAndGet());

        // Create the ModePaiement
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modePaiementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(modePaiementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModePaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiement.setId(intCount.incrementAndGet());

        // Create the ModePaiement
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(modePaiementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModePaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiement.setId(intCount.incrementAndGet());

        // Create the ModePaiement
        ModePaiementDTO modePaiementDTO = modePaiementMapper.toDto(modePaiement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(modePaiementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModePaiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModePaiement() throws Exception {
        // Initialize the database
        insertedModePaiement = modePaiementRepository.saveAndFlush(modePaiement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the modePaiement
        restModePaiementMockMvc
            .perform(delete(ENTITY_API_URL_ID, modePaiement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return modePaiementRepository.count();
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

    protected ModePaiement getPersistedModePaiement(ModePaiement modePaiement) {
        return modePaiementRepository.findById(modePaiement.getId()).orElseThrow();
    }

    protected void assertPersistedModePaiementToMatchAllProperties(ModePaiement expectedModePaiement) {
        assertModePaiementAllPropertiesEquals(expectedModePaiement, getPersistedModePaiement(expectedModePaiement));
    }

    protected void assertPersistedModePaiementToMatchUpdatableProperties(ModePaiement expectedModePaiement) {
        assertModePaiementAllUpdatablePropertiesEquals(expectedModePaiement, getPersistedModePaiement(expectedModePaiement));
    }
}
