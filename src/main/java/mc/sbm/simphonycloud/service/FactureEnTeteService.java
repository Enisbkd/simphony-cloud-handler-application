package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.FactureEnTete;
import mc.sbm.simphonycloud.repository.FactureEnTeteRepository;
import mc.sbm.simphonycloud.service.dto.FactureEnTeteDTO;
import mc.sbm.simphonycloud.service.mapper.FactureEnTeteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.FactureEnTete}.
 */
@Service
@Transactional
public class FactureEnTeteService {

    private static final Logger LOG = LoggerFactory.getLogger(FactureEnTeteService.class);

    private final FactureEnTeteRepository factureEnTeteRepository;

    private final FactureEnTeteMapper factureEnTeteMapper;

    public FactureEnTeteService(FactureEnTeteRepository factureEnTeteRepository, FactureEnTeteMapper factureEnTeteMapper) {
        this.factureEnTeteRepository = factureEnTeteRepository;
        this.factureEnTeteMapper = factureEnTeteMapper;
    }

    /**
     * Save a factureEnTete.
     *
     * @param factureEnTeteDTO the entity to save.
     * @return the persisted entity.
     */
    public FactureEnTeteDTO save(FactureEnTeteDTO factureEnTeteDTO) {
        LOG.debug("Request to save FactureEnTete : {}", factureEnTeteDTO);
        FactureEnTete factureEnTete = factureEnTeteMapper.toEntity(factureEnTeteDTO);
        factureEnTete = factureEnTeteRepository.save(factureEnTete);
        return factureEnTeteMapper.toDto(factureEnTete);
    }

    /**
     * Update a factureEnTete.
     *
     * @param factureEnTeteDTO the entity to save.
     * @return the persisted entity.
     */
    public FactureEnTeteDTO update(FactureEnTeteDTO factureEnTeteDTO) {
        LOG.debug("Request to update FactureEnTete : {}", factureEnTeteDTO);
        FactureEnTete factureEnTete = factureEnTeteMapper.toEntity(factureEnTeteDTO);
        factureEnTete = factureEnTeteRepository.save(factureEnTete);
        return factureEnTeteMapper.toDto(factureEnTete);
    }

    /**
     * Partially update a factureEnTete.
     *
     * @param factureEnTeteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FactureEnTeteDTO> partialUpdate(FactureEnTeteDTO factureEnTeteDTO) {
        LOG.debug("Request to partially update FactureEnTete : {}", factureEnTeteDTO);

        return factureEnTeteRepository
            .findById(factureEnTeteDTO.getId())
            .map(existingFactureEnTete -> {
                factureEnTeteMapper.partialUpdate(existingFactureEnTete, factureEnTeteDTO);

                return existingFactureEnTete;
            })
            .map(factureEnTeteRepository::save)
            .map(factureEnTeteMapper::toDto);
    }

    /**
     * Get all the factureEnTetes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FactureEnTeteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all FactureEnTetes");
        return factureEnTeteRepository.findAll(pageable).map(factureEnTeteMapper::toDto);
    }

    /**
     * Get one factureEnTete by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FactureEnTeteDTO> findOne(Integer id) {
        LOG.debug("Request to get FactureEnTete : {}", id);
        return factureEnTeteRepository.findById(id).map(factureEnTeteMapper::toDto);
    }

    /**
     * Delete the factureEnTete by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete FactureEnTete : {}", id);
        factureEnTeteRepository.deleteById(id);
    }
}
