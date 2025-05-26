package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.FactureDetailRepository;
import mc.sbm.simphonycloud.service.FactureDetailService;
import mc.sbm.simphonycloud.service.dto.FactureDetailDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.FactureDetail}.
 */
@RestController
@RequestMapping("/api/facture-details")
public class FactureDetailResource {

    private static final Logger LOG = LoggerFactory.getLogger(FactureDetailResource.class);

    private static final String ENTITY_NAME = "factureDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactureDetailService factureDetailService;

    private final FactureDetailRepository factureDetailRepository;

    public FactureDetailResource(FactureDetailService factureDetailService, FactureDetailRepository factureDetailRepository) {
        this.factureDetailService = factureDetailService;
        this.factureDetailRepository = factureDetailRepository;
    }

    /**
     * {@code POST  /facture-details} : Create a new factureDetail.
     *
     * @param factureDetailDTO the factureDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factureDetailDTO, or with status {@code 400 (Bad Request)} if the factureDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FactureDetailDTO> createFactureDetail(@Valid @RequestBody FactureDetailDTO factureDetailDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save FactureDetail : {}", factureDetailDTO);
        if (factureDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new factureDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        factureDetailDTO = factureDetailService.save(factureDetailDTO);
        return ResponseEntity.created(new URI("/api/facture-details/" + factureDetailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, factureDetailDTO.getId().toString()))
            .body(factureDetailDTO);
    }

    /**
     * {@code PUT  /facture-details/:id} : Updates an existing factureDetail.
     *
     * @param id the id of the factureDetailDTO to save.
     * @param factureDetailDTO the factureDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureDetailDTO,
     * or with status {@code 400 (Bad Request)} if the factureDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factureDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FactureDetailDTO> updateFactureDetail(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody FactureDetailDTO factureDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FactureDetail : {}, {}", id, factureDetailDTO);
        if (factureDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        factureDetailDTO = factureDetailService.update(factureDetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factureDetailDTO.getId().toString()))
            .body(factureDetailDTO);
    }

    /**
     * {@code PATCH  /facture-details/:id} : Partial updates given fields of an existing factureDetail, field will ignore if it is null
     *
     * @param id the id of the factureDetailDTO to save.
     * @param factureDetailDTO the factureDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureDetailDTO,
     * or with status {@code 400 (Bad Request)} if the factureDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the factureDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the factureDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FactureDetailDTO> partialUpdateFactureDetail(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody FactureDetailDTO factureDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FactureDetail partially : {}, {}", id, factureDetailDTO);
        if (factureDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactureDetailDTO> result = factureDetailService.partialUpdate(factureDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factureDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /facture-details} : get all the factureDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factureDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FactureDetailDTO>> getAllFactureDetails(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of FactureDetails");
        Page<FactureDetailDTO> page = factureDetailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facture-details/:id} : get the "id" factureDetail.
     *
     * @param id the id of the factureDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factureDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FactureDetailDTO> getFactureDetail(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get FactureDetail : {}", id);
        Optional<FactureDetailDTO> factureDetailDTO = factureDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factureDetailDTO);
    }

    /**
     * {@code DELETE  /facture-details/:id} : delete the "id" factureDetail.
     *
     * @param id the id of the factureDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactureDetail(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete FactureDetail : {}", id);
        factureDetailService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
