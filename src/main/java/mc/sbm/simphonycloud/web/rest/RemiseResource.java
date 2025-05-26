package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.RemiseRepository;
import mc.sbm.simphonycloud.service.RemiseService;
import mc.sbm.simphonycloud.service.dto.RemiseDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.Remise}.
 */
@RestController
@RequestMapping("/api/remises")
public class RemiseResource {

    private static final Logger LOG = LoggerFactory.getLogger(RemiseResource.class);

    private static final String ENTITY_NAME = "remise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemiseService remiseService;

    private final RemiseRepository remiseRepository;

    public RemiseResource(RemiseService remiseService, RemiseRepository remiseRepository) {
        this.remiseService = remiseService;
        this.remiseRepository = remiseRepository;
    }

    /**
     * {@code POST  /remises} : Create a new remise.
     *
     * @param remiseDTO the remiseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remiseDTO, or with status {@code 400 (Bad Request)} if the remise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RemiseDTO> createRemise(@Valid @RequestBody RemiseDTO remiseDTO) throws URISyntaxException {
        LOG.debug("REST request to save Remise : {}", remiseDTO);
        if (remiseDTO.getId() != null) {
            throw new BadRequestAlertException("A new remise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        remiseDTO = remiseService.save(remiseDTO);
        return ResponseEntity.created(new URI("/api/remises/" + remiseDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, remiseDTO.getId().toString()))
            .body(remiseDTO);
    }

    /**
     * {@code PUT  /remises/:id} : Updates an existing remise.
     *
     * @param id the id of the remiseDTO to save.
     * @param remiseDTO the remiseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remiseDTO,
     * or with status {@code 400 (Bad Request)} if the remiseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remiseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RemiseDTO> updateRemise(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody RemiseDTO remiseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Remise : {}, {}", id, remiseDTO);
        if (remiseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remiseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        remiseDTO = remiseService.update(remiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, remiseDTO.getId().toString()))
            .body(remiseDTO);
    }

    /**
     * {@code PATCH  /remises/:id} : Partial updates given fields of an existing remise, field will ignore if it is null
     *
     * @param id the id of the remiseDTO to save.
     * @param remiseDTO the remiseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remiseDTO,
     * or with status {@code 400 (Bad Request)} if the remiseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the remiseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the remiseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RemiseDTO> partialUpdateRemise(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody RemiseDTO remiseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Remise partially : {}, {}", id, remiseDTO);
        if (remiseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remiseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RemiseDTO> result = remiseService.partialUpdate(remiseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, remiseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /remises} : get all the remises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remises in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RemiseDTO>> getAllRemises(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Remises");
        Page<RemiseDTO> page = remiseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remises/:id} : get the "id" remise.
     *
     * @param id the id of the remiseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remiseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RemiseDTO> getRemise(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get Remise : {}", id);
        Optional<RemiseDTO> remiseDTO = remiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remiseDTO);
    }

    /**
     * {@code DELETE  /remises/:id} : delete the "id" remise.
     *
     * @param id the id of the remiseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemise(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete Remise : {}", id);
        remiseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
