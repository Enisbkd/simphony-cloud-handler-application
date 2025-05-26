package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.FamilyGroup;
import mc.sbm.simphonycloud.repository.FamilyGroupRepository;
import mc.sbm.simphonycloud.service.dto.FamilyGroupDTO;
import mc.sbm.simphonycloud.service.mapper.FamilyGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.FamilyGroup}.
 */
@Service
@Transactional
public class FamilyGroupService {

    private static final Logger LOG = LoggerFactory.getLogger(FamilyGroupService.class);

    private final FamilyGroupRepository familyGroupRepository;

    private final FamilyGroupMapper familyGroupMapper;

    public FamilyGroupService(FamilyGroupRepository familyGroupRepository, FamilyGroupMapper familyGroupMapper) {
        this.familyGroupRepository = familyGroupRepository;
        this.familyGroupMapper = familyGroupMapper;
    }

    /**
     * Save a familyGroup.
     *
     * @param familyGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public FamilyGroupDTO save(FamilyGroupDTO familyGroupDTO) {
        LOG.debug("Request to save FamilyGroup : {}", familyGroupDTO);
        FamilyGroup familyGroup = familyGroupMapper.toEntity(familyGroupDTO);
        familyGroup = familyGroupRepository.save(familyGroup);
        return familyGroupMapper.toDto(familyGroup);
    }

    /**
     * Update a familyGroup.
     *
     * @param familyGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public FamilyGroupDTO update(FamilyGroupDTO familyGroupDTO) {
        LOG.debug("Request to update FamilyGroup : {}", familyGroupDTO);
        FamilyGroup familyGroup = familyGroupMapper.toEntity(familyGroupDTO);
        familyGroup = familyGroupRepository.save(familyGroup);
        return familyGroupMapper.toDto(familyGroup);
    }

    /**
     * Partially update a familyGroup.
     *
     * @param familyGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FamilyGroupDTO> partialUpdate(FamilyGroupDTO familyGroupDTO) {
        LOG.debug("Request to partially update FamilyGroup : {}", familyGroupDTO);

        return familyGroupRepository
            .findById(familyGroupDTO.getId())
            .map(existingFamilyGroup -> {
                familyGroupMapper.partialUpdate(existingFamilyGroup, familyGroupDTO);

                return existingFamilyGroup;
            })
            .map(familyGroupRepository::save)
            .map(familyGroupMapper::toDto);
    }

    /**
     * Get all the familyGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FamilyGroupDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all FamilyGroups");
        return familyGroupRepository.findAll(pageable).map(familyGroupMapper::toDto);
    }

    /**
     * Get one familyGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FamilyGroupDTO> findOne(Integer id) {
        LOG.debug("Request to get FamilyGroup : {}", id);
        return familyGroupRepository.findById(id).map(familyGroupMapper::toDto);
    }

    /**
     * Delete the familyGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete FamilyGroup : {}", id);
        familyGroupRepository.deleteById(id);
    }
}
