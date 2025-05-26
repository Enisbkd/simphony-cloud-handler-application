package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.FactureEnTeteAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static mc.sbm.simphonycloud.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.FactureEnTete;
import mc.sbm.simphonycloud.repository.FactureEnTeteRepository;
import mc.sbm.simphonycloud.service.dto.FactureEnTeteDTO;
import mc.sbm.simphonycloud.service.mapper.FactureEnTeteMapper;
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
 * Integration tests for the {@link FactureEnTeteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FactureEnTeteResourceIT {

    private static final Integer DEFAULT_NUM = 1;
    private static final Integer UPDATED_NUM = 2;

    private static final String DEFAULT_FACTURE_REF = "AAAAAAAAAA";
    private static final String UPDATED_FACTURE_REF = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_OUVERTURE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OUVERTURE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FERMETURE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FERMETURE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_EST_ANNULE = false;
    private static final Boolean UPDATED_EST_ANNULE = true;

    private static final Integer DEFAULT_NBRE_PAX = 1;
    private static final Integer UPDATED_NBRE_PAX = 2;

    private static final Integer DEFAULT_NUM_TABLE = 1;
    private static final Integer UPDATED_NUM_TABLE = 2;

    private static final Integer DEFAULT_TAXE_MONTANT_TOTAL = 1;
    private static final Integer UPDATED_TAXE_MONTANT_TOTAL = 2;

    private static final Integer DEFAULT_SOUS_TOTAL = 1;
    private static final Integer UPDATED_SOUS_TOTAL = 2;

    private static final Integer DEFAULT_FACTURE_TOTAL = 1;
    private static final Integer UPDATED_FACTURE_TOTAL = 2;

    private static final Integer DEFAULT_COMMISSION_TOTAL = 1;
    private static final Integer UPDATED_COMMISSION_TOTAL = 2;

    private static final Integer DEFAULT_TIP_TOTAL = 1;
    private static final Integer UPDATED_TIP_TOTAL = 2;

    private static final Integer DEFAULT_REMISE_TOTAL = 1;
    private static final Integer UPDATED_REMISE_TOTAL = 2;

    private static final Integer DEFAULT_ERREURS_CORRIGEES_TOTAL = 1;
    private static final Integer UPDATED_ERREURS_CORRIGEES_TOTAL = 2;

    private static final Integer DEFAULT_RETOUR_TOTAL = 1;
    private static final Integer UPDATED_RETOUR_TOTAL = 2;

    private static final Integer DEFAULT_XFER_TO_FACTURE_EN_TETE_REF = 1;
    private static final Integer UPDATED_XFER_TO_FACTURE_EN_TETE_REF = 2;

    private static final String DEFAULT_XFER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_XFER_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_CATEGORIE_POINT_DE_VENTE_REF = 1;
    private static final Integer UPDATED_CATEGORIE_POINT_DE_VENTE_REF = 2;

    private static final Integer DEFAULT_POINT_DE_VENTE_REF = 1;
    private static final Integer UPDATED_POINT_DE_VENTE_REF = 2;

    private static final String ENTITY_API_URL = "/api/facture-en-tetes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FactureEnTeteRepository factureEnTeteRepository;

    @Autowired
    private FactureEnTeteMapper factureEnTeteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactureEnTeteMockMvc;

    private FactureEnTete factureEnTete;

    private FactureEnTete insertedFactureEnTete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureEnTete createEntity() {
        return new FactureEnTete()
            .num(DEFAULT_NUM)
            .factureRef(DEFAULT_FACTURE_REF)
            .ouvertureDateTime(DEFAULT_OUVERTURE_DATE_TIME)
            .fermetureDateTime(DEFAULT_FERMETURE_DATE_TIME)
            .estAnnule(DEFAULT_EST_ANNULE)
            .nbrePax(DEFAULT_NBRE_PAX)
            .numTable(DEFAULT_NUM_TABLE)
            .taxeMontantTotal(DEFAULT_TAXE_MONTANT_TOTAL)
            .sousTotal(DEFAULT_SOUS_TOTAL)
            .factureTotal(DEFAULT_FACTURE_TOTAL)
            .commissionTotal(DEFAULT_COMMISSION_TOTAL)
            .tipTotal(DEFAULT_TIP_TOTAL)
            .remiseTotal(DEFAULT_REMISE_TOTAL)
            .erreursCorrigeesTotal(DEFAULT_ERREURS_CORRIGEES_TOTAL)
            .retourTotal(DEFAULT_RETOUR_TOTAL)
            .xferToFactureEnTeteRef(DEFAULT_XFER_TO_FACTURE_EN_TETE_REF)
            .xferStatus(DEFAULT_XFER_STATUS)
            .categoriePointDeVenteRef(DEFAULT_CATEGORIE_POINT_DE_VENTE_REF)
            .pointDeVenteRef(DEFAULT_POINT_DE_VENTE_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureEnTete createUpdatedEntity() {
        return new FactureEnTete()
            .num(UPDATED_NUM)
            .factureRef(UPDATED_FACTURE_REF)
            .ouvertureDateTime(UPDATED_OUVERTURE_DATE_TIME)
            .fermetureDateTime(UPDATED_FERMETURE_DATE_TIME)
            .estAnnule(UPDATED_EST_ANNULE)
            .nbrePax(UPDATED_NBRE_PAX)
            .numTable(UPDATED_NUM_TABLE)
            .taxeMontantTotal(UPDATED_TAXE_MONTANT_TOTAL)
            .sousTotal(UPDATED_SOUS_TOTAL)
            .factureTotal(UPDATED_FACTURE_TOTAL)
            .commissionTotal(UPDATED_COMMISSION_TOTAL)
            .tipTotal(UPDATED_TIP_TOTAL)
            .remiseTotal(UPDATED_REMISE_TOTAL)
            .erreursCorrigeesTotal(UPDATED_ERREURS_CORRIGEES_TOTAL)
            .retourTotal(UPDATED_RETOUR_TOTAL)
            .xferToFactureEnTeteRef(UPDATED_XFER_TO_FACTURE_EN_TETE_REF)
            .xferStatus(UPDATED_XFER_STATUS)
            .categoriePointDeVenteRef(UPDATED_CATEGORIE_POINT_DE_VENTE_REF)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);
    }

    @BeforeEach
    void initTest() {
        factureEnTete = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFactureEnTete != null) {
            factureEnTeteRepository.delete(insertedFactureEnTete);
            insertedFactureEnTete = null;
        }
    }

    @Test
    @Transactional
    void createFactureEnTete() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FactureEnTete
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(factureEnTete);
        var returnedFactureEnTeteDTO = om.readValue(
            restFactureEnTeteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factureEnTeteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FactureEnTeteDTO.class
        );

        // Validate the FactureEnTete in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFactureEnTete = factureEnTeteMapper.toEntity(returnedFactureEnTeteDTO);
        assertFactureEnTeteUpdatableFieldsEquals(returnedFactureEnTete, getPersistedFactureEnTete(returnedFactureEnTete));

        insertedFactureEnTete = returnedFactureEnTete;
    }

    @Test
    @Transactional
    void createFactureEnTeteWithExistingId() throws Exception {
        // Create the FactureEnTete with an existing ID
        factureEnTete.setId(1);
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(factureEnTete);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureEnTeteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factureEnTeteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FactureEnTete in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactureEnTetes() throws Exception {
        // Initialize the database
        insertedFactureEnTete = factureEnTeteRepository.saveAndFlush(factureEnTete);

        // Get all the factureEnTeteList
        restFactureEnTeteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factureEnTete.getId().intValue())))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].factureRef").value(hasItem(DEFAULT_FACTURE_REF)))
            .andExpect(jsonPath("$.[*].ouvertureDateTime").value(hasItem(sameInstant(DEFAULT_OUVERTURE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].fermetureDateTime").value(hasItem(sameInstant(DEFAULT_FERMETURE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].estAnnule").value(hasItem(DEFAULT_EST_ANNULE)))
            .andExpect(jsonPath("$.[*].nbrePax").value(hasItem(DEFAULT_NBRE_PAX)))
            .andExpect(jsonPath("$.[*].numTable").value(hasItem(DEFAULT_NUM_TABLE)))
            .andExpect(jsonPath("$.[*].taxeMontantTotal").value(hasItem(DEFAULT_TAXE_MONTANT_TOTAL)))
            .andExpect(jsonPath("$.[*].sousTotal").value(hasItem(DEFAULT_SOUS_TOTAL)))
            .andExpect(jsonPath("$.[*].factureTotal").value(hasItem(DEFAULT_FACTURE_TOTAL)))
            .andExpect(jsonPath("$.[*].commissionTotal").value(hasItem(DEFAULT_COMMISSION_TOTAL)))
            .andExpect(jsonPath("$.[*].tipTotal").value(hasItem(DEFAULT_TIP_TOTAL)))
            .andExpect(jsonPath("$.[*].remiseTotal").value(hasItem(DEFAULT_REMISE_TOTAL)))
            .andExpect(jsonPath("$.[*].erreursCorrigeesTotal").value(hasItem(DEFAULT_ERREURS_CORRIGEES_TOTAL)))
            .andExpect(jsonPath("$.[*].retourTotal").value(hasItem(DEFAULT_RETOUR_TOTAL)))
            .andExpect(jsonPath("$.[*].xferToFactureEnTeteRef").value(hasItem(DEFAULT_XFER_TO_FACTURE_EN_TETE_REF)))
            .andExpect(jsonPath("$.[*].xferStatus").value(hasItem(DEFAULT_XFER_STATUS)))
            .andExpect(jsonPath("$.[*].categoriePointDeVenteRef").value(hasItem(DEFAULT_CATEGORIE_POINT_DE_VENTE_REF)))
            .andExpect(jsonPath("$.[*].pointDeVenteRef").value(hasItem(DEFAULT_POINT_DE_VENTE_REF)));
    }

    @Test
    @Transactional
    void getFactureEnTete() throws Exception {
        // Initialize the database
        insertedFactureEnTete = factureEnTeteRepository.saveAndFlush(factureEnTete);

        // Get the factureEnTete
        restFactureEnTeteMockMvc
            .perform(get(ENTITY_API_URL_ID, factureEnTete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factureEnTete.getId().intValue()))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.factureRef").value(DEFAULT_FACTURE_REF))
            .andExpect(jsonPath("$.ouvertureDateTime").value(sameInstant(DEFAULT_OUVERTURE_DATE_TIME)))
            .andExpect(jsonPath("$.fermetureDateTime").value(sameInstant(DEFAULT_FERMETURE_DATE_TIME)))
            .andExpect(jsonPath("$.estAnnule").value(DEFAULT_EST_ANNULE))
            .andExpect(jsonPath("$.nbrePax").value(DEFAULT_NBRE_PAX))
            .andExpect(jsonPath("$.numTable").value(DEFAULT_NUM_TABLE))
            .andExpect(jsonPath("$.taxeMontantTotal").value(DEFAULT_TAXE_MONTANT_TOTAL))
            .andExpect(jsonPath("$.sousTotal").value(DEFAULT_SOUS_TOTAL))
            .andExpect(jsonPath("$.factureTotal").value(DEFAULT_FACTURE_TOTAL))
            .andExpect(jsonPath("$.commissionTotal").value(DEFAULT_COMMISSION_TOTAL))
            .andExpect(jsonPath("$.tipTotal").value(DEFAULT_TIP_TOTAL))
            .andExpect(jsonPath("$.remiseTotal").value(DEFAULT_REMISE_TOTAL))
            .andExpect(jsonPath("$.erreursCorrigeesTotal").value(DEFAULT_ERREURS_CORRIGEES_TOTAL))
            .andExpect(jsonPath("$.retourTotal").value(DEFAULT_RETOUR_TOTAL))
            .andExpect(jsonPath("$.xferToFactureEnTeteRef").value(DEFAULT_XFER_TO_FACTURE_EN_TETE_REF))
            .andExpect(jsonPath("$.xferStatus").value(DEFAULT_XFER_STATUS))
            .andExpect(jsonPath("$.categoriePointDeVenteRef").value(DEFAULT_CATEGORIE_POINT_DE_VENTE_REF))
            .andExpect(jsonPath("$.pointDeVenteRef").value(DEFAULT_POINT_DE_VENTE_REF));
    }

    @Test
    @Transactional
    void getNonExistingFactureEnTete() throws Exception {
        // Get the factureEnTete
        restFactureEnTeteMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFactureEnTete() throws Exception {
        // Initialize the database
        insertedFactureEnTete = factureEnTeteRepository.saveAndFlush(factureEnTete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factureEnTete
        FactureEnTete updatedFactureEnTete = factureEnTeteRepository.findById(factureEnTete.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFactureEnTete are not directly saved in db
        em.detach(updatedFactureEnTete);
        updatedFactureEnTete
            .num(UPDATED_NUM)
            .factureRef(UPDATED_FACTURE_REF)
            .ouvertureDateTime(UPDATED_OUVERTURE_DATE_TIME)
            .fermetureDateTime(UPDATED_FERMETURE_DATE_TIME)
            .estAnnule(UPDATED_EST_ANNULE)
            .nbrePax(UPDATED_NBRE_PAX)
            .numTable(UPDATED_NUM_TABLE)
            .taxeMontantTotal(UPDATED_TAXE_MONTANT_TOTAL)
            .sousTotal(UPDATED_SOUS_TOTAL)
            .factureTotal(UPDATED_FACTURE_TOTAL)
            .commissionTotal(UPDATED_COMMISSION_TOTAL)
            .tipTotal(UPDATED_TIP_TOTAL)
            .remiseTotal(UPDATED_REMISE_TOTAL)
            .erreursCorrigeesTotal(UPDATED_ERREURS_CORRIGEES_TOTAL)
            .retourTotal(UPDATED_RETOUR_TOTAL)
            .xferToFactureEnTeteRef(UPDATED_XFER_TO_FACTURE_EN_TETE_REF)
            .xferStatus(UPDATED_XFER_STATUS)
            .categoriePointDeVenteRef(UPDATED_CATEGORIE_POINT_DE_VENTE_REF)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(updatedFactureEnTete);

        restFactureEnTeteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureEnTeteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factureEnTeteDTO))
            )
            .andExpect(status().isOk());

        // Validate the FactureEnTete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFactureEnTeteToMatchAllProperties(updatedFactureEnTete);
    }

    @Test
    @Transactional
    void putNonExistingFactureEnTete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureEnTete.setId(intCount.incrementAndGet());

        // Create the FactureEnTete
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(factureEnTete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureEnTeteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureEnTeteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factureEnTeteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureEnTete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactureEnTete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureEnTete.setId(intCount.incrementAndGet());

        // Create the FactureEnTete
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(factureEnTete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureEnTeteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factureEnTeteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureEnTete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactureEnTete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureEnTete.setId(intCount.incrementAndGet());

        // Create the FactureEnTete
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(factureEnTete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureEnTeteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factureEnTeteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureEnTete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactureEnTeteWithPatch() throws Exception {
        // Initialize the database
        insertedFactureEnTete = factureEnTeteRepository.saveAndFlush(factureEnTete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factureEnTete using partial update
        FactureEnTete partialUpdatedFactureEnTete = new FactureEnTete();
        partialUpdatedFactureEnTete.setId(factureEnTete.getId());

        partialUpdatedFactureEnTete
            .num(UPDATED_NUM)
            .factureRef(UPDATED_FACTURE_REF)
            .fermetureDateTime(UPDATED_FERMETURE_DATE_TIME)
            .estAnnule(UPDATED_EST_ANNULE)
            .nbrePax(UPDATED_NBRE_PAX)
            .numTable(UPDATED_NUM_TABLE)
            .taxeMontantTotal(UPDATED_TAXE_MONTANT_TOTAL)
            .tipTotal(UPDATED_TIP_TOTAL)
            .remiseTotal(UPDATED_REMISE_TOTAL)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restFactureEnTeteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureEnTete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFactureEnTete))
            )
            .andExpect(status().isOk());

        // Validate the FactureEnTete in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFactureEnTeteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFactureEnTete, factureEnTete),
            getPersistedFactureEnTete(factureEnTete)
        );
    }

    @Test
    @Transactional
    void fullUpdateFactureEnTeteWithPatch() throws Exception {
        // Initialize the database
        insertedFactureEnTete = factureEnTeteRepository.saveAndFlush(factureEnTete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factureEnTete using partial update
        FactureEnTete partialUpdatedFactureEnTete = new FactureEnTete();
        partialUpdatedFactureEnTete.setId(factureEnTete.getId());

        partialUpdatedFactureEnTete
            .num(UPDATED_NUM)
            .factureRef(UPDATED_FACTURE_REF)
            .ouvertureDateTime(UPDATED_OUVERTURE_DATE_TIME)
            .fermetureDateTime(UPDATED_FERMETURE_DATE_TIME)
            .estAnnule(UPDATED_EST_ANNULE)
            .nbrePax(UPDATED_NBRE_PAX)
            .numTable(UPDATED_NUM_TABLE)
            .taxeMontantTotal(UPDATED_TAXE_MONTANT_TOTAL)
            .sousTotal(UPDATED_SOUS_TOTAL)
            .factureTotal(UPDATED_FACTURE_TOTAL)
            .commissionTotal(UPDATED_COMMISSION_TOTAL)
            .tipTotal(UPDATED_TIP_TOTAL)
            .remiseTotal(UPDATED_REMISE_TOTAL)
            .erreursCorrigeesTotal(UPDATED_ERREURS_CORRIGEES_TOTAL)
            .retourTotal(UPDATED_RETOUR_TOTAL)
            .xferToFactureEnTeteRef(UPDATED_XFER_TO_FACTURE_EN_TETE_REF)
            .xferStatus(UPDATED_XFER_STATUS)
            .categoriePointDeVenteRef(UPDATED_CATEGORIE_POINT_DE_VENTE_REF)
            .pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restFactureEnTeteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureEnTete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFactureEnTete))
            )
            .andExpect(status().isOk());

        // Validate the FactureEnTete in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFactureEnTeteUpdatableFieldsEquals(partialUpdatedFactureEnTete, getPersistedFactureEnTete(partialUpdatedFactureEnTete));
    }

    @Test
    @Transactional
    void patchNonExistingFactureEnTete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureEnTete.setId(intCount.incrementAndGet());

        // Create the FactureEnTete
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(factureEnTete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureEnTeteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factureEnTeteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(factureEnTeteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureEnTete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactureEnTete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureEnTete.setId(intCount.incrementAndGet());

        // Create the FactureEnTete
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(factureEnTete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureEnTeteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(factureEnTeteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureEnTete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactureEnTete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureEnTete.setId(intCount.incrementAndGet());

        // Create the FactureEnTete
        FactureEnTeteDTO factureEnTeteDTO = factureEnTeteMapper.toDto(factureEnTete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureEnTeteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(factureEnTeteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureEnTete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactureEnTete() throws Exception {
        // Initialize the database
        insertedFactureEnTete = factureEnTeteRepository.saveAndFlush(factureEnTete);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the factureEnTete
        restFactureEnTeteMockMvc
            .perform(delete(ENTITY_API_URL_ID, factureEnTete.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return factureEnTeteRepository.count();
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

    protected FactureEnTete getPersistedFactureEnTete(FactureEnTete factureEnTete) {
        return factureEnTeteRepository.findById(factureEnTete.getId()).orElseThrow();
    }

    protected void assertPersistedFactureEnTeteToMatchAllProperties(FactureEnTete expectedFactureEnTete) {
        assertFactureEnTeteAllPropertiesEquals(expectedFactureEnTete, getPersistedFactureEnTete(expectedFactureEnTete));
    }

    protected void assertPersistedFactureEnTeteToMatchUpdatableProperties(FactureEnTete expectedFactureEnTete) {
        assertFactureEnTeteAllUpdatablePropertiesEquals(expectedFactureEnTete, getPersistedFactureEnTete(expectedFactureEnTete));
    }
}
