package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.PartieDeJourneeAsserts.*;
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
import mc.sbm.simphonycloud.domain.PartieDeJournee;
import mc.sbm.simphonycloud.repository.PartieDeJourneeRepository;
import mc.sbm.simphonycloud.service.dto.PartieDeJourneeDTO;
import mc.sbm.simphonycloud.service.mapper.PartieDeJourneeMapper;
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
 * Integration tests for the {@link PartieDeJourneeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartieDeJourneeResourceIT {

    private static final String DEFAULT_TIME_RANGE_START = "AAAAAAAAAA";
    private static final String UPDATED_TIME_RANGE_START = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_RANGE_END = "AAAAAAAAAA";
    private static final String UPDATED_TIME_RANGE_END = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partie-de-journees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PartieDeJourneeRepository partieDeJourneeRepository;

    @Autowired
    private PartieDeJourneeMapper partieDeJourneeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartieDeJourneeMockMvc;

    private PartieDeJournee partieDeJournee;

    private PartieDeJournee insertedPartieDeJournee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartieDeJournee createEntity() {
        return new PartieDeJournee().timeRangeStart(DEFAULT_TIME_RANGE_START).timeRangeEnd(DEFAULT_TIME_RANGE_END).nom(DEFAULT_NOM);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartieDeJournee createUpdatedEntity() {
        return new PartieDeJournee().timeRangeStart(UPDATED_TIME_RANGE_START).timeRangeEnd(UPDATED_TIME_RANGE_END).nom(UPDATED_NOM);
    }

    @BeforeEach
    void initTest() {
        partieDeJournee = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPartieDeJournee != null) {
            partieDeJourneeRepository.delete(insertedPartieDeJournee);
            insertedPartieDeJournee = null;
        }
    }

    @Test
    @Transactional
    void createPartieDeJournee() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PartieDeJournee
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);
        var returnedPartieDeJourneeDTO = om.readValue(
            restPartieDeJourneeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partieDeJourneeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PartieDeJourneeDTO.class
        );

        // Validate the PartieDeJournee in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPartieDeJournee = partieDeJourneeMapper.toEntity(returnedPartieDeJourneeDTO);
        assertPartieDeJourneeUpdatableFieldsEquals(returnedPartieDeJournee, getPersistedPartieDeJournee(returnedPartieDeJournee));

        insertedPartieDeJournee = returnedPartieDeJournee;
    }

    @Test
    @Transactional
    void createPartieDeJourneeWithExistingId() throws Exception {
        // Create the PartieDeJournee with an existing ID
        partieDeJournee.setId(1);
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartieDeJourneeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partieDeJourneeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartieDeJournee in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        partieDeJournee.setNom(null);

        // Create the PartieDeJournee, which fails.
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);

        restPartieDeJourneeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partieDeJourneeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPartieDeJournees() throws Exception {
        // Initialize the database
        insertedPartieDeJournee = partieDeJourneeRepository.saveAndFlush(partieDeJournee);

        // Get all the partieDeJourneeList
        restPartieDeJourneeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partieDeJournee.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeRangeStart").value(hasItem(DEFAULT_TIME_RANGE_START)))
            .andExpect(jsonPath("$.[*].timeRangeEnd").value(hasItem(DEFAULT_TIME_RANGE_END)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    void getPartieDeJournee() throws Exception {
        // Initialize the database
        insertedPartieDeJournee = partieDeJourneeRepository.saveAndFlush(partieDeJournee);

        // Get the partieDeJournee
        restPartieDeJourneeMockMvc
            .perform(get(ENTITY_API_URL_ID, partieDeJournee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partieDeJournee.getId().intValue()))
            .andExpect(jsonPath("$.timeRangeStart").value(DEFAULT_TIME_RANGE_START))
            .andExpect(jsonPath("$.timeRangeEnd").value(DEFAULT_TIME_RANGE_END))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    void getNonExistingPartieDeJournee() throws Exception {
        // Get the partieDeJournee
        restPartieDeJourneeMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartieDeJournee() throws Exception {
        // Initialize the database
        insertedPartieDeJournee = partieDeJourneeRepository.saveAndFlush(partieDeJournee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partieDeJournee
        PartieDeJournee updatedPartieDeJournee = partieDeJourneeRepository.findById(partieDeJournee.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPartieDeJournee are not directly saved in db
        em.detach(updatedPartieDeJournee);
        updatedPartieDeJournee.timeRangeStart(UPDATED_TIME_RANGE_START).timeRangeEnd(UPDATED_TIME_RANGE_END).nom(UPDATED_NOM);
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(updatedPartieDeJournee);

        restPartieDeJourneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partieDeJourneeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partieDeJourneeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PartieDeJournee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPartieDeJourneeToMatchAllProperties(updatedPartieDeJournee);
    }

    @Test
    @Transactional
    void putNonExistingPartieDeJournee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partieDeJournee.setId(intCount.incrementAndGet());

        // Create the PartieDeJournee
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartieDeJourneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partieDeJourneeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partieDeJourneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartieDeJournee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartieDeJournee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partieDeJournee.setId(intCount.incrementAndGet());

        // Create the PartieDeJournee
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartieDeJourneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partieDeJourneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartieDeJournee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartieDeJournee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partieDeJournee.setId(intCount.incrementAndGet());

        // Create the PartieDeJournee
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartieDeJourneeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partieDeJourneeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartieDeJournee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartieDeJourneeWithPatch() throws Exception {
        // Initialize the database
        insertedPartieDeJournee = partieDeJourneeRepository.saveAndFlush(partieDeJournee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partieDeJournee using partial update
        PartieDeJournee partialUpdatedPartieDeJournee = new PartieDeJournee();
        partialUpdatedPartieDeJournee.setId(partieDeJournee.getId());

        partialUpdatedPartieDeJournee.timeRangeStart(UPDATED_TIME_RANGE_START).timeRangeEnd(UPDATED_TIME_RANGE_END).nom(UPDATED_NOM);

        restPartieDeJourneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartieDeJournee.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartieDeJournee))
            )
            .andExpect(status().isOk());

        // Validate the PartieDeJournee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartieDeJourneeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPartieDeJournee, partieDeJournee),
            getPersistedPartieDeJournee(partieDeJournee)
        );
    }

    @Test
    @Transactional
    void fullUpdatePartieDeJourneeWithPatch() throws Exception {
        // Initialize the database
        insertedPartieDeJournee = partieDeJourneeRepository.saveAndFlush(partieDeJournee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partieDeJournee using partial update
        PartieDeJournee partialUpdatedPartieDeJournee = new PartieDeJournee();
        partialUpdatedPartieDeJournee.setId(partieDeJournee.getId());

        partialUpdatedPartieDeJournee.timeRangeStart(UPDATED_TIME_RANGE_START).timeRangeEnd(UPDATED_TIME_RANGE_END).nom(UPDATED_NOM);

        restPartieDeJourneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartieDeJournee.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartieDeJournee))
            )
            .andExpect(status().isOk());

        // Validate the PartieDeJournee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartieDeJourneeUpdatableFieldsEquals(
            partialUpdatedPartieDeJournee,
            getPersistedPartieDeJournee(partialUpdatedPartieDeJournee)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPartieDeJournee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partieDeJournee.setId(intCount.incrementAndGet());

        // Create the PartieDeJournee
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartieDeJourneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partieDeJourneeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partieDeJourneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartieDeJournee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartieDeJournee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partieDeJournee.setId(intCount.incrementAndGet());

        // Create the PartieDeJournee
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartieDeJourneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partieDeJourneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartieDeJournee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartieDeJournee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partieDeJournee.setId(intCount.incrementAndGet());

        // Create the PartieDeJournee
        PartieDeJourneeDTO partieDeJourneeDTO = partieDeJourneeMapper.toDto(partieDeJournee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartieDeJourneeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(partieDeJourneeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartieDeJournee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartieDeJournee() throws Exception {
        // Initialize the database
        insertedPartieDeJournee = partieDeJourneeRepository.saveAndFlush(partieDeJournee);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the partieDeJournee
        restPartieDeJourneeMockMvc
            .perform(delete(ENTITY_API_URL_ID, partieDeJournee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return partieDeJourneeRepository.count();
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

    protected PartieDeJournee getPersistedPartieDeJournee(PartieDeJournee partieDeJournee) {
        return partieDeJourneeRepository.findById(partieDeJournee.getId()).orElseThrow();
    }

    protected void assertPersistedPartieDeJourneeToMatchAllProperties(PartieDeJournee expectedPartieDeJournee) {
        assertPartieDeJourneeAllPropertiesEquals(expectedPartieDeJournee, getPersistedPartieDeJournee(expectedPartieDeJournee));
    }

    protected void assertPersistedPartieDeJourneeToMatchUpdatableProperties(PartieDeJournee expectedPartieDeJournee) {
        assertPartieDeJourneeAllUpdatablePropertiesEquals(expectedPartieDeJournee, getPersistedPartieDeJournee(expectedPartieDeJournee));
    }
}
