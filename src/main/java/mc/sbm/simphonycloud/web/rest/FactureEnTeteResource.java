package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.FactureEnTeteRepository;
import mc.sbm.simphonycloud.service.FactureEnTeteService;
import mc.sbm.simphonycloud.service.dto.FactureEnTeteDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.FactureEnTete}.
 */
@RestController
@RequestMapping("/api/facture-en-tetes")
public class FactureEnTeteResource {

    private static final Logger LOG = LoggerFactory.getLogger(FactureEnTeteResource.class);

    private static final String ENTITY_NAME = "factureEnTete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactureEnTeteService factureEnTeteService;

    private final FactureEnTeteRepository factureEnTeteRepository;

    public FactureEnTeteResource(FactureEnTeteService factureEnTeteService, FactureEnTeteRepository factureEnTeteRepository) {
        this.factureEnTeteService = factureEnTeteService;
        this.factureEnTeteRepository = factureEnTeteRepository;
    }

    /**
     * {@code POST  /facture-en-tetes} : Create a new factureEnTete.
     *
     * @param factureEnTeteDTO the factureEnTeteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factureEnTeteDTO, or with status {@code 400 (Bad Request)} if the factureEnTete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FactureEnTeteDTO> createFactureEnTete(@Valid @RequestBody FactureEnTeteDTO factureEnTeteDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save FactureEnTete : {}", factureEnTeteDTO);
        if (factureEnTeteDTO.getId() != null) {
            throw new BadRequestAlertException("A new factureEnTete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        factureEnTeteDTO = factureEnTeteService.save(factureEnTeteDTO);
        return ResponseEntity.created(new URI("/api/facture-en-tetes/" + factureEnTeteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, factureEnTeteDTO.getId().toString()))
            .body(factureEnTeteDTO);
    }

    /**
     * {@code PUT  /facture-en-tetes/:id} : Updates an existing factureEnTete.
     *
     * @param id the id of the factureEnTeteDTO to save.
     * @param factureEnTeteDTO the factureEnTeteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureEnTeteDTO,
     * or with status {@code 400 (Bad Request)} if the factureEnTeteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factureEnTeteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FactureEnTeteDTO> updateFactureEnTete(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody FactureEnTeteDTO factureEnTeteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FactureEnTete : {}, {}", id, factureEnTeteDTO);
        if (factureEnTeteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureEnTeteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureEnTeteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        factureEnTeteDTO = factureEnTeteService.update(factureEnTeteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factureEnTeteDTO.getId().toString()))
            .body(factureEnTeteDTO);
    }

    /**
     * {@code PATCH  /facture-en-tetes/:id} : Partial updates given fields of an existing factureEnTete, field will ignore if it is null
     *
     * @param id the id of the factureEnTeteDTO to save.
     * @param factureEnTeteDTO the factureEnTeteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureEnTeteDTO,
     * or with status {@code 400 (Bad Request)} if the factureEnTeteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the factureEnTeteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the factureEnTeteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FactureEnTeteDTO> partialUpdateFactureEnTete(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody FactureEnTeteDTO factureEnTeteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FactureEnTete partially : {}, {}", id, factureEnTeteDTO);
        if (factureEnTeteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureEnTeteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureEnTeteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactureEnTeteDTO> result = factureEnTeteService.partialUpdate(factureEnTeteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factureEnTeteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /facture-en-tetes} : get all the factureEnTetes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factureEnTetes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FactureEnTeteDTO>> getAllFactureEnTetes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of FactureEnTetes");
        Page<FactureEnTeteDTO> page = factureEnTeteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facture-en-tetes/:id} : get the "id" factureEnTete.
     *
     * @param id the id of the factureEnTeteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factureEnTeteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FactureEnTeteDTO> getFactureEnTete(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get FactureEnTete : {}", id);
        Optional<FactureEnTeteDTO> factureEnTeteDTO = factureEnTeteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factureEnTeteDTO);
    }

    /**
     * {@code DELETE  /facture-en-tetes/:id} : delete the "id" factureEnTete.
     *
     * @param id the id of the factureEnTeteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactureEnTete(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete FactureEnTete : {}", id);
        factureEnTeteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
