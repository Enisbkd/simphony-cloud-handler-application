package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.FactureDetail;
import mc.sbm.simphonycloud.repository.FactureDetailRepository;
import mc.sbm.simphonycloud.service.dto.FactureDetailDTO;
import mc.sbm.simphonycloud.service.mapper.FactureDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.FactureDetail}.
 */
@Service
@Transactional
public class FactureDetailService {

    private static final Logger LOG = LoggerFactory.getLogger(FactureDetailService.class);

    private final FactureDetailRepository factureDetailRepository;

    private final FactureDetailMapper factureDetailMapper;

    public FactureDetailService(FactureDetailRepository factureDetailRepository, FactureDetailMapper factureDetailMapper) {
        this.factureDetailRepository = factureDetailRepository;
        this.factureDetailMapper = factureDetailMapper;
    }

    /**
     * Save a factureDetail.
     *
     * @param factureDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public FactureDetailDTO save(FactureDetailDTO factureDetailDTO) {
        LOG.debug("Request to save FactureDetail : {}", factureDetailDTO);
        FactureDetail factureDetail = factureDetailMapper.toEntity(factureDetailDTO);
        factureDetail = factureDetailRepository.save(factureDetail);
        return factureDetailMapper.toDto(factureDetail);
    }

    /**
     * Update a factureDetail.
     *
     * @param factureDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public FactureDetailDTO update(FactureDetailDTO factureDetailDTO) {
        LOG.debug("Request to update FactureDetail : {}", factureDetailDTO);
        FactureDetail factureDetail = factureDetailMapper.toEntity(factureDetailDTO);
        factureDetail = factureDetailRepository.save(factureDetail);
        return factureDetailMapper.toDto(factureDetail);
    }

    /**
     * Partially update a factureDetail.
     *
     * @param factureDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FactureDetailDTO> partialUpdate(FactureDetailDTO factureDetailDTO) {
        LOG.debug("Request to partially update FactureDetail : {}", factureDetailDTO);

        return factureDetailRepository
            .findById(factureDetailDTO.getId())
            .map(existingFactureDetail -> {
                factureDetailMapper.partialUpdate(existingFactureDetail, factureDetailDTO);

                return existingFactureDetail;
            })
            .map(factureDetailRepository::save)
            .map(factureDetailMapper::toDto);
    }

    /**
     * Get all the factureDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FactureDetailDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all FactureDetails");
        return factureDetailRepository.findAll(pageable).map(factureDetailMapper::toDto);
    }

    /**
     * Get one factureDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FactureDetailDTO> findOne(Integer id) {
        LOG.debug("Request to get FactureDetail : {}", id);
        return factureDetailRepository.findById(id).map(factureDetailMapper::toDto);
    }

    /**
     * Delete the factureDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete FactureDetail : {}", id);
        factureDetailRepository.deleteById(id);
    }
}
