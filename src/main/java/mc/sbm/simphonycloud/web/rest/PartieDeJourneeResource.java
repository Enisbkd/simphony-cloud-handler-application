package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.PartieDeJourneeRepository;
import mc.sbm.simphonycloud.service.PartieDeJourneeService;
import mc.sbm.simphonycloud.service.dto.PartieDeJourneeDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.PartieDeJournee}.
 */
@RestController
@RequestMapping("/api/partie-de-journees")
public class PartieDeJourneeResource {

    private static final Logger LOG = LoggerFactory.getLogger(PartieDeJourneeResource.class);

    private static final String ENTITY_NAME = "partieDeJournee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartieDeJourneeService partieDeJourneeService;

    private final PartieDeJourneeRepository partieDeJourneeRepository;

    public PartieDeJourneeResource(PartieDeJourneeService partieDeJourneeService, PartieDeJourneeRepository partieDeJourneeRepository) {
        this.partieDeJourneeService = partieDeJourneeService;
        this.partieDeJourneeRepository = partieDeJourneeRepository;
    }

    /**
     * {@code POST  /partie-de-journees} : Create a new partieDeJournee.
     *
     * @param partieDeJourneeDTO the partieDeJourneeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partieDeJourneeDTO, or with status {@code 400 (Bad Request)} if the partieDeJournee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PartieDeJourneeDTO> createPartieDeJournee(@Valid @RequestBody PartieDeJourneeDTO partieDeJourneeDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PartieDeJournee : {}", partieDeJourneeDTO);
        if (partieDeJourneeDTO.getId() != null) {
            throw new BadRequestAlertException("A new partieDeJournee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        partieDeJourneeDTO = partieDeJourneeService.save(partieDeJourneeDTO);
        return ResponseEntity.created(new URI("/api/partie-de-journees/" + partieDeJourneeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, partieDeJourneeDTO.getId().toString()))
            .body(partieDeJourneeDTO);
    }

    /**
     * {@code PUT  /partie-de-journees/:id} : Updates an existing partieDeJournee.
     *
     * @param id the id of the partieDeJourneeDTO to save.
     * @param partieDeJourneeDTO the partieDeJourneeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partieDeJourneeDTO,
     * or with status {@code 400 (Bad Request)} if the partieDeJourneeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partieDeJourneeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartieDeJourneeDTO> updatePartieDeJournee(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody PartieDeJourneeDTO partieDeJourneeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PartieDeJournee : {}, {}", id, partieDeJourneeDTO);
        if (partieDeJourneeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partieDeJourneeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partieDeJourneeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partieDeJourneeDTO = partieDeJourneeService.update(partieDeJourneeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partieDeJourneeDTO.getId().toString()))
            .body(partieDeJourneeDTO);
    }

    /**
     * {@code PATCH  /partie-de-journees/:id} : Partial updates given fields of an existing partieDeJournee, field will ignore if it is null
     *
     * @param id the id of the partieDeJourneeDTO to save.
     * @param partieDeJourneeDTO the partieDeJourneeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partieDeJourneeDTO,
     * or with status {@code 400 (Bad Request)} if the partieDeJourneeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the partieDeJourneeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the partieDeJourneeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartieDeJourneeDTO> partialUpdatePartieDeJournee(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody PartieDeJourneeDTO partieDeJourneeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PartieDeJournee partially : {}, {}", id, partieDeJourneeDTO);
        if (partieDeJourneeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partieDeJourneeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partieDeJourneeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartieDeJourneeDTO> result = partieDeJourneeService.partialUpdate(partieDeJourneeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partieDeJourneeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /partie-de-journees} : get all the partieDeJournees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partieDeJournees in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PartieDeJourneeDTO>> getAllPartieDeJournees(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PartieDeJournees");
        Page<PartieDeJourneeDTO> page = partieDeJourneeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partie-de-journees/:id} : get the "id" partieDeJournee.
     *
     * @param id the id of the partieDeJourneeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partieDeJourneeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartieDeJourneeDTO> getPartieDeJournee(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get PartieDeJournee : {}", id);
        Optional<PartieDeJourneeDTO> partieDeJourneeDTO = partieDeJourneeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partieDeJourneeDTO);
    }

    /**
     * {@code DELETE  /partie-de-journees/:id} : delete the "id" partieDeJournee.
     *
     * @param id the id of the partieDeJourneeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartieDeJournee(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete PartieDeJournee : {}", id);
        partieDeJourneeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
