package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.CommissionService;
import mc.sbm.simphonycloud.repository.CommissionServiceRepository;
import mc.sbm.simphonycloud.service.dto.CommissionServiceDTO;
import mc.sbm.simphonycloud.service.mapper.CommissionServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.CommissionService}.
 */
@Service
@Transactional
public class CommissionServiceService {

    private static final Logger LOG = LoggerFactory.getLogger(CommissionServiceService.class);

    private final CommissionServiceRepository commissionServiceRepository;

    private final CommissionServiceMapper commissionServiceMapper;

    public CommissionServiceService(
        CommissionServiceRepository commissionServiceRepository,
        CommissionServiceMapper commissionServiceMapper
    ) {
        this.commissionServiceRepository = commissionServiceRepository;
        this.commissionServiceMapper = commissionServiceMapper;
    }

    /**
     * Save a commissionService.
     *
     * @param commissionServiceDTO the entity to save.
     * @return the persisted entity.
     */
    public CommissionServiceDTO save(CommissionServiceDTO commissionServiceDTO) {
        LOG.debug("Request to save CommissionService : {}", commissionServiceDTO);
        CommissionService commissionService = commissionServiceMapper.toEntity(commissionServiceDTO);
        commissionService = commissionServiceRepository.save(commissionService);
        return commissionServiceMapper.toDto(commissionService);
    }

    /**
     * Update a commissionService.
     *
     * @param commissionServiceDTO the entity to save.
     * @return the persisted entity.
     */
    public CommissionServiceDTO update(CommissionServiceDTO commissionServiceDTO) {
        LOG.debug("Request to update CommissionService : {}", commissionServiceDTO);
        CommissionService commissionService = commissionServiceMapper.toEntity(commissionServiceDTO);
        commissionService = commissionServiceRepository.save(commissionService);
        return commissionServiceMapper.toDto(commissionService);
    }

    /**
     * Partially update a commissionService.
     *
     * @param commissionServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommissionServiceDTO> partialUpdate(CommissionServiceDTO commissionServiceDTO) {
        LOG.debug("Request to partially update CommissionService : {}", commissionServiceDTO);

        return commissionServiceRepository
            .findById(commissionServiceDTO.getId())
            .map(existingCommissionService -> {
                commissionServiceMapper.partialUpdate(existingCommissionService, commissionServiceDTO);

                return existingCommissionService;
            })
            .map(commissionServiceRepository::save)
            .map(commissionServiceMapper::toDto);
    }

    /**
     * Get all the commissionServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommissionServiceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CommissionServices");
        return commissionServiceRepository.findAll(pageable).map(commissionServiceMapper::toDto);
    }

    /**
     * Get one commissionService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommissionServiceDTO> findOne(Integer id) {
        LOG.debug("Request to get CommissionService : {}", id);
        return commissionServiceRepository.findById(id).map(commissionServiceMapper::toDto);
    }

    /**
     * Delete the commissionService by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete CommissionService : {}", id);
        commissionServiceRepository.deleteById(id);
    }
}
