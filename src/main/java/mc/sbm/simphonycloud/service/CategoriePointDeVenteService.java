package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.CategoriePointDeVente;
import mc.sbm.simphonycloud.repository.CategoriePointDeVenteRepository;
import mc.sbm.simphonycloud.service.dto.CategoriePointDeVenteDTO;
import mc.sbm.simphonycloud.service.mapper.CategoriePointDeVenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.CategoriePointDeVente}.
 */
@Service
@Transactional
public class CategoriePointDeVenteService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoriePointDeVenteService.class);

    private final CategoriePointDeVenteRepository categoriePointDeVenteRepository;

    private final CategoriePointDeVenteMapper categoriePointDeVenteMapper;

    public CategoriePointDeVenteService(
        CategoriePointDeVenteRepository categoriePointDeVenteRepository,
        CategoriePointDeVenteMapper categoriePointDeVenteMapper
    ) {
        this.categoriePointDeVenteRepository = categoriePointDeVenteRepository;
        this.categoriePointDeVenteMapper = categoriePointDeVenteMapper;
    }

    /**
     * Save a categoriePointDeVente.
     *
     * @param categoriePointDeVenteDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriePointDeVenteDTO save(CategoriePointDeVenteDTO categoriePointDeVenteDTO) {
        LOG.debug("Request to save CategoriePointDeVente : {}", categoriePointDeVenteDTO);
        CategoriePointDeVente categoriePointDeVente = categoriePointDeVenteMapper.toEntity(categoriePointDeVenteDTO);
        categoriePointDeVente = categoriePointDeVenteRepository.save(categoriePointDeVente);
        return categoriePointDeVenteMapper.toDto(categoriePointDeVente);
    }

    /**
     * Update a categoriePointDeVente.
     *
     * @param categoriePointDeVenteDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriePointDeVenteDTO update(CategoriePointDeVenteDTO categoriePointDeVenteDTO) {
        LOG.debug("Request to update CategoriePointDeVente : {}", categoriePointDeVenteDTO);
        CategoriePointDeVente categoriePointDeVente = categoriePointDeVenteMapper.toEntity(categoriePointDeVenteDTO);
        categoriePointDeVente = categoriePointDeVenteRepository.save(categoriePointDeVente);
        return categoriePointDeVenteMapper.toDto(categoriePointDeVente);
    }

    /**
     * Partially update a categoriePointDeVente.
     *
     * @param categoriePointDeVenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CategoriePointDeVenteDTO> partialUpdate(CategoriePointDeVenteDTO categoriePointDeVenteDTO) {
        LOG.debug("Request to partially update CategoriePointDeVente : {}", categoriePointDeVenteDTO);

        return categoriePointDeVenteRepository
            .findById(categoriePointDeVenteDTO.getId())
            .map(existingCategoriePointDeVente -> {
                categoriePointDeVenteMapper.partialUpdate(existingCategoriePointDeVente, categoriePointDeVenteDTO);

                return existingCategoriePointDeVente;
            })
            .map(categoriePointDeVenteRepository::save)
            .map(categoriePointDeVenteMapper::toDto);
    }

    /**
     * Get all the categoriePointDeVentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriePointDeVenteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CategoriePointDeVentes");
        return categoriePointDeVenteRepository.findAll(pageable).map(categoriePointDeVenteMapper::toDto);
    }

    /**
     * Get one categoriePointDeVente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriePointDeVenteDTO> findOne(Integer id) {
        LOG.debug("Request to get CategoriePointDeVente : {}", id);
        return categoriePointDeVenteRepository.findById(id).map(categoriePointDeVenteMapper::toDto);
    }

    /**
     * Delete the categoriePointDeVente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete CategoriePointDeVente : {}", id);
        categoriePointDeVenteRepository.deleteById(id);
    }
}
