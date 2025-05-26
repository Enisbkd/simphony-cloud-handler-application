package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.Remise;
import mc.sbm.simphonycloud.repository.RemiseRepository;
import mc.sbm.simphonycloud.service.dto.RemiseDTO;
import mc.sbm.simphonycloud.service.mapper.RemiseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.Remise}.
 */
@Service
@Transactional
public class RemiseService {

    private static final Logger LOG = LoggerFactory.getLogger(RemiseService.class);

    private final RemiseRepository remiseRepository;

    private final RemiseMapper remiseMapper;

    public RemiseService(RemiseRepository remiseRepository, RemiseMapper remiseMapper) {
        this.remiseRepository = remiseRepository;
        this.remiseMapper = remiseMapper;
    }

    /**
     * Save a remise.
     *
     * @param remiseDTO the entity to save.
     * @return the persisted entity.
     */
    public RemiseDTO save(RemiseDTO remiseDTO) {
        LOG.debug("Request to save Remise : {}", remiseDTO);
        Remise remise = remiseMapper.toEntity(remiseDTO);
        remise = remiseRepository.save(remise);
        return remiseMapper.toDto(remise);
    }

    /**
     * Update a remise.
     *
     * @param remiseDTO the entity to save.
     * @return the persisted entity.
     */
    public RemiseDTO update(RemiseDTO remiseDTO) {
        LOG.debug("Request to update Remise : {}", remiseDTO);
        Remise remise = remiseMapper.toEntity(remiseDTO);
        remise = remiseRepository.save(remise);
        return remiseMapper.toDto(remise);
    }

    /**
     * Partially update a remise.
     *
     * @param remiseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RemiseDTO> partialUpdate(RemiseDTO remiseDTO) {
        LOG.debug("Request to partially update Remise : {}", remiseDTO);

        return remiseRepository
            .findById(remiseDTO.getId())
            .map(existingRemise -> {
                remiseMapper.partialUpdate(existingRemise, remiseDTO);

                return existingRemise;
            })
            .map(remiseRepository::save)
            .map(remiseMapper::toDto);
    }

    /**
     * Get all the remises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RemiseDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Remises");
        return remiseRepository.findAll(pageable).map(remiseMapper::toDto);
    }

    /**
     * Get one remise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RemiseDTO> findOne(Integer id) {
        LOG.debug("Request to get Remise : {}", id);
        return remiseRepository.findById(id).map(remiseMapper::toDto);
    }

    /**
     * Delete the remise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Remise : {}", id);
        remiseRepository.deleteById(id);
    }
}
