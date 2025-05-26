package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.CommissionServiceRepository;
import mc.sbm.simphonycloud.service.CommissionServiceService;
import mc.sbm.simphonycloud.service.dto.CommissionServiceDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.CommissionService}.
 */
@RestController
@RequestMapping("/api/commission-services")
public class CommissionServiceResource {

    private static final Logger LOG = LoggerFactory.getLogger(CommissionServiceResource.class);

    private static final String ENTITY_NAME = "commissionService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommissionServiceService commissionServiceService;

    private final CommissionServiceRepository commissionServiceRepository;

    public CommissionServiceResource(
        CommissionServiceService commissionServiceService,
        CommissionServiceRepository commissionServiceRepository
    ) {
        this.commissionServiceService = commissionServiceService;
        this.commissionServiceRepository = commissionServiceRepository;
    }

    /**
     * {@code POST  /commission-services} : Create a new commissionService.
     *
     * @param commissionServiceDTO the commissionServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commissionServiceDTO, or with status {@code 400 (Bad Request)} if the commissionService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CommissionServiceDTO> createCommissionService(@Valid @RequestBody CommissionServiceDTO commissionServiceDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CommissionService : {}", commissionServiceDTO);
        if (commissionServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new commissionService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        commissionServiceDTO = commissionServiceService.save(commissionServiceDTO);
        return ResponseEntity.created(new URI("/api/commission-services/" + commissionServiceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, commissionServiceDTO.getId().toString()))
            .body(commissionServiceDTO);
    }

    /**
     * {@code PUT  /commission-services/:id} : Updates an existing commissionService.
     *
     * @param id the id of the commissionServiceDTO to save.
     * @param commissionServiceDTO the commissionServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionServiceDTO,
     * or with status {@code 400 (Bad Request)} if the commissionServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commissionServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommissionServiceDTO> updateCommissionService(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody CommissionServiceDTO commissionServiceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CommissionService : {}, {}", id, commissionServiceDTO);
        if (commissionServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commissionServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commissionServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        commissionServiceDTO = commissionServiceService.update(commissionServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commissionServiceDTO.getId().toString()))
            .body(commissionServiceDTO);
    }

    /**
     * {@code PATCH  /commission-services/:id} : Partial updates given fields of an existing commissionService, field will ignore if it is null
     *
     * @param id the id of the commissionServiceDTO to save.
     * @param commissionServiceDTO the commissionServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionServiceDTO,
     * or with status {@code 400 (Bad Request)} if the commissionServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commissionServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commissionServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommissionServiceDTO> partialUpdateCommissionService(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody CommissionServiceDTO commissionServiceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CommissionService partially : {}, {}", id, commissionServiceDTO);
        if (commissionServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commissionServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commissionServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommissionServiceDTO> result = commissionServiceService.partialUpdate(commissionServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commissionServiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /commission-services} : get all the commissionServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commissionServices in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CommissionServiceDTO>> getAllCommissionServices(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of CommissionServices");
        Page<CommissionServiceDTO> page = commissionServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commission-services/:id} : get the "id" commissionService.
     *
     * @param id the id of the commissionServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commissionServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommissionServiceDTO> getCommissionService(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get CommissionService : {}", id);
        Optional<CommissionServiceDTO> commissionServiceDTO = commissionServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commissionServiceDTO);
    }

    /**
     * {@code DELETE  /commission-services/:id} : delete the "id" commissionService.
     *
     * @param id the id of the commissionServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommissionService(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete CommissionService : {}", id);
        commissionServiceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
