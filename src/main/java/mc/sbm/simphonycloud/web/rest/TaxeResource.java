package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.TaxeRepository;
import mc.sbm.simphonycloud.service.TaxeService;
import mc.sbm.simphonycloud.service.dto.TaxeDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.Taxe}.
 */
@RestController
@RequestMapping("/api/taxes")
public class TaxeResource {

    private static final Logger LOG = LoggerFactory.getLogger(TaxeResource.class);

    private static final String ENTITY_NAME = "taxe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxeService taxeService;

    private final TaxeRepository taxeRepository;

    public TaxeResource(TaxeService taxeService, TaxeRepository taxeRepository) {
        this.taxeService = taxeService;
        this.taxeRepository = taxeRepository;
    }

    /**
     * {@code POST  /taxes} : Create a new taxe.
     *
     * @param taxeDTO the taxeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxeDTO, or with status {@code 400 (Bad Request)} if the taxe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TaxeDTO> createTaxe(@Valid @RequestBody TaxeDTO taxeDTO) throws URISyntaxException {
        LOG.debug("REST request to save Taxe : {}", taxeDTO);
        if (taxeDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taxeDTO = taxeService.save(taxeDTO);
        return ResponseEntity.created(new URI("/api/taxes/" + taxeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, taxeDTO.getId().toString()))
            .body(taxeDTO);
    }

    /**
     * {@code PUT  /taxes/:id} : Updates an existing taxe.
     *
     * @param id the id of the taxeDTO to save.
     * @param taxeDTO the taxeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxeDTO,
     * or with status {@code 400 (Bad Request)} if the taxeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaxeDTO> updateTaxe(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody TaxeDTO taxeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Taxe : {}, {}", id, taxeDTO);
        if (taxeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        taxeDTO = taxeService.update(taxeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxeDTO.getId().toString()))
            .body(taxeDTO);
    }

    /**
     * {@code PATCH  /taxes/:id} : Partial updates given fields of an existing taxe, field will ignore if it is null
     *
     * @param id the id of the taxeDTO to save.
     * @param taxeDTO the taxeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxeDTO,
     * or with status {@code 400 (Bad Request)} if the taxeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taxeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxeDTO> partialUpdateTaxe(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody TaxeDTO taxeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Taxe partially : {}, {}", id, taxeDTO);
        if (taxeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxeDTO> result = taxeService.partialUpdate(taxeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taxes} : get all the taxes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TaxeDTO>> getAllTaxes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Taxes");
        Page<TaxeDTO> page = taxeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /taxes/:id} : get the "id" taxe.
     *
     * @param id the id of the taxeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaxeDTO> getTaxe(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get Taxe : {}", id);
        Optional<TaxeDTO> taxeDTO = taxeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxeDTO);
    }

    /**
     * {@code DELETE  /taxes/:id} : delete the "id" taxe.
     *
     * @param id the id of the taxeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxe(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete Taxe : {}", id);
        taxeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
