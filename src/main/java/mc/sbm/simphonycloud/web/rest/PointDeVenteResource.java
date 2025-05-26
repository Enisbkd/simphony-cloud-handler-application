package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.PointDeVenteRepository;
import mc.sbm.simphonycloud.service.PointDeVenteService;
import mc.sbm.simphonycloud.service.dto.PointDeVenteDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.PointDeVente}.
 */
@RestController
@RequestMapping("/api/point-de-ventes")
public class PointDeVenteResource {

    private static final Logger LOG = LoggerFactory.getLogger(PointDeVenteResource.class);

    private static final String ENTITY_NAME = "pointDeVente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointDeVenteService pointDeVenteService;

    private final PointDeVenteRepository pointDeVenteRepository;

    public PointDeVenteResource(PointDeVenteService pointDeVenteService, PointDeVenteRepository pointDeVenteRepository) {
        this.pointDeVenteService = pointDeVenteService;
        this.pointDeVenteRepository = pointDeVenteRepository;
    }

    /**
     * {@code POST  /point-de-ventes} : Create a new pointDeVente.
     *
     * @param pointDeVenteDTO the pointDeVenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointDeVenteDTO, or with status {@code 400 (Bad Request)} if the pointDeVente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PointDeVenteDTO> createPointDeVente(@Valid @RequestBody PointDeVenteDTO pointDeVenteDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PointDeVente : {}", pointDeVenteDTO);
        if (pointDeVenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new pointDeVente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pointDeVenteDTO = pointDeVenteService.save(pointDeVenteDTO);
        return ResponseEntity.created(new URI("/api/point-de-ventes/" + pointDeVenteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, pointDeVenteDTO.getId().toString()))
            .body(pointDeVenteDTO);
    }

    /**
     * {@code PUT  /point-de-ventes/:id} : Updates an existing pointDeVente.
     *
     * @param id the id of the pointDeVenteDTO to save.
     * @param pointDeVenteDTO the pointDeVenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDeVenteDTO,
     * or with status {@code 400 (Bad Request)} if the pointDeVenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointDeVenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PointDeVenteDTO> updatePointDeVente(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody PointDeVenteDTO pointDeVenteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PointDeVente : {}, {}", id, pointDeVenteDTO);
        if (pointDeVenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointDeVenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointDeVenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pointDeVenteDTO = pointDeVenteService.update(pointDeVenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pointDeVenteDTO.getId().toString()))
            .body(pointDeVenteDTO);
    }

    /**
     * {@code PATCH  /point-de-ventes/:id} : Partial updates given fields of an existing pointDeVente, field will ignore if it is null
     *
     * @param id the id of the pointDeVenteDTO to save.
     * @param pointDeVenteDTO the pointDeVenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDeVenteDTO,
     * or with status {@code 400 (Bad Request)} if the pointDeVenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pointDeVenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointDeVenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PointDeVenteDTO> partialUpdatePointDeVente(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody PointDeVenteDTO pointDeVenteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PointDeVente partially : {}, {}", id, pointDeVenteDTO);
        if (pointDeVenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointDeVenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointDeVenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointDeVenteDTO> result = pointDeVenteService.partialUpdate(pointDeVenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pointDeVenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /point-de-ventes} : get all the pointDeVentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointDeVentes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PointDeVenteDTO>> getAllPointDeVentes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of PointDeVentes");
        Page<PointDeVenteDTO> page = pointDeVenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /point-de-ventes/:id} : get the "id" pointDeVente.
     *
     * @param id the id of the pointDeVenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointDeVenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PointDeVenteDTO> getPointDeVente(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get PointDeVente : {}", id);
        Optional<PointDeVenteDTO> pointDeVenteDTO = pointDeVenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointDeVenteDTO);
    }

    /**
     * {@code DELETE  /point-de-ventes/:id} : delete the "id" pointDeVente.
     *
     * @param id the id of the pointDeVenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePointDeVente(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete PointDeVente : {}", id);
        pointDeVenteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
