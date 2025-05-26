package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.TaxeAsserts.*;
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
import mc.sbm.simphonycloud.domain.Taxe;
import mc.sbm.simphonycloud.repository.TaxeRepository;
import mc.sbm.simphonycloud.service.dto.TaxeDTO;
import mc.sbm.simphonycloud.service.mapper.TaxeMapper;
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
 * Integration tests for the {@link TaxeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final Integer DEFAULT_VAT_TAX_RATE = 1;
    private static final Integer UPDATED_VAT_TAX_RATE = 2;

    private static final Integer DEFAULT_CLASS_ID = 1;
    private static final Integer UPDATED_CLASS_ID = 2;

    private static final Integer DEFAULT_TAX_TYPE = 1;
    private static final Integer UPDATED_TAX_TYPE = 2;

    private static final String DEFAULT_ETABLISSEMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/taxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaxeRepository taxeRepository;

    @Autowired
    private TaxeMapper taxeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxeMockMvc;

    private Taxe taxe;

    private Taxe insertedTaxe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taxe createEntity() {
        return new Taxe()
            .nom(DEFAULT_NOM)
            .nomCourt(DEFAULT_NOM_COURT)
            .vatTaxRate(DEFAULT_VAT_TAX_RATE)
            .classId(DEFAULT_CLASS_ID)
            .taxType(DEFAULT_TAX_TYPE)
            .etablissementRef(DEFAULT_ETABLISSEMENT_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taxe createUpdatedEntity() {
        return new Taxe()
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .vatTaxRate(UPDATED_VAT_TAX_RATE)
            .classId(UPDATED_CLASS_ID)
            .taxType(UPDATED_TAX_TYPE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
    }

    @BeforeEach
    void initTest() {
        taxe = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTaxe != null) {
            taxeRepository.delete(insertedTaxe);
            insertedTaxe = null;
        }
    }

    @Test
    @Transactional
    void createTaxe() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Taxe
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);
        var returnedTaxeDTO = om.readValue(
            restTaxeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TaxeDTO.class
        );

        // Validate the Taxe in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTaxe = taxeMapper.toEntity(returnedTaxeDTO);
        assertTaxeUpdatableFieldsEquals(returnedTaxe, getPersistedTaxe(returnedTaxe));

        insertedTaxe = returnedTaxe;
    }

    @Test
    @Transactional
    void createTaxeWithExistingId() throws Exception {
        // Create the Taxe with an existing ID
        taxe.setId(1);
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taxe in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEtablissementRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        taxe.setEtablissementRef(null);

        // Create the Taxe, which fails.
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);

        restTaxeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaxes() throws Exception {
        // Initialize the database
        insertedTaxe = taxeRepository.saveAndFlush(taxe);

        // Get all the taxeList
        restTaxeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].vatTaxRate").value(hasItem(DEFAULT_VAT_TAX_RATE)))
            .andExpect(jsonPath("$.[*].classId").value(hasItem(DEFAULT_CLASS_ID)))
            .andExpect(jsonPath("$.[*].taxType").value(hasItem(DEFAULT_TAX_TYPE)))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)));
    }

    @Test
    @Transactional
    void getTaxe() throws Exception {
        // Initialize the database
        insertedTaxe = taxeRepository.saveAndFlush(taxe);

        // Get the taxe
        restTaxeMockMvc
            .perform(get(ENTITY_API_URL_ID, taxe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.vatTaxRate").value(DEFAULT_VAT_TAX_RATE))
            .andExpect(jsonPath("$.classId").value(DEFAULT_CLASS_ID))
            .andExpect(jsonPath("$.taxType").value(DEFAULT_TAX_TYPE))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF));
    }

    @Test
    @Transactional
    void getNonExistingTaxe() throws Exception {
        // Get the taxe
        restTaxeMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaxe() throws Exception {
        // Initialize the database
        insertedTaxe = taxeRepository.saveAndFlush(taxe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxe
        Taxe updatedTaxe = taxeRepository.findById(taxe.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTaxe are not directly saved in db
        em.detach(updatedTaxe);
        updatedTaxe
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .vatTaxRate(UPDATED_VAT_TAX_RATE)
            .classId(UPDATED_CLASS_ID)
            .taxType(UPDATED_TAX_TYPE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
        TaxeDTO taxeDTO = taxeMapper.toDto(updatedTaxe);

        restTaxeMockMvc
            .perform(put(ENTITY_API_URL_ID, taxeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeDTO)))
            .andExpect(status().isOk());

        // Validate the Taxe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaxeToMatchAllProperties(updatedTaxe);
    }

    @Test
    @Transactional
    void putNonExistingTaxe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxe.setId(intCount.incrementAndGet());

        // Create the Taxe
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxeMockMvc
            .perform(put(ENTITY_API_URL_ID, taxeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taxe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxe.setId(intCount.incrementAndGet());

        // Create the Taxe
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taxeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taxe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxe.setId(intCount.incrementAndGet());

        // Create the Taxe
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taxe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxeWithPatch() throws Exception {
        // Initialize the database
        insertedTaxe = taxeRepository.saveAndFlush(taxe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxe using partial update
        Taxe partialUpdatedTaxe = new Taxe();
        partialUpdatedTaxe.setId(taxe.getId());

        partialUpdatedTaxe.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);

        restTaxeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxe.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxe))
            )
            .andExpect(status().isOk());

        // Validate the Taxe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTaxe, taxe), getPersistedTaxe(taxe));
    }

    @Test
    @Transactional
    void fullUpdateTaxeWithPatch() throws Exception {
        // Initialize the database
        insertedTaxe = taxeRepository.saveAndFlush(taxe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxe using partial update
        Taxe partialUpdatedTaxe = new Taxe();
        partialUpdatedTaxe.setId(taxe.getId());

        partialUpdatedTaxe
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .vatTaxRate(UPDATED_VAT_TAX_RATE)
            .classId(UPDATED_CLASS_ID)
            .taxType(UPDATED_TAX_TYPE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restTaxeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxe.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxe))
            )
            .andExpect(status().isOk());

        // Validate the Taxe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxeUpdatableFieldsEquals(partialUpdatedTaxe, getPersistedTaxe(partialUpdatedTaxe));
    }

    @Test
    @Transactional
    void patchNonExistingTaxe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxe.setId(intCount.incrementAndGet());

        // Create the Taxe
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxeDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taxeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taxe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxe.setId(intCount.incrementAndGet());

        // Create the Taxe
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taxeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taxe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxe.setId(intCount.incrementAndGet());

        // Create the Taxe
        TaxeDTO taxeDTO = taxeMapper.toDto(taxe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taxeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taxe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxe() throws Exception {
        // Initialize the database
        insertedTaxe = taxeRepository.saveAndFlush(taxe);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the taxe
        restTaxeMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taxeRepository.count();
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

    protected Taxe getPersistedTaxe(Taxe taxe) {
        return taxeRepository.findById(taxe.getId()).orElseThrow();
    }

    protected void assertPersistedTaxeToMatchAllProperties(Taxe expectedTaxe) {
        assertTaxeAllPropertiesEquals(expectedTaxe, getPersistedTaxe(expectedTaxe));
    }

    protected void assertPersistedTaxeToMatchUpdatableProperties(Taxe expectedTaxe) {
        assertTaxeAllUpdatablePropertiesEquals(expectedTaxe, getPersistedTaxe(expectedTaxe));
    }
}
