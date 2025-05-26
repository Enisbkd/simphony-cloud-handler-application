package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.FactureDetailAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.FactureDetail;
import mc.sbm.simphonycloud.repository.FactureDetailRepository;
import mc.sbm.simphonycloud.service.dto.FactureDetailDTO;
import mc.sbm.simphonycloud.service.mapper.FactureDetailMapper;
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
 * Integration tests for the {@link FactureDetailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FactureDetailResourceIT {

    private static final Integer DEFAULT_FACTURE_EN_TETE_REF = 1;
    private static final Integer UPDATED_FACTURE_EN_TETE_REF = 2;

    private static final Integer DEFAULT_NUM_LIGNE = 1;
    private static final Integer UPDATED_NUM_LIGNE = 2;

    private static final String DEFAULT_DETAIL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UTC_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UTC_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LCL_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LCL_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NUM_SIEGE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_SIEGE = "BBBBBBBBBB";

    private static final Float DEFAULT_NIVEAU_PRIX = 1F;
    private static final Float UPDATED_NIVEAU_PRIX = 2F;

    private static final Float DEFAULT_TOTAL_AFFICHE = 1F;
    private static final Float UPDATED_TOTAL_AFFICHE = 2F;

    private static final Integer DEFAULT_QUANTITE_AFFICHE = 1;
    private static final Integer UPDATED_QUANTITE_AFFICHE = 2;

    private static final Boolean DEFAULT_EST_ERREUR = false;
    private static final Boolean UPDATED_EST_ERREUR = true;

    private static final Boolean DEFAULT_EST_NUL = false;
    private static final Boolean UPDATED_EST_NUL = true;

    private static final Boolean DEFAULT_EST_RETOURNE = false;
    private static final Boolean UPDATED_EST_RETOURNE = true;

    private static final Boolean DEFAULT_EST_INVISIBLE = false;
    private static final Boolean UPDATED_EST_INVISIBLE = true;

    private static final Integer DEFAULT_TOTAL_LIGNE = 1;
    private static final Integer UPDATED_TOTAL_LIGNE = 2;

    private static final Integer DEFAULT_CODE_RAISON_REF = 1;
    private static final Integer UPDATED_CODE_RAISON_REF = 2;

    private static final Integer DEFAULT_MULTIPLICATEUR = 1;
    private static final Integer UPDATED_MULTIPLICATEUR = 2;

    private static final String DEFAULT_REFERENCE_INFO = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_INFO_2 = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_INFO_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARTIE_DE_JOURNEE_REF = 1;
    private static final Integer UPDATED_PARTIE_DE_JOURNEE_REF = 2;

    private static final Integer DEFAULT_PERIODE_DE_SERVICE_REF = 1;
    private static final Integer UPDATED_PERIODE_DE_SERVICE_REF = 2;

    private static final Integer DEFAULT_NUM_CHRONO = 1;
    private static final Integer UPDATED_NUM_CHRONO = 2;

    private static final Integer DEFAULT_PARENT_FACTURE_DETAIL_REF = 1;
    private static final Integer UPDATED_PARENT_FACTURE_DETAIL_REF = 2;

    private static final Integer DEFAULT_TAXE_POURCENTAGE = 1;
    private static final Integer UPDATED_TAXE_POURCENTAGE = 2;

    private static final Integer DEFAULT_TAXE_MONTANT = 1;
    private static final Integer UPDATED_TAXE_MONTANT = 2;

    private static final Integer DEFAULT_MODE_PAIEMENT_TOTAL = 1;
    private static final Integer UPDATED_MODE_PAIEMENT_TOTAL = 2;

    private static final Integer DEFAULT_PRIX = 1;
    private static final Integer UPDATED_PRIX = 2;

    private static final Integer DEFAULT_TRANSACTION_EMPLOYE_REF = 1;
    private static final Integer UPDATED_TRANSACTION_EMPLOYE_REF = 2;

    private static final Integer DEFAULT_TRANSFERT_EMPLOYE_REF = 1;
    private static final Integer UPDATED_TRANSFERT_EMPLOYE_REF = 2;

    private static final Integer DEFAULT_MANAGER_EMPLOYE_REF = 1;
    private static final Integer UPDATED_MANAGER_EMPLOYE_REF = 2;

    private static final Integer DEFAULT_REPAS_EMPLOYE_REF = 1;
    private static final Integer UPDATED_REPAS_EMPLOYE_REF = 2;

    private static final Integer DEFAULT_REMISE_REF = 1;
    private static final Integer UPDATED_REMISE_REF = 2;

    private static final Integer DEFAULT_REMISE_ELEMENT_MENU_REF = 1;
    private static final Integer UPDATED_REMISE_ELEMENT_MENU_REF = 2;

    private static final Integer DEFAULT_COMMISSION_SERVICE_REF = 1;
    private static final Integer UPDATED_COMMISSION_SERVICE_REF = 2;

    private static final Integer DEFAULT_MODE_PAIEMENT_REF = 1;
    private static final Integer UPDATED_MODE_PAIEMENT_REF = 2;

    private static final Integer DEFAULT_ELEMENT_MENU_REF = 1;
    private static final Integer UPDATED_ELEMENT_MENU_REF = 2;

    private static final String ENTITY_API_URL = "/api/facture-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FactureDetailRepository factureDetailRepository;

    @Autowired
    private FactureDetailMapper factureDetailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactureDetailMockMvc;

    private FactureDetail factureDetail;

    private FactureDetail insertedFactureDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureDetail createEntity() {
        return new FactureDetail()
            .factureEnTeteRef(DEFAULT_FACTURE_EN_TETE_REF)
            .numLigne(DEFAULT_NUM_LIGNE)
            .detailType(DEFAULT_DETAIL_TYPE)
            .utcDateTime(DEFAULT_UTC_DATE_TIME)
            .lclDateTime(DEFAULT_LCL_DATE_TIME)
            .numSiege(DEFAULT_NUM_SIEGE)
            .niveauPrix(DEFAULT_NIVEAU_PRIX)
            .totalAffiche(DEFAULT_TOTAL_AFFICHE)
            .quantiteAffiche(DEFAULT_QUANTITE_AFFICHE)
            .estErreur(DEFAULT_EST_ERREUR)
            .estNul(DEFAULT_EST_NUL)
            .estRetourne(DEFAULT_EST_RETOURNE)
            .estInvisible(DEFAULT_EST_INVISIBLE)
            .totalLigne(DEFAULT_TOTAL_LIGNE)
            .codeRaisonRef(DEFAULT_CODE_RAISON_REF)
            .multiplicateur(DEFAULT_MULTIPLICATEUR)
            .referenceInfo(DEFAULT_REFERENCE_INFO)
            .referenceInfo2(DEFAULT_REFERENCE_INFO_2)
            .partieDeJourneeRef(DEFAULT_PARTIE_DE_JOURNEE_REF)
            .periodeDeServiceRef(DEFAULT_PERIODE_DE_SERVICE_REF)
            .numChrono(DEFAULT_NUM_CHRONO)
            .parentFactureDetailRef(DEFAULT_PARENT_FACTURE_DETAIL_REF)
            .taxePourcentage(DEFAULT_TAXE_POURCENTAGE)
            .taxeMontant(DEFAULT_TAXE_MONTANT)
            .modePaiementTotal(DEFAULT_MODE_PAIEMENT_TOTAL)
            .prix(DEFAULT_PRIX)
            .transactionEmployeRef(DEFAULT_TRANSACTION_EMPLOYE_REF)
            .transfertEmployeRef(DEFAULT_TRANSFERT_EMPLOYE_REF)
            .managerEmployeRef(DEFAULT_MANAGER_EMPLOYE_REF)
            .repasEmployeRef(DEFAULT_REPAS_EMPLOYE_REF)
            .remiseRef(DEFAULT_REMISE_REF)
            .remiseElementMenuRef(DEFAULT_REMISE_ELEMENT_MENU_REF)
            .commissionServiceRef(DEFAULT_COMMISSION_SERVICE_REF)
            .modePaiementRef(DEFAULT_MODE_PAIEMENT_REF)
            .elementMenuRef(DEFAULT_ELEMENT_MENU_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureDetail createUpdatedEntity() {
        return new FactureDetail()
            .factureEnTeteRef(UPDATED_FACTURE_EN_TETE_REF)
            .numLigne(UPDATED_NUM_LIGNE)
            .detailType(UPDATED_DETAIL_TYPE)
            .utcDateTime(UPDATED_UTC_DATE_TIME)
            .lclDateTime(UPDATED_LCL_DATE_TIME)
            .numSiege(UPDATED_NUM_SIEGE)
            .niveauPrix(UPDATED_NIVEAU_PRIX)
            .totalAffiche(UPDATED_TOTAL_AFFICHE)
            .quantiteAffiche(UPDATED_QUANTITE_AFFICHE)
            .estErreur(UPDATED_EST_ERREUR)
            .estNul(UPDATED_EST_NUL)
            .estRetourne(UPDATED_EST_RETOURNE)
            .estInvisible(UPDATED_EST_INVISIBLE)
            .totalLigne(UPDATED_TOTAL_LIGNE)
            .codeRaisonRef(UPDATED_CODE_RAISON_REF)
            .multiplicateur(UPDATED_MULTIPLICATEUR)
            .referenceInfo(UPDATED_REFERENCE_INFO)
            .referenceInfo2(UPDATED_REFERENCE_INFO_2)
            .partieDeJourneeRef(UPDATED_PARTIE_DE_JOURNEE_REF)
            .periodeDeServiceRef(UPDATED_PERIODE_DE_SERVICE_REF)
            .numChrono(UPDATED_NUM_CHRONO)
            .parentFactureDetailRef(UPDATED_PARENT_FACTURE_DETAIL_REF)
            .taxePourcentage(UPDATED_TAXE_POURCENTAGE)
            .taxeMontant(UPDATED_TAXE_MONTANT)
            .modePaiementTotal(UPDATED_MODE_PAIEMENT_TOTAL)
            .prix(UPDATED_PRIX)
            .transactionEmployeRef(UPDATED_TRANSACTION_EMPLOYE_REF)
            .transfertEmployeRef(UPDATED_TRANSFERT_EMPLOYE_REF)
            .managerEmployeRef(UPDATED_MANAGER_EMPLOYE_REF)
            .repasEmployeRef(UPDATED_REPAS_EMPLOYE_REF)
            .remiseRef(UPDATED_REMISE_REF)
            .remiseElementMenuRef(UPDATED_REMISE_ELEMENT_MENU_REF)
            .commissionServiceRef(UPDATED_COMMISSION_SERVICE_REF)
            .modePaiementRef(UPDATED_MODE_PAIEMENT_REF)
            .elementMenuRef(UPDATED_ELEMENT_MENU_REF);
    }

    @BeforeEach
    void initTest() {
        factureDetail = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFactureDetail != null) {
            factureDetailRepository.delete(insertedFactureDetail);
            insertedFactureDetail = null;
        }
    }

    @Test
    @Transactional
    void createFactureDetail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FactureDetail
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(factureDetail);
        var returnedFactureDetailDTO = om.readValue(
            restFactureDetailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factureDetailDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FactureDetailDTO.class
        );

        // Validate the FactureDetail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFactureDetail = factureDetailMapper.toEntity(returnedFactureDetailDTO);
        assertFactureDetailUpdatableFieldsEquals(returnedFactureDetail, getPersistedFactureDetail(returnedFactureDetail));

        insertedFactureDetail = returnedFactureDetail;
    }

    @Test
    @Transactional
    void createFactureDetailWithExistingId() throws Exception {
        // Create the FactureDetail with an existing ID
        factureDetail.setId(1);
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(factureDetail);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factureDetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FactureDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactureDetails() throws Exception {
        // Initialize the database
        insertedFactureDetail = factureDetailRepository.saveAndFlush(factureDetail);

        // Get all the factureDetailList
        restFactureDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factureDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].factureEnTeteRef").value(hasItem(DEFAULT_FACTURE_EN_TETE_REF)))
            .andExpect(jsonPath("$.[*].numLigne").value(hasItem(DEFAULT_NUM_LIGNE)))
            .andExpect(jsonPath("$.[*].detailType").value(hasItem(DEFAULT_DETAIL_TYPE)))
            .andExpect(jsonPath("$.[*].utcDateTime").value(hasItem(DEFAULT_UTC_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].lclDateTime").value(hasItem(DEFAULT_LCL_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].numSiege").value(hasItem(DEFAULT_NUM_SIEGE)))
            .andExpect(jsonPath("$.[*].niveauPrix").value(hasItem(DEFAULT_NIVEAU_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAffiche").value(hasItem(DEFAULT_TOTAL_AFFICHE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteAffiche").value(hasItem(DEFAULT_QUANTITE_AFFICHE)))
            .andExpect(jsonPath("$.[*].estErreur").value(hasItem(DEFAULT_EST_ERREUR)))
            .andExpect(jsonPath("$.[*].estNul").value(hasItem(DEFAULT_EST_NUL)))
            .andExpect(jsonPath("$.[*].estRetourne").value(hasItem(DEFAULT_EST_RETOURNE)))
            .andExpect(jsonPath("$.[*].estInvisible").value(hasItem(DEFAULT_EST_INVISIBLE)))
            .andExpect(jsonPath("$.[*].totalLigne").value(hasItem(DEFAULT_TOTAL_LIGNE)))
            .andExpect(jsonPath("$.[*].codeRaisonRef").value(hasItem(DEFAULT_CODE_RAISON_REF)))
            .andExpect(jsonPath("$.[*].multiplicateur").value(hasItem(DEFAULT_MULTIPLICATEUR)))
            .andExpect(jsonPath("$.[*].referenceInfo").value(hasItem(DEFAULT_REFERENCE_INFO)))
            .andExpect(jsonPath("$.[*].referenceInfo2").value(hasItem(DEFAULT_REFERENCE_INFO_2)))
            .andExpect(jsonPath("$.[*].partieDeJourneeRef").value(hasItem(DEFAULT_PARTIE_DE_JOURNEE_REF)))
            .andExpect(jsonPath("$.[*].periodeDeServiceRef").value(hasItem(DEFAULT_PERIODE_DE_SERVICE_REF)))
            .andExpect(jsonPath("$.[*].numChrono").value(hasItem(DEFAULT_NUM_CHRONO)))
            .andExpect(jsonPath("$.[*].parentFactureDetailRef").value(hasItem(DEFAULT_PARENT_FACTURE_DETAIL_REF)))
            .andExpect(jsonPath("$.[*].taxePourcentage").value(hasItem(DEFAULT_TAXE_POURCENTAGE)))
            .andExpect(jsonPath("$.[*].taxeMontant").value(hasItem(DEFAULT_TAXE_MONTANT)))
            .andExpect(jsonPath("$.[*].modePaiementTotal").value(hasItem(DEFAULT_MODE_PAIEMENT_TOTAL)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)))
            .andExpect(jsonPath("$.[*].transactionEmployeRef").value(hasItem(DEFAULT_TRANSACTION_EMPLOYE_REF)))
            .andExpect(jsonPath("$.[*].transfertEmployeRef").value(hasItem(DEFAULT_TRANSFERT_EMPLOYE_REF)))
            .andExpect(jsonPath("$.[*].managerEmployeRef").value(hasItem(DEFAULT_MANAGER_EMPLOYE_REF)))
            .andExpect(jsonPath("$.[*].repasEmployeRef").value(hasItem(DEFAULT_REPAS_EMPLOYE_REF)))
            .andExpect(jsonPath("$.[*].remiseRef").value(hasItem(DEFAULT_REMISE_REF)))
            .andExpect(jsonPath("$.[*].remiseElementMenuRef").value(hasItem(DEFAULT_REMISE_ELEMENT_MENU_REF)))
            .andExpect(jsonPath("$.[*].commissionServiceRef").value(hasItem(DEFAULT_COMMISSION_SERVICE_REF)))
            .andExpect(jsonPath("$.[*].modePaiementRef").value(hasItem(DEFAULT_MODE_PAIEMENT_REF)))
            .andExpect(jsonPath("$.[*].elementMenuRef").value(hasItem(DEFAULT_ELEMENT_MENU_REF)));
    }

    @Test
    @Transactional
    void getFactureDetail() throws Exception {
        // Initialize the database
        insertedFactureDetail = factureDetailRepository.saveAndFlush(factureDetail);

        // Get the factureDetail
        restFactureDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, factureDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factureDetail.getId().intValue()))
            .andExpect(jsonPath("$.factureEnTeteRef").value(DEFAULT_FACTURE_EN_TETE_REF))
            .andExpect(jsonPath("$.numLigne").value(DEFAULT_NUM_LIGNE))
            .andExpect(jsonPath("$.detailType").value(DEFAULT_DETAIL_TYPE))
            .andExpect(jsonPath("$.utcDateTime").value(DEFAULT_UTC_DATE_TIME.toString()))
            .andExpect(jsonPath("$.lclDateTime").value(DEFAULT_LCL_DATE_TIME.toString()))
            .andExpect(jsonPath("$.numSiege").value(DEFAULT_NUM_SIEGE))
            .andExpect(jsonPath("$.niveauPrix").value(DEFAULT_NIVEAU_PRIX.doubleValue()))
            .andExpect(jsonPath("$.totalAffiche").value(DEFAULT_TOTAL_AFFICHE.doubleValue()))
            .andExpect(jsonPath("$.quantiteAffiche").value(DEFAULT_QUANTITE_AFFICHE))
            .andExpect(jsonPath("$.estErreur").value(DEFAULT_EST_ERREUR))
            .andExpect(jsonPath("$.estNul").value(DEFAULT_EST_NUL))
            .andExpect(jsonPath("$.estRetourne").value(DEFAULT_EST_RETOURNE))
            .andExpect(jsonPath("$.estInvisible").value(DEFAULT_EST_INVISIBLE))
            .andExpect(jsonPath("$.totalLigne").value(DEFAULT_TOTAL_LIGNE))
            .andExpect(jsonPath("$.codeRaisonRef").value(DEFAULT_CODE_RAISON_REF))
            .andExpect(jsonPath("$.multiplicateur").value(DEFAULT_MULTIPLICATEUR))
            .andExpect(jsonPath("$.referenceInfo").value(DEFAULT_REFERENCE_INFO))
            .andExpect(jsonPath("$.referenceInfo2").value(DEFAULT_REFERENCE_INFO_2))
            .andExpect(jsonPath("$.partieDeJourneeRef").value(DEFAULT_PARTIE_DE_JOURNEE_REF))
            .andExpect(jsonPath("$.periodeDeServiceRef").value(DEFAULT_PERIODE_DE_SERVICE_REF))
            .andExpect(jsonPath("$.numChrono").value(DEFAULT_NUM_CHRONO))
            .andExpect(jsonPath("$.parentFactureDetailRef").value(DEFAULT_PARENT_FACTURE_DETAIL_REF))
            .andExpect(jsonPath("$.taxePourcentage").value(DEFAULT_TAXE_POURCENTAGE))
            .andExpect(jsonPath("$.taxeMontant").value(DEFAULT_TAXE_MONTANT))
            .andExpect(jsonPath("$.modePaiementTotal").value(DEFAULT_MODE_PAIEMENT_TOTAL))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX))
            .andExpect(jsonPath("$.transactionEmployeRef").value(DEFAULT_TRANSACTION_EMPLOYE_REF))
            .andExpect(jsonPath("$.transfertEmployeRef").value(DEFAULT_TRANSFERT_EMPLOYE_REF))
            .andExpect(jsonPath("$.managerEmployeRef").value(DEFAULT_MANAGER_EMPLOYE_REF))
            .andExpect(jsonPath("$.repasEmployeRef").value(DEFAULT_REPAS_EMPLOYE_REF))
            .andExpect(jsonPath("$.remiseRef").value(DEFAULT_REMISE_REF))
            .andExpect(jsonPath("$.remiseElementMenuRef").value(DEFAULT_REMISE_ELEMENT_MENU_REF))
            .andExpect(jsonPath("$.commissionServiceRef").value(DEFAULT_COMMISSION_SERVICE_REF))
            .andExpect(jsonPath("$.modePaiementRef").value(DEFAULT_MODE_PAIEMENT_REF))
            .andExpect(jsonPath("$.elementMenuRef").value(DEFAULT_ELEMENT_MENU_REF));
    }

    @Test
    @Transactional
    void getNonExistingFactureDetail() throws Exception {
        // Get the factureDetail
        restFactureDetailMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFactureDetail() throws Exception {
        // Initialize the database
        insertedFactureDetail = factureDetailRepository.saveAndFlush(factureDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factureDetail
        FactureDetail updatedFactureDetail = factureDetailRepository.findById(factureDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFactureDetail are not directly saved in db
        em.detach(updatedFactureDetail);
        updatedFactureDetail
            .factureEnTeteRef(UPDATED_FACTURE_EN_TETE_REF)
            .numLigne(UPDATED_NUM_LIGNE)
            .detailType(UPDATED_DETAIL_TYPE)
            .utcDateTime(UPDATED_UTC_DATE_TIME)
            .lclDateTime(UPDATED_LCL_DATE_TIME)
            .numSiege(UPDATED_NUM_SIEGE)
            .niveauPrix(UPDATED_NIVEAU_PRIX)
            .totalAffiche(UPDATED_TOTAL_AFFICHE)
            .quantiteAffiche(UPDATED_QUANTITE_AFFICHE)
            .estErreur(UPDATED_EST_ERREUR)
            .estNul(UPDATED_EST_NUL)
            .estRetourne(UPDATED_EST_RETOURNE)
            .estInvisible(UPDATED_EST_INVISIBLE)
            .totalLigne(UPDATED_TOTAL_LIGNE)
            .codeRaisonRef(UPDATED_CODE_RAISON_REF)
            .multiplicateur(UPDATED_MULTIPLICATEUR)
            .referenceInfo(UPDATED_REFERENCE_INFO)
            .referenceInfo2(UPDATED_REFERENCE_INFO_2)
            .partieDeJourneeRef(UPDATED_PARTIE_DE_JOURNEE_REF)
            .periodeDeServiceRef(UPDATED_PERIODE_DE_SERVICE_REF)
            .numChrono(UPDATED_NUM_CHRONO)
            .parentFactureDetailRef(UPDATED_PARENT_FACTURE_DETAIL_REF)
            .taxePourcentage(UPDATED_TAXE_POURCENTAGE)
            .taxeMontant(UPDATED_TAXE_MONTANT)
            .modePaiementTotal(UPDATED_MODE_PAIEMENT_TOTAL)
            .prix(UPDATED_PRIX)
            .transactionEmployeRef(UPDATED_TRANSACTION_EMPLOYE_REF)
            .transfertEmployeRef(UPDATED_TRANSFERT_EMPLOYE_REF)
            .managerEmployeRef(UPDATED_MANAGER_EMPLOYE_REF)
            .repasEmployeRef(UPDATED_REPAS_EMPLOYE_REF)
            .remiseRef(UPDATED_REMISE_REF)
            .remiseElementMenuRef(UPDATED_REMISE_ELEMENT_MENU_REF)
            .commissionServiceRef(UPDATED_COMMISSION_SERVICE_REF)
            .modePaiementRef(UPDATED_MODE_PAIEMENT_REF)
            .elementMenuRef(UPDATED_ELEMENT_MENU_REF);
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(updatedFactureDetail);

        restFactureDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factureDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the FactureDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFactureDetailToMatchAllProperties(updatedFactureDetail);
    }

    @Test
    @Transactional
    void putNonExistingFactureDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureDetail.setId(intCount.incrementAndGet());

        // Create the FactureDetail
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(factureDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factureDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactureDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureDetail.setId(intCount.incrementAndGet());

        // Create the FactureDetail
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(factureDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factureDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactureDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureDetail.setId(intCount.incrementAndGet());

        // Create the FactureDetail
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(factureDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factureDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactureDetailWithPatch() throws Exception {
        // Initialize the database
        insertedFactureDetail = factureDetailRepository.saveAndFlush(factureDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factureDetail using partial update
        FactureDetail partialUpdatedFactureDetail = new FactureDetail();
        partialUpdatedFactureDetail.setId(factureDetail.getId());

        partialUpdatedFactureDetail
            .utcDateTime(UPDATED_UTC_DATE_TIME)
            .quantiteAffiche(UPDATED_QUANTITE_AFFICHE)
            .estErreur(UPDATED_EST_ERREUR)
            .codeRaisonRef(UPDATED_CODE_RAISON_REF)
            .multiplicateur(UPDATED_MULTIPLICATEUR)
            .referenceInfo(UPDATED_REFERENCE_INFO)
            .prix(UPDATED_PRIX)
            .transactionEmployeRef(UPDATED_TRANSACTION_EMPLOYE_REF)
            .managerEmployeRef(UPDATED_MANAGER_EMPLOYE_REF)
            .repasEmployeRef(UPDATED_REPAS_EMPLOYE_REF)
            .remiseRef(UPDATED_REMISE_REF)
            .remiseElementMenuRef(UPDATED_REMISE_ELEMENT_MENU_REF);

        restFactureDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFactureDetail))
            )
            .andExpect(status().isOk());

        // Validate the FactureDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFactureDetailUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFactureDetail, factureDetail),
            getPersistedFactureDetail(factureDetail)
        );
    }

    @Test
    @Transactional
    void fullUpdateFactureDetailWithPatch() throws Exception {
        // Initialize the database
        insertedFactureDetail = factureDetailRepository.saveAndFlush(factureDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factureDetail using partial update
        FactureDetail partialUpdatedFactureDetail = new FactureDetail();
        partialUpdatedFactureDetail.setId(factureDetail.getId());

        partialUpdatedFactureDetail
            .factureEnTeteRef(UPDATED_FACTURE_EN_TETE_REF)
            .numLigne(UPDATED_NUM_LIGNE)
            .detailType(UPDATED_DETAIL_TYPE)
            .utcDateTime(UPDATED_UTC_DATE_TIME)
            .lclDateTime(UPDATED_LCL_DATE_TIME)
            .numSiege(UPDATED_NUM_SIEGE)
            .niveauPrix(UPDATED_NIVEAU_PRIX)
            .totalAffiche(UPDATED_TOTAL_AFFICHE)
            .quantiteAffiche(UPDATED_QUANTITE_AFFICHE)
            .estErreur(UPDATED_EST_ERREUR)
            .estNul(UPDATED_EST_NUL)
            .estRetourne(UPDATED_EST_RETOURNE)
            .estInvisible(UPDATED_EST_INVISIBLE)
            .totalLigne(UPDATED_TOTAL_LIGNE)
            .codeRaisonRef(UPDATED_CODE_RAISON_REF)
            .multiplicateur(UPDATED_MULTIPLICATEUR)
            .referenceInfo(UPDATED_REFERENCE_INFO)
            .referenceInfo2(UPDATED_REFERENCE_INFO_2)
            .partieDeJourneeRef(UPDATED_PARTIE_DE_JOURNEE_REF)
            .periodeDeServiceRef(UPDATED_PERIODE_DE_SERVICE_REF)
            .numChrono(UPDATED_NUM_CHRONO)
            .parentFactureDetailRef(UPDATED_PARENT_FACTURE_DETAIL_REF)
            .taxePourcentage(UPDATED_TAXE_POURCENTAGE)
            .taxeMontant(UPDATED_TAXE_MONTANT)
            .modePaiementTotal(UPDATED_MODE_PAIEMENT_TOTAL)
            .prix(UPDATED_PRIX)
            .transactionEmployeRef(UPDATED_TRANSACTION_EMPLOYE_REF)
            .transfertEmployeRef(UPDATED_TRANSFERT_EMPLOYE_REF)
            .managerEmployeRef(UPDATED_MANAGER_EMPLOYE_REF)
            .repasEmployeRef(UPDATED_REPAS_EMPLOYE_REF)
            .remiseRef(UPDATED_REMISE_REF)
            .remiseElementMenuRef(UPDATED_REMISE_ELEMENT_MENU_REF)
            .commissionServiceRef(UPDATED_COMMISSION_SERVICE_REF)
            .modePaiementRef(UPDATED_MODE_PAIEMENT_REF)
            .elementMenuRef(UPDATED_ELEMENT_MENU_REF);

        restFactureDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFactureDetail))
            )
            .andExpect(status().isOk());

        // Validate the FactureDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFactureDetailUpdatableFieldsEquals(partialUpdatedFactureDetail, getPersistedFactureDetail(partialUpdatedFactureDetail));
    }

    @Test
    @Transactional
    void patchNonExistingFactureDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureDetail.setId(intCount.incrementAndGet());

        // Create the FactureDetail
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(factureDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factureDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(factureDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactureDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureDetail.setId(intCount.incrementAndGet());

        // Create the FactureDetail
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(factureDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(factureDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactureDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factureDetail.setId(intCount.incrementAndGet());

        // Create the FactureDetail
        FactureDetailDTO factureDetailDTO = factureDetailMapper.toDto(factureDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureDetailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(factureDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactureDetail() throws Exception {
        // Initialize the database
        insertedFactureDetail = factureDetailRepository.saveAndFlush(factureDetail);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the factureDetail
        restFactureDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, factureDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return factureDetailRepository.count();
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

    protected FactureDetail getPersistedFactureDetail(FactureDetail factureDetail) {
        return factureDetailRepository.findById(factureDetail.getId()).orElseThrow();
    }

    protected void assertPersistedFactureDetailToMatchAllProperties(FactureDetail expectedFactureDetail) {
        assertFactureDetailAllPropertiesEquals(expectedFactureDetail, getPersistedFactureDetail(expectedFactureDetail));
    }

    protected void assertPersistedFactureDetailToMatchUpdatableProperties(FactureDetail expectedFactureDetail) {
        assertFactureDetailAllUpdatablePropertiesEquals(expectedFactureDetail, getPersistedFactureDetail(expectedFactureDetail));
    }
}
