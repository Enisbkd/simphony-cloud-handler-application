package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.SocieteRepository;
import mc.sbm.simphonycloud.service.SocieteService;
import mc.sbm.simphonycloud.service.dto.SocieteDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.Societe}.
 */
@RestController
@RequestMapping("/api/societes")
public class SocieteResource {

    private static final Logger LOG = LoggerFactory.getLogger(SocieteResource.class);

    private static final String ENTITY_NAME = "societe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocieteService societeService;

    private final SocieteRepository societeRepository;

    public SocieteResource(SocieteService societeService, SocieteRepository societeRepository) {
        this.societeService = societeService;
        this.societeRepository = societeRepository;
    }

    /**
     * {@code POST  /societes} : Create a new societe.
     *
     * @param societeDTO the societeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societeDTO, or with status {@code 400 (Bad Request)} if the societe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SocieteDTO> createSociete(@Valid @RequestBody SocieteDTO societeDTO) throws URISyntaxException {
        LOG.debug("REST request to save Societe : {}", societeDTO);
        if (societeDTO.getId() != null) {
            throw new BadRequestAlertException("A new societe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        societeDTO = societeService.save(societeDTO);
        return ResponseEntity.created(new URI("/api/societes/" + societeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, societeDTO.getId()))
            .body(societeDTO);
    }

    /**
     * {@code PUT  /societes/:id} : Updates an existing societe.
     *
     * @param id the id of the societeDTO to save.
     * @param societeDTO the societeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societeDTO,
     * or with status {@code 400 (Bad Request)} if the societeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SocieteDTO> updateSociete(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody SocieteDTO societeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Societe : {}, {}", id, societeDTO);
        if (societeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        societeDTO = societeService.update(societeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, societeDTO.getId()))
            .body(societeDTO);
    }

    /**
     * {@code PATCH  /societes/:id} : Partial updates given fields of an existing societe, field will ignore if it is null
     *
     * @param id the id of the societeDTO to save.
     * @param societeDTO the societeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societeDTO,
     * or with status {@code 400 (Bad Request)} if the societeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocieteDTO> partialUpdateSociete(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody SocieteDTO societeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Societe partially : {}, {}", id, societeDTO);
        if (societeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocieteDTO> result = societeService.partialUpdate(societeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, societeDTO.getId())
        );
    }

    /**
     * {@code GET  /societes} : get all the societes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SocieteDTO>> getAllSocietes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Societes");
        Page<SocieteDTO> page = societeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /societes/:id} : get the "id" societe.
     *
     * @param id the id of the societeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SocieteDTO> getSociete(@PathVariable("id") String id) {
        LOG.debug("REST request to get Societe : {}", id);
        Optional<SocieteDTO> societeDTO = societeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societeDTO);
    }

    /**
     * {@code DELETE  /societes/:id} : delete the "id" societe.
     *
     * @param id the id of the societeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSociete(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Societe : {}", id);
        societeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
