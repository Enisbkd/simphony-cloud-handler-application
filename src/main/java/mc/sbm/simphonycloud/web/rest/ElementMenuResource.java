package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.repository.ElementMenuRepository;
import mc.sbm.simphonycloud.service.ElementMenuService;
import mc.sbm.simphonycloud.service.dto.ElementMenuDTO;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.ElementMenu}.
 */
@RestController
@RequestMapping("/api/element-menus")
public class ElementMenuResource {

    private static final Logger LOG = LoggerFactory.getLogger(ElementMenuResource.class);

    private static final String ENTITY_NAME = "elementMenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementMenuService elementMenuService;

    private final ElementMenuRepository elementMenuRepository;

    public ElementMenuResource(ElementMenuService elementMenuService, ElementMenuRepository elementMenuRepository) {
        this.elementMenuService = elementMenuService;
        this.elementMenuRepository = elementMenuRepository;
    }

    /**
     * {@code POST  /element-menus} : Create a new elementMenu.
     *
     * @param elementMenuDTO the elementMenuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementMenuDTO, or with status {@code 400 (Bad Request)} if the elementMenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ElementMenuDTO> createElementMenu(@Valid @RequestBody ElementMenuDTO elementMenuDTO) throws URISyntaxException {
        LOG.debug("REST request to save ElementMenu : {}", elementMenuDTO);
        if (elementMenuDTO.getId() != null) {
            throw new BadRequestAlertException("A new elementMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        elementMenuDTO = elementMenuService.save(elementMenuDTO);
        return ResponseEntity.created(new URI("/api/element-menus/" + elementMenuDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, elementMenuDTO.getId().toString()))
            .body(elementMenuDTO);
    }

    /**
     * {@code PUT  /element-menus/:id} : Updates an existing elementMenu.
     *
     * @param id the id of the elementMenuDTO to save.
     * @param elementMenuDTO the elementMenuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementMenuDTO,
     * or with status {@code 400 (Bad Request)} if the elementMenuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementMenuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ElementMenuDTO> updateElementMenu(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody ElementMenuDTO elementMenuDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ElementMenu : {}, {}", id, elementMenuDTO);
        if (elementMenuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementMenuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementMenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        elementMenuDTO = elementMenuService.update(elementMenuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, elementMenuDTO.getId().toString()))
            .body(elementMenuDTO);
    }

    /**
     * {@code PATCH  /element-menus/:id} : Partial updates given fields of an existing elementMenu, field will ignore if it is null
     *
     * @param id the id of the elementMenuDTO to save.
     * @param elementMenuDTO the elementMenuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementMenuDTO,
     * or with status {@code 400 (Bad Request)} if the elementMenuDTO is not valid,
     * or with status {@code 404 (Not Found)} if the elementMenuDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the elementMenuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ElementMenuDTO> partialUpdateElementMenu(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody ElementMenuDTO elementMenuDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ElementMenu partially : {}, {}", id, elementMenuDTO);
        if (elementMenuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementMenuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementMenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ElementMenuDTO> result = elementMenuService.partialUpdate(elementMenuDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, elementMenuDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /element-menus} : get all the elementMenus.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementMenus in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ElementMenuDTO>> getAllElementMenus(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of ElementMenus");
        Page<ElementMenuDTO> page = elementMenuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /element-menus/:id} : get the "id" elementMenu.
     *
     * @param id the id of the elementMenuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementMenuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ElementMenuDTO> getElementMenu(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get ElementMenu : {}", id);
        Optional<ElementMenuDTO> elementMenuDTO = elementMenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elementMenuDTO);
    }

    /**
     * {@code DELETE  /element-menus/:id} : delete the "id" elementMenu.
     *
     * @param id the id of the elementMenuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElementMenu(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete ElementMenu : {}", id);
        elementMenuService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
