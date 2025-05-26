package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.EmployeAsserts.*;
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
import mc.sbm.simphonycloud.domain.Employe;
import mc.sbm.simphonycloud.repository.EmployeRepository;
import mc.sbm.simphonycloud.service.dto.EmployeDTO;
import mc.sbm.simphonycloud.service.mapper.EmployeMapper;
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
 * Integration tests for the {@link EmployeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeResourceIT {

    private static final Integer DEFAULT_NUM = 1;
    private static final Integer UPDATED_NUM = 2;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ETABLISSEMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeMapper employeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeMockMvc;

    private Employe employe;

    private Employe insertedEmploye;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createEntity() {
        return new Employe()
            .num(DEFAULT_NUM)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .userName(DEFAULT_USER_NAME)
            .etablissementRef(DEFAULT_ETABLISSEMENT_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createUpdatedEntity() {
        return new Employe()
            .num(UPDATED_NUM)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userName(UPDATED_USER_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
    }

    @BeforeEach
    void initTest() {
        employe = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEmploye != null) {
            employeRepository.delete(insertedEmploye);
            insertedEmploye = null;
        }
    }

    @Test
    @Transactional
    void createEmploye() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);
        var returnedEmployeDTO = om.readValue(
            restEmployeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmployeDTO.class
        );

        // Validate the Employe in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEmploye = employeMapper.toEntity(returnedEmployeDTO);
        assertEmployeUpdatableFieldsEquals(returnedEmploye, getPersistedEmploye(returnedEmploye));

        insertedEmploye = returnedEmploye;
    }

    @Test
    @Transactional
    void createEmployeWithExistingId() throws Exception {
        // Create the Employe with an existing ID
        employe.setId(1);
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        employe.setNum(null);

        // Create the Employe, which fails.
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEtablissementRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        employe.setEtablissementRef(null);

        // Create the Employe, which fails.
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployes() throws Exception {
        // Initialize the database
        insertedEmploye = employeRepository.saveAndFlush(employe);

        // Get all the employeList
        restEmployeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employe.getId().intValue())))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)));
    }

    @Test
    @Transactional
    void getEmploye() throws Exception {
        // Initialize the database
        insertedEmploye = employeRepository.saveAndFlush(employe);

        // Get the employe
        restEmployeMockMvc
            .perform(get(ENTITY_API_URL_ID, employe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employe.getId().intValue()))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF));
    }

    @Test
    @Transactional
    void getNonExistingEmploye() throws Exception {
        // Get the employe
        restEmployeMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmploye() throws Exception {
        // Initialize the database
        insertedEmploye = employeRepository.saveAndFlush(employe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employe
        Employe updatedEmploye = employeRepository.findById(employe.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmploye are not directly saved in db
        em.detach(updatedEmploye);
        updatedEmploye
            .num(UPDATED_NUM)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userName(UPDATED_USER_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
        EmployeDTO employeDTO = employeMapper.toDto(updatedEmploye);

        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmployeToMatchAllProperties(updatedEmploye);
    }

    @Test
    @Transactional
    void putNonExistingEmploye() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employe.setId(intCount.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploye() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employe.setId(intCount.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploye() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employe.setId(intCount.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeWithPatch() throws Exception {
        // Initialize the database
        insertedEmploye = employeRepository.saveAndFlush(employe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employe using partial update
        Employe partialUpdatedEmploye = new Employe();
        partialUpdatedEmploye.setId(employe.getId());

        partialUpdatedEmploye.num(UPDATED_NUM).etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploye.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmployeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEmploye, employe), getPersistedEmploye(employe));
    }

    @Test
    @Transactional
    void fullUpdateEmployeWithPatch() throws Exception {
        // Initialize the database
        insertedEmploye = employeRepository.saveAndFlush(employe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employe using partial update
        Employe partialUpdatedEmploye = new Employe();
        partialUpdatedEmploye.setId(employe.getId());

        partialUpdatedEmploye
            .num(UPDATED_NUM)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userName(UPDATED_USER_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploye.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmployeUpdatableFieldsEquals(partialUpdatedEmploye, getPersistedEmploye(partialUpdatedEmploye));
    }

    @Test
    @Transactional
    void patchNonExistingEmploye() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employe.setId(intCount.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploye() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employe.setId(intCount.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploye() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employe.setId(intCount.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(employeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploye() throws Exception {
        // Initialize the database
        insertedEmploye = employeRepository.saveAndFlush(employe);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the employe
        restEmployeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return employeRepository.count();
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

    protected Employe getPersistedEmploye(Employe employe) {
        return employeRepository.findById(employe.getId()).orElseThrow();
    }

    protected void assertPersistedEmployeToMatchAllProperties(Employe expectedEmploye) {
        assertEmployeAllPropertiesEquals(expectedEmploye, getPersistedEmploye(expectedEmploye));
    }

    protected void assertPersistedEmployeToMatchUpdatableProperties(Employe expectedEmploye) {
        assertEmployeAllUpdatablePropertiesEquals(expectedEmploye, getPersistedEmploye(expectedEmploye));
    }
}
