package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.RemiseAsserts.*;
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
import mc.sbm.simphonycloud.domain.Remise;
import mc.sbm.simphonycloud.repository.RemiseRepository;
import mc.sbm.simphonycloud.service.dto.RemiseDTO;
import mc.sbm.simphonycloud.service.mapper.RemiseMapper;
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
 * Integration tests for the {@link RemiseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RemiseResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MSTR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MSTR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_VALUE = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final Integer DEFAULT_POINT_DE_VENTE_REF = 1;
    private static final Integer UPDATED_POINT_DE_VENTE_REF = 2;

    private static final String ENTITY_API_URL = "/api/remises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RemiseRepository remiseRepository;

    @Autowired
    private RemiseMapper remiseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemiseMockMvc;

    private Remise remise;

    private Remise insertedRemise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remise createEntity() {
        return new Remise()
            .nom(DEFAULT_NOM)
            .nomCourt(DEFAULT_NOM_COURT)
            .nomMstr(DEFAULT_NOM_MSTR)
            .typeValue(DEFAULT_TYPE_VALUE)
            .value(DEFAULT_VALUE)
            .pointDeVenteRef(DEFAULT_POINT_DE_VENTE_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remise createUpdatedEntity() {
        return new Remise()
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);
    }

    @BeforeEach
    void initTest() {
        remise = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedRemise != null) {
            remiseRepository.delete(insertedRemise);
            insertedRemise = null;
        }
    }

    @Test
    @Transactional
    void createRemise() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Remise
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);
        var returnedRemiseDTO = om.readValue(
            restRemiseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RemiseDTO.class
        );

        // Validate the Remise in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRemise = remiseMapper.toEntity(returnedRemiseDTO);
        assertRemiseUpdatableFieldsEquals(returnedRemise, getPersistedRemise(returnedRemise));

        insertedRemise = returnedRemise;
    }

    @Test
    @Transactional
    void createRemiseWithExistingId() throws Exception {
        // Create the Remise with an existing ID
        remise.setId(1);
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPointDeVenteRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        remise.setPointDeVenteRef(null);

        // Create the Remise, which fails.
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);

        restRemiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRemises() throws Exception {
        // Initialize the database
        insertedRemise = remiseRepository.saveAndFlush(remise);

        // Get all the remiseList
        restRemiseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remise.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].nomMstr").value(hasItem(DEFAULT_NOM_MSTR)))
            .andExpect(jsonPath("$.[*].typeValue").value(hasItem(DEFAULT_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].pointDeVenteRef").value(hasItem(DEFAULT_POINT_DE_VENTE_REF)));
    }

    @Test
    @Transactional
    void getRemise() throws Exception {
        // Initialize the database
        insertedRemise = remiseRepository.saveAndFlush(remise);

        // Get the remise
        restRemiseMockMvc
            .perform(get(ENTITY_API_URL_ID, remise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remise.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.nomMstr").value(DEFAULT_NOM_MSTR))
            .andExpect(jsonPath("$.typeValue").value(DEFAULT_TYPE_VALUE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.pointDeVenteRef").value(DEFAULT_POINT_DE_VENTE_REF));
    }

    @Test
    @Transactional
    void getNonExistingRemise() throws Exception {
        // Get the remise
        restRemiseMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRemise() throws Exception {
        // Initialize the database
        insertedRemise = remiseRepository.saveAndFlush(remise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remise
        Remise updatedRemise = remiseRepository.findById(remise.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRemise are not directly saved in db
        em.detach(updatedRemise);
        updatedRemise
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);
        RemiseDTO remiseDTO = remiseMapper.toDto(updatedRemise);

        restRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remiseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Remise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRemiseToMatchAllProperties(updatedRemise);
    }

    @Test
    @Transactional
    void putNonExistingRemise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remise.setId(intCount.incrementAndGet());

        // Create the Remise
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remiseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remise.setId(intCount.incrementAndGet());

        // Create the Remise
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(remiseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remise.setId(intCount.incrementAndGet());

        // Create the Remise
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemiseWithPatch() throws Exception {
        // Initialize the database
        insertedRemise = remiseRepository.saveAndFlush(remise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remise using partial update
        Remise partialUpdatedRemise = new Remise();
        partialUpdatedRemise.setId(remise.getId());

        partialUpdatedRemise.nomMstr(UPDATED_NOM_MSTR);

        restRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRemise))
            )
            .andExpect(status().isOk());

        // Validate the Remise in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRemiseUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRemise, remise), getPersistedRemise(remise));
    }

    @Test
    @Transactional
    void fullUpdateRemiseWithPatch() throws Exception {
        // Initialize the database
        insertedRemise = remiseRepository.saveAndFlush(remise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remise using partial update
        Remise partialUpdatedRemise = new Remise();
        partialUpdatedRemise.setId(remise.getId());

        partialUpdatedRemise
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRemise))
            )
            .andExpect(status().isOk());

        // Validate the Remise in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRemiseUpdatableFieldsEquals(partialUpdatedRemise, getPersistedRemise(partialUpdatedRemise));
    }

    @Test
    @Transactional
    void patchNonExistingRemise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remise.setId(intCount.incrementAndGet());

        // Create the Remise
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remiseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(remiseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remise.setId(intCount.incrementAndGet());

        // Create the Remise
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(remiseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remise.setId(intCount.incrementAndGet());

        // Create the Remise
        RemiseDTO remiseDTO = remiseMapper.toDto(remise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(remiseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemise() throws Exception {
        // Initialize the database
        insertedRemise = remiseRepository.saveAndFlush(remise);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the remise
        restRemiseMockMvc
            .perform(delete(ENTITY_API_URL_ID, remise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return remiseRepository.count();
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

    protected Remise getPersistedRemise(Remise remise) {
        return remiseRepository.findById(remise.getId()).orElseThrow();
    }

    protected void assertPersistedRemiseToMatchAllProperties(Remise expectedRemise) {
        assertRemiseAllPropertiesEquals(expectedRemise, getPersistedRemise(expectedRemise));
    }

    protected void assertPersistedRemiseToMatchUpdatableProperties(Remise expectedRemise) {
        assertRemiseAllUpdatablePropertiesEquals(expectedRemise, getPersistedRemise(expectedRemise));
    }
}
