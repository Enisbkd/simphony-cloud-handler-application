package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.MajorGroupRepository;
import mc.sbm.simphonycloud.service.MajorGroupService;
import mc.sbm.simphonycloud.service.dto.MajorGroupDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.MajorGroup}.
 */
@RestController
@RequestMapping("/api/major-groups")
public class MajorGroupResource {

    private static final Logger LOG = LoggerFactory.getLogger(MajorGroupResource.class);

    private static final String ENTITY_NAME = "majorGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MajorGroupService majorGroupService;

    private final MajorGroupRepository majorGroupRepository;

    public MajorGroupResource(MajorGroupService majorGroupService, MajorGroupRepository majorGroupRepository) {
        this.majorGroupService = majorGroupService;
        this.majorGroupRepository = majorGroupRepository;
    }

    /**
     * {@code POST  /major-groups} : Create a new majorGroup.
     *
     * @param majorGroupDTO the majorGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new majorGroupDTO, or with status {@code 400 (Bad Request)} if the majorGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MajorGroupDTO> createMajorGroup(@Valid @RequestBody MajorGroupDTO majorGroupDTO) throws URISyntaxException {
        LOG.debug("REST request to save MajorGroup : {}", majorGroupDTO);
        if (majorGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new majorGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        majorGroupDTO = majorGroupService.save(majorGroupDTO);
        return ResponseEntity.created(new URI("/api/major-groups/" + majorGroupDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, majorGroupDTO.getId().toString()))
            .body(majorGroupDTO);
    }

    /**
     * {@code PUT  /major-groups/:id} : Updates an existing majorGroup.
     *
     * @param id the id of the majorGroupDTO to save.
     * @param majorGroupDTO the majorGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorGroupDTO,
     * or with status {@code 400 (Bad Request)} if the majorGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the majorGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MajorGroupDTO> updateMajorGroup(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody MajorGroupDTO majorGroupDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MajorGroup : {}, {}", id, majorGroupDTO);
        if (majorGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, majorGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!majorGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        majorGroupDTO = majorGroupService.update(majorGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, majorGroupDTO.getId().toString()))
            .body(majorGroupDTO);
    }

    /**
     * {@code PATCH  /major-groups/:id} : Partial updates given fields of an existing majorGroup, field will ignore if it is null
     *
     * @param id the id of the majorGroupDTO to save.
     * @param majorGroupDTO the majorGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorGroupDTO,
     * or with status {@code 400 (Bad Request)} if the majorGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the majorGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the majorGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MajorGroupDTO> partialUpdateMajorGroup(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody MajorGroupDTO majorGroupDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MajorGroup partially : {}, {}", id, majorGroupDTO);
        if (majorGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, majorGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!majorGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MajorGroupDTO> result = majorGroupService.partialUpdate(majorGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, majorGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /major-groups} : get all the majorGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of majorGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MajorGroupDTO>> getAllMajorGroups(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MajorGroups");
        Page<MajorGroupDTO> page = majorGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /major-groups/:id} : get the "id" majorGroup.
     *
     * @param id the id of the majorGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the majorGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MajorGroupDTO> getMajorGroup(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get MajorGroup : {}", id);
        Optional<MajorGroupDTO> majorGroupDTO = majorGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(majorGroupDTO);
    }

    /**
     * {@code DELETE  /major-groups/:id} : delete the "id" majorGroup.
     *
     * @param id the id of the majorGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajorGroup(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete MajorGroup : {}", id);
        majorGroupService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
