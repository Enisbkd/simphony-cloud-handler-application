package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.CategoriePointDeVenteRepository;
import mc.sbm.simphonycloud.service.CategoriePointDeVenteService;
import mc.sbm.simphonycloud.service.dto.CategoriePointDeVenteDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.CategoriePointDeVente}.
 */
@RestController
@RequestMapping("/api/categorie-point-de-ventes")
public class CategoriePointDeVenteResource {

    private static final Logger LOG = LoggerFactory.getLogger(CategoriePointDeVenteResource.class);

    private static final String ENTITY_NAME = "categoriePointDeVente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriePointDeVenteService categoriePointDeVenteService;

    private final CategoriePointDeVenteRepository categoriePointDeVenteRepository;

    public CategoriePointDeVenteResource(
        CategoriePointDeVenteService categoriePointDeVenteService,
        CategoriePointDeVenteRepository categoriePointDeVenteRepository
    ) {
        this.categoriePointDeVenteService = categoriePointDeVenteService;
        this.categoriePointDeVenteRepository = categoriePointDeVenteRepository;
    }

    /**
     * {@code POST  /categorie-point-de-ventes} : Create a new categoriePointDeVente.
     *
     * @param categoriePointDeVenteDTO the categoriePointDeVenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriePointDeVenteDTO, or with status {@code 400 (Bad Request)} if the categoriePointDeVente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CategoriePointDeVenteDTO> createCategoriePointDeVente(
        @Valid @RequestBody CategoriePointDeVenteDTO categoriePointDeVenteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save CategoriePointDeVente : {}", categoriePointDeVenteDTO);
        if (categoriePointDeVenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriePointDeVente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        categoriePointDeVenteDTO = categoriePointDeVenteService.save(categoriePointDeVenteDTO);
        return ResponseEntity.created(new URI("/api/categorie-point-de-ventes/" + categoriePointDeVenteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, categoriePointDeVenteDTO.getId().toString()))
            .body(categoriePointDeVenteDTO);
    }

    /**
     * {@code PUT  /categorie-point-de-ventes/:id} : Updates an existing categoriePointDeVente.
     *
     * @param id the id of the categoriePointDeVenteDTO to save.
     * @param categoriePointDeVenteDTO the categoriePointDeVenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriePointDeVenteDTO,
     * or with status {@code 400 (Bad Request)} if the categoriePointDeVenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriePointDeVenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriePointDeVenteDTO> updateCategoriePointDeVente(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody CategoriePointDeVenteDTO categoriePointDeVenteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CategoriePointDeVente : {}, {}", id, categoriePointDeVenteDTO);
        if (categoriePointDeVenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriePointDeVenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriePointDeVenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        categoriePointDeVenteDTO = categoriePointDeVenteService.update(categoriePointDeVenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoriePointDeVenteDTO.getId().toString()))
            .body(categoriePointDeVenteDTO);
    }

    /**
     * {@code PATCH  /categorie-point-de-ventes/:id} : Partial updates given fields of an existing categoriePointDeVente, field will ignore if it is null
     *
     * @param id the id of the categoriePointDeVenteDTO to save.
     * @param categoriePointDeVenteDTO the categoriePointDeVenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriePointDeVenteDTO,
     * or with status {@code 400 (Bad Request)} if the categoriePointDeVenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoriePointDeVenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoriePointDeVenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoriePointDeVenteDTO> partialUpdateCategoriePointDeVente(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody CategoriePointDeVenteDTO categoriePointDeVenteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CategoriePointDeVente partially : {}, {}", id, categoriePointDeVenteDTO);
        if (categoriePointDeVenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriePointDeVenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriePointDeVenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoriePointDeVenteDTO> result = categoriePointDeVenteService.partialUpdate(categoriePointDeVenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoriePointDeVenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categorie-point-de-ventes} : get all the categoriePointDeVentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriePointDeVentes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CategoriePointDeVenteDTO>> getAllCategoriePointDeVentes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of CategoriePointDeVentes");
        Page<CategoriePointDeVenteDTO> page = categoriePointDeVenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorie-point-de-ventes/:id} : get the "id" categoriePointDeVente.
     *
     * @param id the id of the categoriePointDeVenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriePointDeVenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriePointDeVenteDTO> getCategoriePointDeVente(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get CategoriePointDeVente : {}", id);
        Optional<CategoriePointDeVenteDTO> categoriePointDeVenteDTO = categoriePointDeVenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriePointDeVenteDTO);
    }

    /**
     * {@code DELETE  /categorie-point-de-ventes/:id} : delete the "id" categoriePointDeVente.
     *
     * @param id the id of the categoriePointDeVenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriePointDeVente(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete CategoriePointDeVente : {}", id);
        categoriePointDeVenteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
