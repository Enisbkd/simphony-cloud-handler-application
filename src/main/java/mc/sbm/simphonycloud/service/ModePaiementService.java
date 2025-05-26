package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.ModePaiement;
import mc.sbm.simphonycloud.repository.ModePaiementRepository;
import mc.sbm.simphonycloud.service.dto.ModePaiementDTO;
import mc.sbm.simphonycloud.service.mapper.ModePaiementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.ModePaiement}.
 */
@Service
@Transactional
public class ModePaiementService {

    private static final Logger LOG = LoggerFactory.getLogger(ModePaiementService.class);

    private final ModePaiementRepository modePaiementRepository;

    private final ModePaiementMapper modePaiementMapper;

    public ModePaiementService(ModePaiementRepository modePaiementRepository, ModePaiementMapper modePaiementMapper) {
        this.modePaiementRepository = modePaiementRepository;
        this.modePaiementMapper = modePaiementMapper;
    }

    /**
     * Save a modePaiement.
     *
     * @param modePaiementDTO the entity to save.
     * @return the persisted entity.
     */
    public ModePaiementDTO save(ModePaiementDTO modePaiementDTO) {
        LOG.debug("Request to save ModePaiement : {}", modePaiementDTO);
        ModePaiement modePaiement = modePaiementMapper.toEntity(modePaiementDTO);
        modePaiement = modePaiementRepository.save(modePaiement);
        return modePaiementMapper.toDto(modePaiement);
    }

    /**
     * Update a modePaiement.
     *
     * @param modePaiementDTO the entity to save.
     * @return the persisted entity.
     */
    public ModePaiementDTO update(ModePaiementDTO modePaiementDTO) {
        LOG.debug("Request to update ModePaiement : {}", modePaiementDTO);
        ModePaiement modePaiement = modePaiementMapper.toEntity(modePaiementDTO);
        modePaiement = modePaiementRepository.save(modePaiement);
        return modePaiementMapper.toDto(modePaiement);
    }

    /**
     * Partially update a modePaiement.
     *
     * @param modePaiementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ModePaiementDTO> partialUpdate(ModePaiementDTO modePaiementDTO) {
        LOG.debug("Request to partially update ModePaiement : {}", modePaiementDTO);

        return modePaiementRepository
            .findById(modePaiementDTO.getId())
            .map(existingModePaiement -> {
                modePaiementMapper.partialUpdate(existingModePaiement, modePaiementDTO);

                return existingModePaiement;
            })
            .map(modePaiementRepository::save)
            .map(modePaiementMapper::toDto);
    }

    /**
     * Get all the modePaiements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ModePaiementDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ModePaiements");
        return modePaiementRepository.findAll(pageable).map(modePaiementMapper::toDto);
    }

    /**
     * Get one modePaiement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModePaiementDTO> findOne(Integer id) {
        LOG.debug("Request to get ModePaiement : {}", id);
        return modePaiementRepository.findById(id).map(modePaiementMapper::toDto);
    }

    /**
     * Delete the modePaiement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete ModePaiement : {}", id);
        modePaiementRepository.deleteById(id);
    }
}
