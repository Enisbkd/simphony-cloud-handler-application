package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.ModePaiementRepository;
import mc.sbm.simphonycloud.service.ModePaiementService;
import mc.sbm.simphonycloud.service.dto.ModePaiementDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.ModePaiement}.
 */
@RestController
@RequestMapping("/api/mode-paiements")
public class ModePaiementResource {

    private static final Logger LOG = LoggerFactory.getLogger(ModePaiementResource.class);

    private static final String ENTITY_NAME = "modePaiement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModePaiementService modePaiementService;

    private final ModePaiementRepository modePaiementRepository;

    public ModePaiementResource(ModePaiementService modePaiementService, ModePaiementRepository modePaiementRepository) {
        this.modePaiementService = modePaiementService;
        this.modePaiementRepository = modePaiementRepository;
    }

    /**
     * {@code POST  /mode-paiements} : Create a new modePaiement.
     *
     * @param modePaiementDTO the modePaiementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modePaiementDTO, or with status {@code 400 (Bad Request)} if the modePaiement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ModePaiementDTO> createModePaiement(@Valid @RequestBody ModePaiementDTO modePaiementDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ModePaiement : {}", modePaiementDTO);
        if (modePaiementDTO.getId() != null) {
            throw new BadRequestAlertException("A new modePaiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        modePaiementDTO = modePaiementService.save(modePaiementDTO);
        return ResponseEntity.created(new URI("/api/mode-paiements/" + modePaiementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, modePaiementDTO.getId().toString()))
            .body(modePaiementDTO);
    }

    /**
     * {@code PUT  /mode-paiements/:id} : Updates an existing modePaiement.
     *
     * @param id the id of the modePaiementDTO to save.
     * @param modePaiementDTO the modePaiementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePaiementDTO,
     * or with status {@code 400 (Bad Request)} if the modePaiementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modePaiementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModePaiementDTO> updateModePaiement(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody ModePaiementDTO modePaiementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ModePaiement : {}, {}", id, modePaiementDTO);
        if (modePaiementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePaiementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modePaiementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        modePaiementDTO = modePaiementService.update(modePaiementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, modePaiementDTO.getId().toString()))
            .body(modePaiementDTO);
    }

    /**
     * {@code PATCH  /mode-paiements/:id} : Partial updates given fields of an existing modePaiement, field will ignore if it is null
     *
     * @param id the id of the modePaiementDTO to save.
     * @param modePaiementDTO the modePaiementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePaiementDTO,
     * or with status {@code 400 (Bad Request)} if the modePaiementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modePaiementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modePaiementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModePaiementDTO> partialUpdateModePaiement(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody ModePaiementDTO modePaiementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ModePaiement partially : {}, {}", id, modePaiementDTO);
        if (modePaiementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePaiementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modePaiementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModePaiementDTO> result = modePaiementService.partialUpdate(modePaiementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, modePaiementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mode-paiements} : get all the modePaiements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modePaiements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ModePaiementDTO>> getAllModePaiements(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of ModePaiements");
        Page<ModePaiementDTO> page = modePaiementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mode-paiements/:id} : get the "id" modePaiement.
     *
     * @param id the id of the modePaiementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modePaiementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModePaiementDTO> getModePaiement(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get ModePaiement : {}", id);
        Optional<ModePaiementDTO> modePaiementDTO = modePaiementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modePaiementDTO);
    }

    /**
     * {@code DELETE  /mode-paiements/:id} : delete the "id" modePaiement.
     *
     * @param id the id of the modePaiementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModePaiement(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete ModePaiement : {}", id);
        modePaiementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
