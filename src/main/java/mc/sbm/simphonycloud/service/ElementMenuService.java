package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.ElementMenu;
import mc.sbm.simphonycloud.repository.ElementMenuRepository;
import mc.sbm.simphonycloud.service.dto.ElementMenuDTO;
import mc.sbm.simphonycloud.service.mapper.ElementMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.ElementMenu}.
 */
@Service
@Transactional
public class ElementMenuService {

    private static final Logger LOG = LoggerFactory.getLogger(ElementMenuService.class);

    private final ElementMenuRepository elementMenuRepository;

    private final ElementMenuMapper elementMenuMapper;

    public ElementMenuService(ElementMenuRepository elementMenuRepository, ElementMenuMapper elementMenuMapper) {
        this.elementMenuRepository = elementMenuRepository;
        this.elementMenuMapper = elementMenuMapper;
    }

    /**
     * Save a elementMenu.
     *
     * @param elementMenuDTO the entity to save.
     * @return the persisted entity.
     */
    public ElementMenuDTO save(ElementMenuDTO elementMenuDTO) {
        LOG.debug("Request to save ElementMenu : {}", elementMenuDTO);
        ElementMenu elementMenu = elementMenuMapper.toEntity(elementMenuDTO);
        elementMenu = elementMenuRepository.save(elementMenu);
        return elementMenuMapper.toDto(elementMenu);
    }

    /**
     * Update a elementMenu.
     *
     * @param elementMenuDTO the entity to save.
     * @return the persisted entity.
     */
    public ElementMenuDTO update(ElementMenuDTO elementMenuDTO) {
        LOG.debug("Request to update ElementMenu : {}", elementMenuDTO);
        ElementMenu elementMenu = elementMenuMapper.toEntity(elementMenuDTO);
        elementMenu = elementMenuRepository.save(elementMenu);
        return elementMenuMapper.toDto(elementMenu);
    }

    /**
     * Partially update a elementMenu.
     *
     * @param elementMenuDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ElementMenuDTO> partialUpdate(ElementMenuDTO elementMenuDTO) {
        LOG.debug("Request to partially update ElementMenu : {}", elementMenuDTO);

        return elementMenuRepository
            .findById(elementMenuDTO.getId())
            .map(existingElementMenu -> {
                elementMenuMapper.partialUpdate(existingElementMenu, elementMenuDTO);

                return existingElementMenu;
            })
            .map(elementMenuRepository::save)
            .map(elementMenuMapper::toDto);
    }

    /**
     * Get all the elementMenus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ElementMenuDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ElementMenus");
        return elementMenuRepository.findAll(pageable).map(elementMenuMapper::toDto);
    }

    /**
     * Get one elementMenu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ElementMenuDTO> findOne(Integer id) {
        LOG.debug("Request to get ElementMenu : {}", id);
        return elementMenuRepository.findById(id).map(elementMenuMapper::toDto);
    }

    /**
     * Delete the elementMenu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete ElementMenu : {}", id);
        elementMenuRepository.deleteById(id);
    }
}
