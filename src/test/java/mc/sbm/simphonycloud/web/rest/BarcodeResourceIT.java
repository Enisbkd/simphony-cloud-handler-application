package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.BarcodeAsserts.*;
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
import mc.sbm.simphonycloud.domain.Barcode;
import mc.sbm.simphonycloud.repository.BarcodeRepository;
import mc.sbm.simphonycloud.service.dto.BarcodeDTO;
import mc.sbm.simphonycloud.service.mapper.BarcodeMapper;
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
 * Integration tests for the {@link BarcodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BarcodeResourceIT {

    private static final Integer DEFAULT_NUM = 1;
    private static final Integer UPDATED_NUM = 2;

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIX = 1;
    private static final Integer UPDATED_PRIX = 2;

    private static final Integer DEFAULT_COUT_PREPARATION = 1;
    private static final Integer UPDATED_COUT_PREPARATION = 2;

    private static final Integer DEFAULT_DEF_NUM_SEQUENCE = 1;
    private static final Integer UPDATED_DEF_NUM_SEQUENCE = 2;

    private static final Integer DEFAULT_PRIX_NUM_SEQUENCE = 1;
    private static final Integer UPDATED_PRIX_NUM_SEQUENCE = 2;

    private static final Integer DEFAULT_POINT_DE_VENTE_REF = 1;
    private static final Integer UPDATED_POINT_DE_VENTE_REF = 2;

    private static final Integer DEFAULT_ELEMENT_MENU_REF = 1;
    private static final Integer UPDATED_ELEMENT_MENU_REF = 2;

    private static final String ENTITY_API_URL = "/api/barcodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BarcodeRepository barcodeRepository;

    @Autowired
    private BarcodeMapper barcodeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBarcodeMockMvc;

    private Barcode barcode;

    private Barcode insertedBarcode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Barcode createEntity() {
        return new Barcode()
            .num(DEFAULT_NUM)
            .barcode(DEFAULT_BARCODE)
            .prix(DEFAULT_PRIX)
            .coutPreparation(DEFAULT_COUT_PREPARATION)
            .defNumSequence(DEFAULT_DEF_NUM_SEQUENCE)
            .prixNumSequence(DEFAULT_PRIX_NUM_SEQUENCE)
            .pointDeVenteRef(DEFAULT_POINT_DE_VENTE_REF)
            .elementMenuRef(DEFAULT_ELEMENT_MENU_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Barcode createUpdatedEntity() {
        return new Barcode()
            .num(UPDATED_NUM)
            .barcode(UPDATED_BARCODE)
            .prix(UPDATED_PRIX)
            .coutPreparation(UPDATED_COUT_PREPARATION)
            .defNumSequence(UPDATED_DEF_NUM_SEQUENCE)
            .prixNumSequence(UPDATED_PRIX_NUM_SEQUENCE)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF)
            .elementMenuRef(UPDATED_ELEMENT_MENU_REF);
    }

    @BeforeEach
    void initTest() {
        barcode = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBarcode != null) {
            barcodeRepository.delete(insertedBarcode);
            insertedBarcode = null;
        }
    }

    @Test
    @Transactional
    void createBarcode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);
        var returnedBarcodeDTO = om.readValue(
            restBarcodeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barcodeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BarcodeDTO.class
        );

        // Validate the Barcode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBarcode = barcodeMapper.toEntity(returnedBarcodeDTO);
        assertBarcodeUpdatableFieldsEquals(returnedBarcode, getPersistedBarcode(returnedBarcode));

        insertedBarcode = returnedBarcode;
    }

    @Test
    @Transactional
    void createBarcodeWithExistingId() throws Exception {
        // Create the Barcode with an existing ID
        barcode.setId(1);
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barcodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPointDeVenteRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        barcode.setPointDeVenteRef(null);

        // Create the Barcode, which fails.
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        restBarcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barcodeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBarcodes() throws Exception {
        // Initialize the database
        insertedBarcode = barcodeRepository.saveAndFlush(barcode);

        // Get all the barcodeList
        restBarcodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcode.getId().intValue())))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)))
            .andExpect(jsonPath("$.[*].coutPreparation").value(hasItem(DEFAULT_COUT_PREPARATION)))
            .andExpect(jsonPath("$.[*].defNumSequence").value(hasItem(DEFAULT_DEF_NUM_SEQUENCE)))
            .andExpect(jsonPath("$.[*].prixNumSequence").value(hasItem(DEFAULT_PRIX_NUM_SEQUENCE)))
            .andExpect(jsonPath("$.[*].pointDeVenteRef").value(hasItem(DEFAULT_POINT_DE_VENTE_REF)))
            .andExpect(jsonPath("$.[*].elementMenuRef").value(hasItem(DEFAULT_ELEMENT_MENU_REF)));
    }

    @Test
    @Transactional
    void getBarcode() throws Exception {
        // Initialize the database
        insertedBarcode = barcodeRepository.saveAndFlush(barcode);

        // Get the barcode
        restBarcodeMockMvc
            .perform(get(ENTITY_API_URL_ID, barcode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(barcode.getId().intValue()))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX))
            .andExpect(jsonPath("$.coutPreparation").value(DEFAULT_COUT_PREPARATION))
            .andExpect(jsonPath("$.defNumSequence").value(DEFAULT_DEF_NUM_SEQUENCE))
            .andExpect(jsonPath("$.prixNumSequence").value(DEFAULT_PRIX_NUM_SEQUENCE))
            .andExpect(jsonPath("$.pointDeVenteRef").value(DEFAULT_POINT_DE_VENTE_REF))
            .andExpect(jsonPath("$.elementMenuRef").value(DEFAULT_ELEMENT_MENU_REF));
    }

    @Test
    @Transactional
    void getNonExistingBarcode() throws Exception {
        // Get the barcode
        restBarcodeMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBarcode() throws Exception {
        // Initialize the database
        insertedBarcode = barcodeRepository.saveAndFlush(barcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the barcode
        Barcode updatedBarcode = barcodeRepository.findById(barcode.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBarcode are not directly saved in db
        em.detach(updatedBarcode);
        updatedBarcode
            .num(UPDATED_NUM)
            .barcode(UPDATED_BARCODE)
            .prix(UPDATED_PRIX)
            .coutPreparation(UPDATED_COUT_PREPARATION)
            .defNumSequence(UPDATED_DEF_NUM_SEQUENCE)
            .prixNumSequence(UPDATED_PRIX_NUM_SEQUENCE)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF)
            .elementMenuRef(UPDATED_ELEMENT_MENU_REF);
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(updatedBarcode);

        restBarcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, barcodeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barcodeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Barcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBarcodeToMatchAllProperties(updatedBarcode);
    }

    @Test
    @Transactional
    void putNonExistingBarcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barcode.setId(intCount.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, barcodeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barcodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBarcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barcode.setId(intCount.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(barcodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBarcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barcode.setId(intCount.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barcodeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Barcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBarcodeWithPatch() throws Exception {
        // Initialize the database
        insertedBarcode = barcodeRepository.saveAndFlush(barcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the barcode using partial update
        Barcode partialUpdatedBarcode = new Barcode();
        partialUpdatedBarcode.setId(barcode.getId());

        partialUpdatedBarcode
            .coutPreparation(UPDATED_COUT_PREPARATION)
            .prixNumSequence(UPDATED_PRIX_NUM_SEQUENCE)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBarcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBarcode))
            )
            .andExpect(status().isOk());

        // Validate the Barcode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBarcodeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBarcode, barcode), getPersistedBarcode(barcode));
    }

    @Test
    @Transactional
    void fullUpdateBarcodeWithPatch() throws Exception {
        // Initialize the database
        insertedBarcode = barcodeRepository.saveAndFlush(barcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the barcode using partial update
        Barcode partialUpdatedBarcode = new Barcode();
        partialUpdatedBarcode.setId(barcode.getId());

        partialUpdatedBarcode
            .num(UPDATED_NUM)
            .barcode(UPDATED_BARCODE)
            .prix(UPDATED_PRIX)
            .coutPreparation(UPDATED_COUT_PREPARATION)
            .defNumSequence(UPDATED_DEF_NUM_SEQUENCE)
            .prixNumSequence(UPDATED_PRIX_NUM_SEQUENCE)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF)
            .elementMenuRef(UPDATED_ELEMENT_MENU_REF);

        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBarcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBarcode))
            )
            .andExpect(status().isOk());

        // Validate the Barcode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBarcodeUpdatableFieldsEquals(partialUpdatedBarcode, getPersistedBarcode(partialUpdatedBarcode));
    }

    @Test
    @Transactional
    void patchNonExistingBarcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barcode.setId(intCount.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, barcodeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(barcodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBarcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barcode.setId(intCount.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(barcodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBarcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barcode.setId(intCount.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(barcodeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Barcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBarcode() throws Exception {
        // Initialize the database
        insertedBarcode = barcodeRepository.saveAndFlush(barcode);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the barcode
        restBarcodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, barcode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return barcodeRepository.count();
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

    protected Barcode getPersistedBarcode(Barcode barcode) {
        return barcodeRepository.findById(barcode.getId()).orElseThrow();
    }

    protected void assertPersistedBarcodeToMatchAllProperties(Barcode expectedBarcode) {
        assertBarcodeAllPropertiesEquals(expectedBarcode, getPersistedBarcode(expectedBarcode));
    }

    protected void assertPersistedBarcodeToMatchUpdatableProperties(Barcode expectedBarcode) {
        assertBarcodeAllUpdatablePropertiesEquals(expectedBarcode, getPersistedBarcode(expectedBarcode));
    }
}
