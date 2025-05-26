package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.CodeRaisonRepository;
import mc.sbm.simphonycloud.service.CodeRaisonService;
import mc.sbm.simphonycloud.service.dto.CodeRaisonDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.CodeRaison}.
 */
@RestController
@RequestMapping("/api/code-raisons")
public class CodeRaisonResource {

    private static final Logger LOG = LoggerFactory.getLogger(CodeRaisonResource.class);

    private static final String ENTITY_NAME = "codeRaison";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodeRaisonService codeRaisonService;

    private final CodeRaisonRepository codeRaisonRepository;

    public CodeRaisonResource(CodeRaisonService codeRaisonService, CodeRaisonRepository codeRaisonRepository) {
        this.codeRaisonService = codeRaisonService;
        this.codeRaisonRepository = codeRaisonRepository;
    }

    /**
     * {@code POST  /code-raisons} : Create a new codeRaison.
     *
     * @param codeRaisonDTO the codeRaisonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codeRaisonDTO, or with status {@code 400 (Bad Request)} if the codeRaison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CodeRaisonDTO> createCodeRaison(@Valid @RequestBody CodeRaisonDTO codeRaisonDTO) throws URISyntaxException {
        LOG.debug("REST request to save CodeRaison : {}", codeRaisonDTO);
        if (codeRaisonDTO.getId() != null) {
            throw new BadRequestAlertException("A new codeRaison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        codeRaisonDTO = codeRaisonService.save(codeRaisonDTO);
        return ResponseEntity.created(new URI("/api/code-raisons/" + codeRaisonDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, codeRaisonDTO.getId().toString()))
            .body(codeRaisonDTO);
    }

    /**
     * {@code PUT  /code-raisons/:id} : Updates an existing codeRaison.
     *
     * @param id the id of the codeRaisonDTO to save.
     * @param codeRaisonDTO the codeRaisonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeRaisonDTO,
     * or with status {@code 400 (Bad Request)} if the codeRaisonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codeRaisonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CodeRaisonDTO> updateCodeRaison(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody CodeRaisonDTO codeRaisonDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CodeRaison : {}, {}", id, codeRaisonDTO);
        if (codeRaisonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codeRaisonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeRaisonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        codeRaisonDTO = codeRaisonService.update(codeRaisonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, codeRaisonDTO.getId().toString()))
            .body(codeRaisonDTO);
    }

    /**
     * {@code PATCH  /code-raisons/:id} : Partial updates given fields of an existing codeRaison, field will ignore if it is null
     *
     * @param id the id of the codeRaisonDTO to save.
     * @param codeRaisonDTO the codeRaisonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeRaisonDTO,
     * or with status {@code 400 (Bad Request)} if the codeRaisonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the codeRaisonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the codeRaisonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CodeRaisonDTO> partialUpdateCodeRaison(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody CodeRaisonDTO codeRaisonDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CodeRaison partially : {}, {}", id, codeRaisonDTO);
        if (codeRaisonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codeRaisonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeRaisonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CodeRaisonDTO> result = codeRaisonService.partialUpdate(codeRaisonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, codeRaisonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /code-raisons} : get all the codeRaisons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codeRaisons in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CodeRaisonDTO>> getAllCodeRaisons(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of CodeRaisons");
        Page<CodeRaisonDTO> page = codeRaisonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /code-raisons/:id} : get the "id" codeRaison.
     *
     * @param id the id of the codeRaisonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codeRaisonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CodeRaisonDTO> getCodeRaison(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get CodeRaison : {}", id);
        Optional<CodeRaisonDTO> codeRaisonDTO = codeRaisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codeRaisonDTO);
    }

    /**
     * {@code DELETE  /code-raisons/:id} : delete the "id" codeRaison.
     *
     * @param id the id of the codeRaisonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCodeRaison(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete CodeRaison : {}", id);
        codeRaisonService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
