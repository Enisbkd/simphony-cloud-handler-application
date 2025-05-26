package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.MajorGroup;
import mc.sbm.simphonycloud.repository.MajorGroupRepository;
import mc.sbm.simphonycloud.service.dto.MajorGroupDTO;
import mc.sbm.simphonycloud.service.mapper.MajorGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.MajorGroup}.
 */
@Service
@Transactional
public class MajorGroupService {

    private static final Logger LOG = LoggerFactory.getLogger(MajorGroupService.class);

    private final MajorGroupRepository majorGroupRepository;

    private final MajorGroupMapper majorGroupMapper;

    public MajorGroupService(MajorGroupRepository majorGroupRepository, MajorGroupMapper majorGroupMapper) {
        this.majorGroupRepository = majorGroupRepository;
        this.majorGroupMapper = majorGroupMapper;
    }

    /**
     * Save a majorGroup.
     *
     * @param majorGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public MajorGroupDTO save(MajorGroupDTO majorGroupDTO) {
        LOG.debug("Request to save MajorGroup : {}", majorGroupDTO);
        MajorGroup majorGroup = majorGroupMapper.toEntity(majorGroupDTO);
        majorGroup = majorGroupRepository.save(majorGroup);
        return majorGroupMapper.toDto(majorGroup);
    }

    /**
     * Update a majorGroup.
     *
     * @param majorGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public MajorGroupDTO update(MajorGroupDTO majorGroupDTO) {
        LOG.debug("Request to update MajorGroup : {}", majorGroupDTO);
        MajorGroup majorGroup = majorGroupMapper.toEntity(majorGroupDTO);
        majorGroup = majorGroupRepository.save(majorGroup);
        return majorGroupMapper.toDto(majorGroup);
    }

    /**
     * Partially update a majorGroup.
     *
     * @param majorGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MajorGroupDTO> partialUpdate(MajorGroupDTO majorGroupDTO) {
        LOG.debug("Request to partially update MajorGroup : {}", majorGroupDTO);

        return majorGroupRepository
            .findById(majorGroupDTO.getId())
            .map(existingMajorGroup -> {
                majorGroupMapper.partialUpdate(existingMajorGroup, majorGroupDTO);

                return existingMajorGroup;
            })
            .map(majorGroupRepository::save)
            .map(majorGroupMapper::toDto);
    }

    /**
     * Get all the majorGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MajorGroupDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MajorGroups");
        return majorGroupRepository.findAll(pageable).map(majorGroupMapper::toDto);
    }

    /**
     * Get one majorGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MajorGroupDTO> findOne(Integer id) {
        LOG.debug("Request to get MajorGroup : {}", id);
        return majorGroupRepository.findById(id).map(majorGroupMapper::toDto);
    }

    /**
     * Delete the majorGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete MajorGroup : {}", id);
        majorGroupRepository.deleteById(id);
    }
}
