package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.EtablissementRepository;
import mc.sbm.simphonycloud.service.EtablissementService;
import mc.sbm.simphonycloud.service.dto.EtablissementDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.Etablissement}.
 */
@RestController
@RequestMapping("/api/etablissements")
public class EtablissementResource {

    private static final Logger LOG = LoggerFactory.getLogger(EtablissementResource.class);

    private static final String ENTITY_NAME = "etablissement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtablissementService etablissementService;

    private final EtablissementRepository etablissementRepository;

    public EtablissementResource(EtablissementService etablissementService, EtablissementRepository etablissementRepository) {
        this.etablissementService = etablissementService;
        this.etablissementRepository = etablissementRepository;
    }

    /**
     * {@code POST  /etablissements} : Create a new etablissement.
     *
     * @param etablissementDTO the etablissementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etablissementDTO, or with status {@code 400 (Bad Request)} if the etablissement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EtablissementDTO> createEtablissement(@Valid @RequestBody EtablissementDTO etablissementDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Etablissement : {}", etablissementDTO);
        if (etablissementDTO.getId() != null) {
            throw new BadRequestAlertException("A new etablissement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        etablissementDTO = etablissementService.save(etablissementDTO);
        return ResponseEntity.created(new URI("/api/etablissements/" + etablissementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, etablissementDTO.getId()))
            .body(etablissementDTO);
    }

    /**
     * {@code PUT  /etablissements/:id} : Updates an existing etablissement.
     *
     * @param id the id of the etablissementDTO to save.
     * @param etablissementDTO the etablissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablissementDTO,
     * or with status {@code 400 (Bad Request)} if the etablissementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etablissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EtablissementDTO> updateEtablissement(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody EtablissementDTO etablissementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Etablissement : {}, {}", id, etablissementDTO);
        if (etablissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etablissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etablissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        etablissementDTO = etablissementService.update(etablissementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, etablissementDTO.getId()))
            .body(etablissementDTO);
    }

    /**
     * {@code PATCH  /etablissements/:id} : Partial updates given fields of an existing etablissement, field will ignore if it is null
     *
     * @param id the id of the etablissementDTO to save.
     * @param etablissementDTO the etablissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablissementDTO,
     * or with status {@code 400 (Bad Request)} if the etablissementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the etablissementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the etablissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtablissementDTO> partialUpdateEtablissement(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody EtablissementDTO etablissementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Etablissement partially : {}, {}", id, etablissementDTO);
        if (etablissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etablissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etablissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtablissementDTO> result = etablissementService.partialUpdate(etablissementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, etablissementDTO.getId())
        );
    }

    /**
     * {@code GET  /etablissements} : get all the etablissements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etablissements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EtablissementDTO>> getAllEtablissements(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Etablissements");
        Page<EtablissementDTO> page = etablissementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etablissements/:id} : get the "id" etablissement.
     *
     * @param id the id of the etablissementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etablissementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EtablissementDTO> getEtablissement(@PathVariable("id") String id) {
        LOG.debug("REST request to get Etablissement : {}", id);
        Optional<EtablissementDTO> etablissementDTO = etablissementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etablissementDTO);
    }

    /**
     * {@code DELETE  /etablissements/:id} : delete the "id" etablissement.
     *
     * @param id the id of the etablissementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtablissement(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Etablissement : {}", id);
        etablissementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
