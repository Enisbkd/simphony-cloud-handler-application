package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.CodeRaison;
import mc.sbm.simphonycloud.repository.CodeRaisonRepository;
import mc.sbm.simphonycloud.service.dto.CodeRaisonDTO;
import mc.sbm.simphonycloud.service.mapper.CodeRaisonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.CodeRaison}.
 */
@Service
@Transactional
public class CodeRaisonService {

    private static final Logger LOG = LoggerFactory.getLogger(CodeRaisonService.class);

    private final CodeRaisonRepository codeRaisonRepository;

    private final CodeRaisonMapper codeRaisonMapper;

    public CodeRaisonService(CodeRaisonRepository codeRaisonRepository, CodeRaisonMapper codeRaisonMapper) {
        this.codeRaisonRepository = codeRaisonRepository;
        this.codeRaisonMapper = codeRaisonMapper;
    }

    /**
     * Save a codeRaison.
     *
     * @param codeRaisonDTO the entity to save.
     * @return the persisted entity.
     */
    public CodeRaisonDTO save(CodeRaisonDTO codeRaisonDTO) {
        LOG.debug("Request to save CodeRaison : {}", codeRaisonDTO);
        CodeRaison codeRaison = codeRaisonMapper.toEntity(codeRaisonDTO);
        codeRaison = codeRaisonRepository.save(codeRaison);
        return codeRaisonMapper.toDto(codeRaison);
    }

    /**
     * Update a codeRaison.
     *
     * @param codeRaisonDTO the entity to save.
     * @return the persisted entity.
     */
    public CodeRaisonDTO update(CodeRaisonDTO codeRaisonDTO) {
        LOG.debug("Request to update CodeRaison : {}", codeRaisonDTO);
        CodeRaison codeRaison = codeRaisonMapper.toEntity(codeRaisonDTO);
        codeRaison = codeRaisonRepository.save(codeRaison);
        return codeRaisonMapper.toDto(codeRaison);
    }

    /**
     * Partially update a codeRaison.
     *
     * @param codeRaisonDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CodeRaisonDTO> partialUpdate(CodeRaisonDTO codeRaisonDTO) {
        LOG.debug("Request to partially update CodeRaison : {}", codeRaisonDTO);

        return codeRaisonRepository
            .findById(codeRaisonDTO.getId())
            .map(existingCodeRaison -> {
                codeRaisonMapper.partialUpdate(existingCodeRaison, codeRaisonDTO);

                return existingCodeRaison;
            })
            .map(codeRaisonRepository::save)
            .map(codeRaisonMapper::toDto);
    }

    /**
     * Get all the codeRaisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CodeRaisonDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CodeRaisons");
        return codeRaisonRepository.findAll(pageable).map(codeRaisonMapper::toDto);
    }

    /**
     * Get one codeRaison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CodeRaisonDTO> findOne(Integer id) {
        LOG.debug("Request to get CodeRaison : {}", id);
        return codeRaisonRepository.findById(id).map(codeRaisonMapper::toDto);
    }

    /**
     * Delete the codeRaison by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete CodeRaison : {}", id);
        codeRaisonRepository.deleteById(id);
    }
}
