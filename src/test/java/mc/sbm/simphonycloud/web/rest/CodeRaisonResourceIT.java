package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.CodeRaisonAsserts.*;
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
import mc.sbm.simphonycloud.domain.CodeRaison;
import mc.sbm.simphonycloud.repository.CodeRaisonRepository;
import mc.sbm.simphonycloud.service.dto.CodeRaisonDTO;
import mc.sbm.simphonycloud.service.mapper.CodeRaisonMapper;
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
 * Integration tests for the {@link CodeRaisonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodeRaisonResourceIT {

    private static final Integer DEFAULT_NOM_COURT = 1;
    private static final Integer UPDATED_NOM_COURT = 2;

    private static final String DEFAULT_NOM_MSTR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MSTR = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_MSTR = 1;
    private static final Integer UPDATED_NUM_MSTR = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ETABLISSEMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/code-raisons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CodeRaisonRepository codeRaisonRepository;

    @Autowired
    private CodeRaisonMapper codeRaisonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodeRaisonMockMvc;

    private CodeRaison codeRaison;

    private CodeRaison insertedCodeRaison;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeRaison createEntity() {
        return new CodeRaison()
            .nomCourt(DEFAULT_NOM_COURT)
            .nomMstr(DEFAULT_NOM_MSTR)
            .numMstr(DEFAULT_NUM_MSTR)
            .name(DEFAULT_NAME)
            .etablissementRef(DEFAULT_ETABLISSEMENT_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeRaison createUpdatedEntity() {
        return new CodeRaison()
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .numMstr(UPDATED_NUM_MSTR)
            .name(UPDATED_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
    }

    @BeforeEach
    void initTest() {
        codeRaison = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCodeRaison != null) {
            codeRaisonRepository.delete(insertedCodeRaison);
            insertedCodeRaison = null;
        }
    }

    @Test
    @Transactional
    void createCodeRaison() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CodeRaison
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);
        var returnedCodeRaisonDTO = om.readValue(
            restCodeRaisonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CodeRaisonDTO.class
        );

        // Validate the CodeRaison in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCodeRaison = codeRaisonMapper.toEntity(returnedCodeRaisonDTO);
        assertCodeRaisonUpdatableFieldsEquals(returnedCodeRaison, getPersistedCodeRaison(returnedCodeRaison));

        insertedCodeRaison = returnedCodeRaison;
    }

    @Test
    @Transactional
    void createCodeRaisonWithExistingId() throws Exception {
        // Create the CodeRaison with an existing ID
        codeRaison.setId(1);
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeRaisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodeRaison in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomCourtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        codeRaison.setNomCourt(null);

        // Create the CodeRaison, which fails.
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        restCodeRaisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEtablissementRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        codeRaison.setEtablissementRef(null);

        // Create the CodeRaison, which fails.
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        restCodeRaisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCodeRaisons() throws Exception {
        // Initialize the database
        insertedCodeRaison = codeRaisonRepository.saveAndFlush(codeRaison);

        // Get all the codeRaisonList
        restCodeRaisonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codeRaison.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].nomMstr").value(hasItem(DEFAULT_NOM_MSTR)))
            .andExpect(jsonPath("$.[*].numMstr").value(hasItem(DEFAULT_NUM_MSTR)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)));
    }

    @Test
    @Transactional
    void getCodeRaison() throws Exception {
        // Initialize the database
        insertedCodeRaison = codeRaisonRepository.saveAndFlush(codeRaison);

        // Get the codeRaison
        restCodeRaisonMockMvc
            .perform(get(ENTITY_API_URL_ID, codeRaison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codeRaison.getId().intValue()))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.nomMstr").value(DEFAULT_NOM_MSTR))
            .andExpect(jsonPath("$.numMstr").value(DEFAULT_NUM_MSTR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF));
    }

    @Test
    @Transactional
    void getNonExistingCodeRaison() throws Exception {
        // Get the codeRaison
        restCodeRaisonMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCodeRaison() throws Exception {
        // Initialize the database
        insertedCodeRaison = codeRaisonRepository.saveAndFlush(codeRaison);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codeRaison
        CodeRaison updatedCodeRaison = codeRaisonRepository.findById(codeRaison.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCodeRaison are not directly saved in db
        em.detach(updatedCodeRaison);
        updatedCodeRaison
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .numMstr(UPDATED_NUM_MSTR)
            .name(UPDATED_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(updatedCodeRaison);

        restCodeRaisonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codeRaisonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(codeRaisonDTO))
            )
            .andExpect(status().isOk());

        // Validate the CodeRaison in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCodeRaisonToMatchAllProperties(updatedCodeRaison);
    }

    @Test
    @Transactional
    void putNonExistingCodeRaison() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaison.setId(intCount.incrementAndGet());

        // Create the CodeRaison
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeRaisonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codeRaisonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(codeRaisonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeRaison in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCodeRaison() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaison.setId(intCount.incrementAndGet());

        // Create the CodeRaison
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeRaisonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(codeRaisonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeRaison in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCodeRaison() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaison.setId(intCount.incrementAndGet());

        // Create the CodeRaison
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeRaisonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodeRaison in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCodeRaisonWithPatch() throws Exception {
        // Initialize the database
        insertedCodeRaison = codeRaisonRepository.saveAndFlush(codeRaison);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codeRaison using partial update
        CodeRaison partialUpdatedCodeRaison = new CodeRaison();
        partialUpdatedCodeRaison.setId(codeRaison.getId());

        partialUpdatedCodeRaison
            .nomCourt(UPDATED_NOM_COURT)
            .numMstr(UPDATED_NUM_MSTR)
            .name(UPDATED_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCodeRaisonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodeRaison.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCodeRaison))
            )
            .andExpect(status().isOk());

        // Validate the CodeRaison in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCodeRaisonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCodeRaison, codeRaison),
            getPersistedCodeRaison(codeRaison)
        );
    }

    @Test
    @Transactional
    void fullUpdateCodeRaisonWithPatch() throws Exception {
        // Initialize the database
        insertedCodeRaison = codeRaisonRepository.saveAndFlush(codeRaison);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codeRaison using partial update
        CodeRaison partialUpdatedCodeRaison = new CodeRaison();
        partialUpdatedCodeRaison.setId(codeRaison.getId());

        partialUpdatedCodeRaison
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .numMstr(UPDATED_NUM_MSTR)
            .name(UPDATED_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCodeRaisonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodeRaison.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCodeRaison))
            )
            .andExpect(status().isOk());

        // Validate the CodeRaison in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCodeRaisonUpdatableFieldsEquals(partialUpdatedCodeRaison, getPersistedCodeRaison(partialUpdatedCodeRaison));
    }

    @Test
    @Transactional
    void patchNonExistingCodeRaison() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaison.setId(intCount.incrementAndGet());

        // Create the CodeRaison
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeRaisonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, codeRaisonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(codeRaisonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeRaison in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCodeRaison() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaison.setId(intCount.incrementAndGet());

        // Create the CodeRaison
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeRaisonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(codeRaisonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeRaison in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCodeRaison() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaison.setId(intCount.incrementAndGet());

        // Create the CodeRaison
        CodeRaisonDTO codeRaisonDTO = codeRaisonMapper.toDto(codeRaison);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeRaisonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(codeRaisonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodeRaison in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCodeRaison() throws Exception {
        // Initialize the database
        insertedCodeRaison = codeRaisonRepository.saveAndFlush(codeRaison);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the codeRaison
        restCodeRaisonMockMvc
            .perform(delete(ENTITY_API_URL_ID, codeRaison.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return codeRaisonRepository.count();
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

    protected CodeRaison getPersistedCodeRaison(CodeRaison codeRaison) {
        return codeRaisonRepository.findById(codeRaison.getId()).orElseThrow();
    }

    protected void assertPersistedCodeRaisonToMatchAllProperties(CodeRaison expectedCodeRaison) {
        assertCodeRaisonAllPropertiesEquals(expectedCodeRaison, getPersistedCodeRaison(expectedCodeRaison));
    }

    protected void assertPersistedCodeRaisonToMatchUpdatableProperties(CodeRaison expectedCodeRaison) {
        assertCodeRaisonAllUpdatablePropertiesEquals(expectedCodeRaison, getPersistedCodeRaison(expectedCodeRaison));
    }
}
