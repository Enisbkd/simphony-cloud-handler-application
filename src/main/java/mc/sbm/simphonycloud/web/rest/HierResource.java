package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.HierRepository;
import mc.sbm.simphonycloud.service.HierService;
import mc.sbm.simphonycloud.service.dto.HierDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.Hier}.
 */
@RestController
@RequestMapping("/api/hiers")
public class HierResource {

    private static final Logger LOG = LoggerFactory.getLogger(HierResource.class);

    private static final String ENTITY_NAME = "hier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HierService hierService;

    private final HierRepository hierRepository;

    public HierResource(HierService hierService, HierRepository hierRepository) {
        this.hierService = hierService;
        this.hierRepository = hierRepository;
    }

    /**
     * {@code POST  /hiers} : Create a new hier.
     *
     * @param hierDTO the hierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hierDTO, or with status {@code 400 (Bad Request)} if the hier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HierDTO> createHier(@Valid @RequestBody HierDTO hierDTO) throws URISyntaxException {
        LOG.debug("REST request to save Hier : {}", hierDTO);
        if (hierDTO.getId() != null) {
            throw new BadRequestAlertException("A new hier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hierDTO = hierService.save(hierDTO);
        return ResponseEntity.created(new URI("/api/hiers/" + hierDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, hierDTO.getId()))
            .body(hierDTO);
    }

    /**
     * {@code PUT  /hiers/:id} : Updates an existing hier.
     *
     * @param id the id of the hierDTO to save.
     * @param hierDTO the hierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hierDTO,
     * or with status {@code 400 (Bad Request)} if the hierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HierDTO> updateHier(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody HierDTO hierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Hier : {}, {}", id, hierDTO);
        if (hierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hierDTO = hierService.update(hierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hierDTO.getId()))
            .body(hierDTO);
    }

    /**
     * {@code PATCH  /hiers/:id} : Partial updates given fields of an existing hier, field will ignore if it is null
     *
     * @param id the id of the hierDTO to save.
     * @param hierDTO the hierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hierDTO,
     * or with status {@code 400 (Bad Request)} if the hierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HierDTO> partialUpdateHier(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody HierDTO hierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Hier partially : {}, {}", id, hierDTO);
        if (hierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HierDTO> result = hierService.partialUpdate(hierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hierDTO.getId())
        );
    }

    /**
     * {@code GET  /hiers} : get all the hiers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hiers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HierDTO>> getAllHiers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Hiers");
        Page<HierDTO> page = hierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hiers/:id} : get the "id" hier.
     *
     * @param id the id of the hierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HierDTO> getHier(@PathVariable("id") String id) {
        LOG.debug("REST request to get Hier : {}", id);
        Optional<HierDTO> hierDTO = hierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hierDTO);
    }

    /**
     * {@code DELETE  /hiers/:id} : delete the "id" hier.
     *
     * @param id the id of the hierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHier(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Hier : {}", id);
        hierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
