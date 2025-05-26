package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.ElementMenuAsserts.*;
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
import mc.sbm.simphonycloud.domain.ElementMenu;
import mc.sbm.simphonycloud.repository.ElementMenuRepository;
import mc.sbm.simphonycloud.service.dto.ElementMenuDTO;
import mc.sbm.simphonycloud.service.mapper.ElementMenuMapper;
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
 * Integration tests for the {@link ElementMenuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElementMenuResourceIT {

    private static final Integer DEFAULT_MASTER_ID = 1;
    private static final Integer UPDATED_MASTER_ID = 2;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final Integer DEFAULT_FAMILY_GROUP_REF = 1;
    private static final Integer UPDATED_FAMILY_GROUP_REF = 2;

    private static final Integer DEFAULT_PRIX = 1;
    private static final Integer UPDATED_PRIX = 2;

    private static final Integer DEFAULT_MENU_REF = 1;
    private static final Integer UPDATED_MENU_REF = 2;

    private static final String ENTITY_API_URL = "/api/element-menus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ElementMenuRepository elementMenuRepository;

    @Autowired
    private ElementMenuMapper elementMenuMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementMenuMockMvc;

    private ElementMenu elementMenu;

    private ElementMenu insertedElementMenu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementMenu createEntity() {
        return new ElementMenu()
            .masterId(DEFAULT_MASTER_ID)
            .nom(DEFAULT_NOM)
            .nomCourt(DEFAULT_NOM_COURT)
            .familyGroupRef(DEFAULT_FAMILY_GROUP_REF)
            .prix(DEFAULT_PRIX)
            .menuRef(DEFAULT_MENU_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementMenu createUpdatedEntity() {
        return new ElementMenu()
            .masterId(UPDATED_MASTER_ID)
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .familyGroupRef(UPDATED_FAMILY_GROUP_REF)
            .prix(UPDATED_PRIX)
            .menuRef(UPDATED_MENU_REF);
    }

    @BeforeEach
    void initTest() {
        elementMenu = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedElementMenu != null) {
            elementMenuRepository.delete(insertedElementMenu);
            insertedElementMenu = null;
        }
    }

    @Test
    @Transactional
    void createElementMenu() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);
        var returnedElementMenuDTO = om.readValue(
            restElementMenuMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elementMenuDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ElementMenuDTO.class
        );

        // Validate the ElementMenu in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedElementMenu = elementMenuMapper.toEntity(returnedElementMenuDTO);
        assertElementMenuUpdatableFieldsEquals(returnedElementMenu, getPersistedElementMenu(returnedElementMenu));

        insertedElementMenu = returnedElementMenu;
    }

    @Test
    @Transactional
    void createElementMenuWithExistingId() throws Exception {
        // Create the ElementMenu with an existing ID
        elementMenu.setId(1);
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elementMenuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElementMenu in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMasterIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        elementMenu.setMasterId(null);

        // Create the ElementMenu, which fails.
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        restElementMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elementMenuDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        elementMenu.setNom(null);

        // Create the ElementMenu, which fails.
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        restElementMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elementMenuDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFamilyGroupRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        elementMenu.setFamilyGroupRef(null);

        // Create the ElementMenu, which fails.
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        restElementMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elementMenuDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMenuRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        elementMenu.setMenuRef(null);

        // Create the ElementMenu, which fails.
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        restElementMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elementMenuDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllElementMenus() throws Exception {
        // Initialize the database
        insertedElementMenu = elementMenuRepository.saveAndFlush(elementMenu);

        // Get all the elementMenuList
        restElementMenuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].masterId").value(hasItem(DEFAULT_MASTER_ID)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].familyGroupRef").value(hasItem(DEFAULT_FAMILY_GROUP_REF)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)))
            .andExpect(jsonPath("$.[*].menuRef").value(hasItem(DEFAULT_MENU_REF)));
    }

    @Test
    @Transactional
    void getElementMenu() throws Exception {
        // Initialize the database
        insertedElementMenu = elementMenuRepository.saveAndFlush(elementMenu);

        // Get the elementMenu
        restElementMenuMockMvc
            .perform(get(ENTITY_API_URL_ID, elementMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementMenu.getId().intValue()))
            .andExpect(jsonPath("$.masterId").value(DEFAULT_MASTER_ID))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.familyGroupRef").value(DEFAULT_FAMILY_GROUP_REF))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX))
            .andExpect(jsonPath("$.menuRef").value(DEFAULT_MENU_REF));
    }

    @Test
    @Transactional
    void getNonExistingElementMenu() throws Exception {
        // Get the elementMenu
        restElementMenuMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingElementMenu() throws Exception {
        // Initialize the database
        insertedElementMenu = elementMenuRepository.saveAndFlush(elementMenu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the elementMenu
        ElementMenu updatedElementMenu = elementMenuRepository.findById(elementMenu.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedElementMenu are not directly saved in db
        em.detach(updatedElementMenu);
        updatedElementMenu
            .masterId(UPDATED_MASTER_ID)
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .familyGroupRef(UPDATED_FAMILY_GROUP_REF)
            .prix(UPDATED_PRIX)
            .menuRef(UPDATED_MENU_REF);
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(updatedElementMenu);

        restElementMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, elementMenuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(elementMenuDTO))
            )
            .andExpect(status().isOk());

        // Validate the ElementMenu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedElementMenuToMatchAllProperties(updatedElementMenu);
    }

    @Test
    @Transactional
    void putNonExistingElementMenu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elementMenu.setId(intCount.incrementAndGet());

        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, elementMenuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(elementMenuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementMenu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchElementMenu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elementMenu.setId(intCount.incrementAndGet());

        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(elementMenuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementMenu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamElementMenu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elementMenu.setId(intCount.incrementAndGet());

        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementMenuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elementMenuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementMenu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateElementMenuWithPatch() throws Exception {
        // Initialize the database
        insertedElementMenu = elementMenuRepository.saveAndFlush(elementMenu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the elementMenu using partial update
        ElementMenu partialUpdatedElementMenu = new ElementMenu();
        partialUpdatedElementMenu.setId(elementMenu.getId());

        partialUpdatedElementMenu.masterId(UPDATED_MASTER_ID).familyGroupRef(UPDATED_FAMILY_GROUP_REF);

        restElementMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementMenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedElementMenu))
            )
            .andExpect(status().isOk());

        // Validate the ElementMenu in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertElementMenuUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedElementMenu, elementMenu),
            getPersistedElementMenu(elementMenu)
        );
    }

    @Test
    @Transactional
    void fullUpdateElementMenuWithPatch() throws Exception {
        // Initialize the database
        insertedElementMenu = elementMenuRepository.saveAndFlush(elementMenu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the elementMenu using partial update
        ElementMenu partialUpdatedElementMenu = new ElementMenu();
        partialUpdatedElementMenu.setId(elementMenu.getId());

        partialUpdatedElementMenu
            .masterId(UPDATED_MASTER_ID)
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .familyGroupRef(UPDATED_FAMILY_GROUP_REF)
            .prix(UPDATED_PRIX)
            .menuRef(UPDATED_MENU_REF);

        restElementMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementMenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedElementMenu))
            )
            .andExpect(status().isOk());

        // Validate the ElementMenu in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertElementMenuUpdatableFieldsEquals(partialUpdatedElementMenu, getPersistedElementMenu(partialUpdatedElementMenu));
    }

    @Test
    @Transactional
    void patchNonExistingElementMenu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elementMenu.setId(intCount.incrementAndGet());

        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, elementMenuDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(elementMenuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementMenu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchElementMenu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elementMenu.setId(intCount.incrementAndGet());

        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(elementMenuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementMenu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamElementMenu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elementMenu.setId(intCount.incrementAndGet());

        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementMenuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(elementMenuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementMenu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteElementMenu() throws Exception {
        // Initialize the database
        insertedElementMenu = elementMenuRepository.saveAndFlush(elementMenu);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the elementMenu
        restElementMenuMockMvc
            .perform(delete(ENTITY_API_URL_ID, elementMenu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return elementMenuRepository.count();
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

    protected ElementMenu getPersistedElementMenu(ElementMenu elementMenu) {
        return elementMenuRepository.findById(elementMenu.getId()).orElseThrow();
    }

    protected void assertPersistedElementMenuToMatchAllProperties(ElementMenu expectedElementMenu) {
        assertElementMenuAllPropertiesEquals(expectedElementMenu, getPersistedElementMenu(expectedElementMenu));
    }

    protected void assertPersistedElementMenuToMatchUpdatableProperties(ElementMenu expectedElementMenu) {
        assertElementMenuAllUpdatablePropertiesEquals(expectedElementMenu, getPersistedElementMenu(expectedElementMenu));
    }
}
