package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.Societe;
import mc.sbm.simphonycloud.repository.SocieteRepository;
import mc.sbm.simphonycloud.service.dto.SocieteDTO;
import mc.sbm.simphonycloud.service.mapper.SocieteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.Societe}.
 */
@Service
@Transactional
public class SocieteService {

    private static final Logger LOG = LoggerFactory.getLogger(SocieteService.class);

    private final SocieteRepository societeRepository;

    private final SocieteMapper societeMapper;

    public SocieteService(SocieteRepository societeRepository, SocieteMapper societeMapper) {
        this.societeRepository = societeRepository;
        this.societeMapper = societeMapper;
    }

    /**
     * Save a societe.
     *
     * @param societeDTO the entity to save.
     * @return the persisted entity.
     */
    public SocieteDTO save(SocieteDTO societeDTO) {
        LOG.debug("Request to save Societe : {}", societeDTO);
        Societe societe = societeMapper.toEntity(societeDTO);
        societe = societeRepository.save(societe);
        return societeMapper.toDto(societe);
    }

    /**
     * Update a societe.
     *
     * @param societeDTO the entity to save.
     * @return the persisted entity.
     */
    public SocieteDTO update(SocieteDTO societeDTO) {
        LOG.debug("Request to update Societe : {}", societeDTO);
        Societe societe = societeMapper.toEntity(societeDTO);
        societe = societeRepository.save(societe);
        return societeMapper.toDto(societe);
    }

    /**
     * Partially update a societe.
     *
     * @param societeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocieteDTO> partialUpdate(SocieteDTO societeDTO) {
        LOG.debug("Request to partially update Societe : {}", societeDTO);

        return societeRepository
            .findById(societeDTO.getId())
            .map(existingSociete -> {
                societeMapper.partialUpdate(existingSociete, societeDTO);

                return existingSociete;
            })
            .map(societeRepository::save)
            .map(societeMapper::toDto);
    }

    /**
     * Get all the societes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocieteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Societes");
        return societeRepository.findAll(pageable).map(societeMapper::toDto);
    }

    /**
     * Get one societe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocieteDTO> findOne(String id) {
        LOG.debug("Request to get Societe : {}", id);
        return societeRepository.findById(id).map(societeMapper::toDto);
    }

    /**
     * Delete the societe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Societe : {}", id);
        societeRepository.deleteById(id);
    }
}
