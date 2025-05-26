package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.Taxe;
import mc.sbm.simphonycloud.repository.TaxeRepository;
import mc.sbm.simphonycloud.service.dto.TaxeDTO;
import mc.sbm.simphonycloud.service.mapper.TaxeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.Taxe}.
 */
@Service
@Transactional
public class TaxeService {

    private static final Logger LOG = LoggerFactory.getLogger(TaxeService.class);

    private final TaxeRepository taxeRepository;

    private final TaxeMapper taxeMapper;

    public TaxeService(TaxeRepository taxeRepository, TaxeMapper taxeMapper) {
        this.taxeRepository = taxeRepository;
        this.taxeMapper = taxeMapper;
    }

    /**
     * Save a taxe.
     *
     * @param taxeDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxeDTO save(TaxeDTO taxeDTO) {
        LOG.debug("Request to save Taxe : {}", taxeDTO);
        Taxe taxe = taxeMapper.toEntity(taxeDTO);
        taxe = taxeRepository.save(taxe);
        return taxeMapper.toDto(taxe);
    }

    /**
     * Update a taxe.
     *
     * @param taxeDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxeDTO update(TaxeDTO taxeDTO) {
        LOG.debug("Request to update Taxe : {}", taxeDTO);
        Taxe taxe = taxeMapper.toEntity(taxeDTO);
        taxe = taxeRepository.save(taxe);
        return taxeMapper.toDto(taxe);
    }

    /**
     * Partially update a taxe.
     *
     * @param taxeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaxeDTO> partialUpdate(TaxeDTO taxeDTO) {
        LOG.debug("Request to partially update Taxe : {}", taxeDTO);

        return taxeRepository
            .findById(taxeDTO.getId())
            .map(existingTaxe -> {
                taxeMapper.partialUpdate(existingTaxe, taxeDTO);

                return existingTaxe;
            })
            .map(taxeRepository::save)
            .map(taxeMapper::toDto);
    }

    /**
     * Get all the taxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Taxes");
        return taxeRepository.findAll(pageable).map(taxeMapper::toDto);
    }

    /**
     * Get one taxe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaxeDTO> findOne(Integer id) {
        LOG.debug("Request to get Taxe : {}", id);
        return taxeRepository.findById(id).map(taxeMapper::toDto);
    }

    /**
     * Delete the taxe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Taxe : {}", id);
        taxeRepository.deleteById(id);
    }
}
