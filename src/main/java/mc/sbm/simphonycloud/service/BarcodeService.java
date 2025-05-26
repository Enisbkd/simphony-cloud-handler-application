package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.Barcode;
import mc.sbm.simphonycloud.repository.BarcodeRepository;
import mc.sbm.simphonycloud.service.dto.BarcodeDTO;
import mc.sbm.simphonycloud.service.mapper.BarcodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.Barcode}.
 */
@Service
@Transactional
public class BarcodeService {

    private static final Logger LOG = LoggerFactory.getLogger(BarcodeService.class);

    private final BarcodeRepository barcodeRepository;

    private final BarcodeMapper barcodeMapper;

    public BarcodeService(BarcodeRepository barcodeRepository, BarcodeMapper barcodeMapper) {
        this.barcodeRepository = barcodeRepository;
        this.barcodeMapper = barcodeMapper;
    }

    /**
     * Save a barcode.
     *
     * @param barcodeDTO the entity to save.
     * @return the persisted entity.
     */
    public BarcodeDTO save(BarcodeDTO barcodeDTO) {
        LOG.debug("Request to save Barcode : {}", barcodeDTO);
        Barcode barcode = barcodeMapper.toEntity(barcodeDTO);
        barcode = barcodeRepository.save(barcode);
        return barcodeMapper.toDto(barcode);
    }

    /**
     * Update a barcode.
     *
     * @param barcodeDTO the entity to save.
     * @return the persisted entity.
     */
    public BarcodeDTO update(BarcodeDTO barcodeDTO) {
        LOG.debug("Request to update Barcode : {}", barcodeDTO);
        Barcode barcode = barcodeMapper.toEntity(barcodeDTO);
        barcode = barcodeRepository.save(barcode);
        return barcodeMapper.toDto(barcode);
    }

    /**
     * Partially update a barcode.
     *
     * @param barcodeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BarcodeDTO> partialUpdate(BarcodeDTO barcodeDTO) {
        LOG.debug("Request to partially update Barcode : {}", barcodeDTO);

        return barcodeRepository
            .findById(barcodeDTO.getId())
            .map(existingBarcode -> {
                barcodeMapper.partialUpdate(existingBarcode, barcodeDTO);

                return existingBarcode;
            })
            .map(barcodeRepository::save)
            .map(barcodeMapper::toDto);
    }

    /**
     * Get all the barcodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BarcodeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Barcodes");
        return barcodeRepository.findAll(pageable).map(barcodeMapper::toDto);
    }

    /**
     * Get one barcode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BarcodeDTO> findOne(Integer id) {
        LOG.debug("Request to get Barcode : {}", id);
        return barcodeRepository.findById(id).map(barcodeMapper::toDto);
    }

    /**
     * Delete the barcode by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Barcode : {}", id);
        barcodeRepository.deleteById(id);
    }
}
