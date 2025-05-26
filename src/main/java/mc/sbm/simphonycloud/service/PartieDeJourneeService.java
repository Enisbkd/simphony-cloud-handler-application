package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.PartieDeJournee;
import mc.sbm.simphonycloud.repository.PartieDeJourneeRepository;
import mc.sbm.simphonycloud.service.dto.PartieDeJourneeDTO;
import mc.sbm.simphonycloud.service.mapper.PartieDeJourneeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.PartieDeJournee}.
 */
@Service
@Transactional
public class PartieDeJourneeService {

    private static final Logger LOG = LoggerFactory.getLogger(PartieDeJourneeService.class);

    private final PartieDeJourneeRepository partieDeJourneeRepository;

    private final PartieDeJourneeMapper partieDeJourneeMapper;

    public PartieDeJourneeService(PartieDeJourneeRepository partieDeJourneeRepository, PartieDeJourneeMapper partieDeJourneeMapper) {
        this.partieDeJourneeRepository = partieDeJourneeRepository;
        this.partieDeJourneeMapper = partieDeJourneeMapper;
    }

    /**
     * Save a partieDeJournee.
     *
     * @param partieDeJourneeDTO the entity to save.
     * @return the persisted entity.
     */
    public PartieDeJourneeDTO save(PartieDeJourneeDTO partieDeJourneeDTO) {
        LOG.debug("Request to save PartieDeJournee : {}", partieDeJourneeDTO);
        PartieDeJournee partieDeJournee = partieDeJourneeMapper.toEntity(partieDeJourneeDTO);
        partieDeJournee = partieDeJourneeRepository.save(partieDeJournee);
        return partieDeJourneeMapper.toDto(partieDeJournee);
    }

    /**
     * Update a partieDeJournee.
     *
     * @param partieDeJourneeDTO the entity to save.
     * @return the persisted entity.
     */
    public PartieDeJourneeDTO update(PartieDeJourneeDTO partieDeJourneeDTO) {
        LOG.debug("Request to update PartieDeJournee : {}", partieDeJourneeDTO);
        PartieDeJournee partieDeJournee = partieDeJourneeMapper.toEntity(partieDeJourneeDTO);
        partieDeJournee = partieDeJourneeRepository.save(partieDeJournee);
        return partieDeJourneeMapper.toDto(partieDeJournee);
    }

    /**
     * Partially update a partieDeJournee.
     *
     * @param partieDeJourneeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PartieDeJourneeDTO> partialUpdate(PartieDeJourneeDTO partieDeJourneeDTO) {
        LOG.debug("Request to partially update PartieDeJournee : {}", partieDeJourneeDTO);

        return partieDeJourneeRepository
            .findById(partieDeJourneeDTO.getId())
            .map(existingPartieDeJournee -> {
                partieDeJourneeMapper.partialUpdate(existingPartieDeJournee, partieDeJourneeDTO);

                return existingPartieDeJournee;
            })
            .map(partieDeJourneeRepository::save)
            .map(partieDeJourneeMapper::toDto);
    }

    /**
     * Get all the partieDeJournees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartieDeJourneeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PartieDeJournees");
        return partieDeJourneeRepository.findAll(pageable).map(partieDeJourneeMapper::toDto);
    }

    /**
     * Get one partieDeJournee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartieDeJourneeDTO> findOne(Integer id) {
        LOG.debug("Request to get PartieDeJournee : {}", id);
        return partieDeJourneeRepository.findById(id).map(partieDeJourneeMapper::toDto);
    }

    /**
     * Delete the partieDeJournee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete PartieDeJournee : {}", id);
        partieDeJourneeRepository.deleteById(id);
    }
}
