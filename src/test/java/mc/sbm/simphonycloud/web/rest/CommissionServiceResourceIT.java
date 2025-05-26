package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.CommissionServiceAsserts.*;
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
import mc.sbm.simphonycloud.domain.CommissionService;
import mc.sbm.simphonycloud.repository.CommissionServiceRepository;
import mc.sbm.simphonycloud.service.dto.CommissionServiceDTO;
import mc.sbm.simphonycloud.service.mapper.CommissionServiceMapper;
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
 * Integration tests for the {@link CommissionServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommissionServiceResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_VALUE = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final String DEFAULT_ETABLISSEMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/commission-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommissionServiceRepository commissionServiceRepository;

    @Autowired
    private CommissionServiceMapper commissionServiceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommissionServiceMockMvc;

    private CommissionService commissionService;

    private CommissionService insertedCommissionService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionService createEntity() {
        return new CommissionService()
            .nom(DEFAULT_NOM)
            .nomCourt(DEFAULT_NOM_COURT)
            .typeValue(DEFAULT_TYPE_VALUE)
            .value(DEFAULT_VALUE)
            .etablissementRef(DEFAULT_ETABLISSEMENT_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionService createUpdatedEntity() {
        return new CommissionService()
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
    }

    @BeforeEach
    void initTest() {
        commissionService = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCommissionService != null) {
            commissionServiceRepository.delete(insertedCommissionService);
            insertedCommissionService = null;
        }
    }

    @Test
    @Transactional
    void createCommissionService() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CommissionService
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);
        var returnedCommissionServiceDTO = om.readValue(
            restCommissionServiceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CommissionServiceDTO.class
        );

        // Validate the CommissionService in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCommissionService = commissionServiceMapper.toEntity(returnedCommissionServiceDTO);
        assertCommissionServiceUpdatableFieldsEquals(returnedCommissionService, getPersistedCommissionService(returnedCommissionService));

        insertedCommissionService = returnedCommissionService;
    }

    @Test
    @Transactional
    void createCommissionServiceWithExistingId() throws Exception {
        // Create the CommissionService with an existing ID
        commissionService.setId(1);
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommissionService in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEtablissementRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        commissionService.setEtablissementRef(null);

        // Create the CommissionService, which fails.
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);

        restCommissionServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCommissionServices() throws Exception {
        // Initialize the database
        insertedCommissionService = commissionServiceRepository.saveAndFlush(commissionService);

        // Get all the commissionServiceList
        restCommissionServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commissionService.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].typeValue").value(hasItem(DEFAULT_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)));
    }

    @Test
    @Transactional
    void getCommissionService() throws Exception {
        // Initialize the database
        insertedCommissionService = commissionServiceRepository.saveAndFlush(commissionService);

        // Get the commissionService
        restCommissionServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, commissionService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commissionService.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.typeValue").value(DEFAULT_TYPE_VALUE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF));
    }

    @Test
    @Transactional
    void getNonExistingCommissionService() throws Exception {
        // Get the commissionService
        restCommissionServiceMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommissionService() throws Exception {
        // Initialize the database
        insertedCommissionService = commissionServiceRepository.saveAndFlush(commissionService);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionService
        CommissionService updatedCommissionService = commissionServiceRepository.findById(commissionService.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCommissionService are not directly saved in db
        em.detach(updatedCommissionService);
        updatedCommissionService
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(updatedCommissionService);

        restCommissionServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commissionServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commissionServiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the CommissionService in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCommissionServiceToMatchAllProperties(updatedCommissionService);
    }

    @Test
    @Transactional
    void putNonExistingCommissionService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionService.setId(intCount.incrementAndGet());

        // Create the CommissionService
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commissionServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commissionServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionService in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommissionService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionService.setId(intCount.incrementAndGet());

        // Create the CommissionService
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commissionServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionService in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommissionService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionService.setId(intCount.incrementAndGet());

        // Create the CommissionService
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommissionService in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommissionServiceWithPatch() throws Exception {
        // Initialize the database
        insertedCommissionService = commissionServiceRepository.saveAndFlush(commissionService);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionService using partial update
        CommissionService partialUpdatedCommissionService = new CommissionService();
        partialUpdatedCommissionService.setId(commissionService.getId());

        partialUpdatedCommissionService.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).typeValue(UPDATED_TYPE_VALUE);

        restCommissionServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommissionService.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommissionService))
            )
            .andExpect(status().isOk());

        // Validate the CommissionService in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommissionServiceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCommissionService, commissionService),
            getPersistedCommissionService(commissionService)
        );
    }

    @Test
    @Transactional
    void fullUpdateCommissionServiceWithPatch() throws Exception {
        // Initialize the database
        insertedCommissionService = commissionServiceRepository.saveAndFlush(commissionService);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionService using partial update
        CommissionService partialUpdatedCommissionService = new CommissionService();
        partialUpdatedCommissionService.setId(commissionService.getId());

        partialUpdatedCommissionService
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCommissionServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommissionService.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommissionService))
            )
            .andExpect(status().isOk());

        // Validate the CommissionService in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommissionServiceUpdatableFieldsEquals(
            partialUpdatedCommissionService,
            getPersistedCommissionService(partialUpdatedCommissionService)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCommissionService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionService.setId(intCount.incrementAndGet());

        // Create the CommissionService
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commissionServiceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commissionServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionService in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommissionService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionService.setId(intCount.incrementAndGet());

        // Create the CommissionService
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commissionServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionService in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommissionService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionService.setId(intCount.incrementAndGet());

        // Create the CommissionService
        CommissionServiceDTO commissionServiceDTO = commissionServiceMapper.toDto(commissionService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(commissionServiceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommissionService in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommissionService() throws Exception {
        // Initialize the database
        insertedCommissionService = commissionServiceRepository.saveAndFlush(commissionService);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the commissionService
        restCommissionServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, commissionService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return commissionServiceRepository.count();
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

    protected CommissionService getPersistedCommissionService(CommissionService commissionService) {
        return commissionServiceRepository.findById(commissionService.getId()).orElseThrow();
    }

    protected void assertPersistedCommissionServiceToMatchAllProperties(CommissionService expectedCommissionService) {
        assertCommissionServiceAllPropertiesEquals(expectedCommissionService, getPersistedCommissionService(expectedCommissionService));
    }

    protected void assertPersistedCommissionServiceToMatchUpdatableProperties(CommissionService expectedCommissionService) {
        assertCommissionServiceAllUpdatablePropertiesEquals(
            expectedCommissionService,
            getPersistedCommissionService(expectedCommissionService)
        );
    }
}
