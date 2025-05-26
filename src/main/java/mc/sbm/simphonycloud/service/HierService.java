package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.Hier;
import mc.sbm.simphonycloud.repository.HierRepository;
import mc.sbm.simphonycloud.service.dto.HierDTO;
import mc.sbm.simphonycloud.service.mapper.HierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.Hier}.
 */
@Service
@Transactional
public class HierService {

    private static final Logger LOG = LoggerFactory.getLogger(HierService.class);

    private final HierRepository hierRepository;

    private final HierMapper hierMapper;

    public HierService(HierRepository hierRepository, HierMapper hierMapper) {
        this.hierRepository = hierRepository;
        this.hierMapper = hierMapper;
    }

    /**
     * Save a hier.
     *
     * @param hierDTO the entity to save.
     * @return the persisted entity.
     */
    public HierDTO save(HierDTO hierDTO) {
        LOG.debug("Request to save Hier : {}", hierDTO);
        Hier hier = hierMapper.toEntity(hierDTO);
        hier = hierRepository.save(hier);
        return hierMapper.toDto(hier);
    }

    /**
     * Update a hier.
     *
     * @param hierDTO the entity to save.
     * @return the persisted entity.
     */
    public HierDTO update(HierDTO hierDTO) {
        LOG.debug("Request to update Hier : {}", hierDTO);
        Hier hier = hierMapper.toEntity(hierDTO);
        hier = hierRepository.save(hier);
        return hierMapper.toDto(hier);
    }

    /**
     * Partially update a hier.
     *
     * @param hierDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HierDTO> partialUpdate(HierDTO hierDTO) {
        LOG.debug("Request to partially update Hier : {}", hierDTO);

        return hierRepository
            .findById(hierDTO.getId())
            .map(existingHier -> {
                hierMapper.partialUpdate(existingHier, hierDTO);

                return existingHier;
            })
            .map(hierRepository::save)
            .map(hierMapper::toDto);
    }

    /**
     * Get all the hiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HierDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Hiers");
        return hierRepository.findAll(pageable).map(hierMapper::toDto);
    }

    /**
     * Get one hier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HierDTO> findOne(String id) {
        LOG.debug("Request to get Hier : {}", id);
        return hierRepository.findById(id).map(hierMapper::toDto);
    }

    /**
     * Delete the hier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Hier : {}", id);
        hierRepository.deleteById(id);
    }
}
