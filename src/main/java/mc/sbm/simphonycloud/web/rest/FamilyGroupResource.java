package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.FamilyGroupRepository;
import mc.sbm.simphonycloud.service.FamilyGroupService;
import mc.sbm.simphonycloud.service.dto.FamilyGroupDTO;
import mc.sbm.simphonycloud.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.FamilyGroup}.
 */
@RestController
@RequestMapping("/api/family-groups")
public class FamilyGroupResource {

    private static final Logger LOG = LoggerFactory.getLogger(FamilyGroupResource.class);

    private static final String ENTITY_NAME = "familyGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyGroupService familyGroupService;

    private final FamilyGroupRepository familyGroupRepository;

    public FamilyGroupResource(FamilyGroupService familyGroupService, FamilyGroupRepository familyGroupRepository) {
        this.familyGroupService = familyGroupService;
        this.familyGroupRepository = familyGroupRepository;
    }

    /**
     * {@code POST  /family-groups} : Create a new familyGroup.
     *
     * @param familyGroupDTO the familyGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyGroupDTO, or with status {@code 400 (Bad Request)} if the familyGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FamilyGroupDTO> createFamilyGroup(@Valid @RequestBody FamilyGroupDTO familyGroupDTO) throws URISyntaxException {
        LOG.debug("REST request to save FamilyGroup : {}", familyGroupDTO);
        if (familyGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new familyGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        familyGroupDTO = familyGroupService.save(familyGroupDTO);
        return ResponseEntity.created(new URI("/api/family-groups/" + familyGroupDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, familyGroupDTO.getId().toString()))
            .body(familyGroupDTO);
    }

    /**
     * {@code PUT  /family-groups/:id} : Updates an existing familyGroup.
     *
     * @param id the id of the familyGroupDTO to save.
     * @param familyGroupDTO the familyGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyGroupDTO,
     * or with status {@code 400 (Bad Request)} if the familyGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FamilyGroupDTO> updateFamilyGroup(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody FamilyGroupDTO familyGroupDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FamilyGroup : {}, {}", id, familyGroupDTO);
        if (familyGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familyGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familyGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        familyGroupDTO = familyGroupService.update(familyGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, familyGroupDTO.getId().toString()))
            .body(familyGroupDTO);
    }

    /**
     * {@code PATCH  /family-groups/:id} : Partial updates given fields of an existing familyGroup, field will ignore if it is null
     *
     * @param id the id of the familyGroupDTO to save.
     * @param familyGroupDTO the familyGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyGroupDTO,
     * or with status {@code 400 (Bad Request)} if the familyGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the familyGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the familyGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FamilyGroupDTO> partialUpdateFamilyGroup(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody FamilyGroupDTO familyGroupDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FamilyGroup partially : {}, {}", id, familyGroupDTO);
        if (familyGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familyGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familyGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FamilyGroupDTO> result = familyGroupService.partialUpdate(familyGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, familyGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /family-groups} : get all the familyGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FamilyGroupDTO>> getAllFamilyGroups(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of FamilyGroups");
        Page<FamilyGroupDTO> page = familyGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /family-groups/:id} : get the "id" familyGroup.
     *
     * @param id the id of the familyGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FamilyGroupDTO> getFamilyGroup(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get FamilyGroup : {}", id);
        Optional<FamilyGroupDTO> familyGroupDTO = familyGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyGroupDTO);
    }

    /**
     * {@code DELETE  /family-groups/:id} : delete the "id" familyGroup.
     *
     * @param id the id of the familyGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyGroup(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete FamilyGroup : {}", id);
        familyGroupService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
