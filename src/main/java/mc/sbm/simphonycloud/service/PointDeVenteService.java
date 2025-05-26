package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.PointDeVente;
import mc.sbm.simphonycloud.repository.PointDeVenteRepository;
import mc.sbm.simphonycloud.service.dto.PointDeVenteDTO;
import mc.sbm.simphonycloud.service.mapper.PointDeVenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.PointDeVente}.
 */
@Service
@Transactional
public class PointDeVenteService {

    private static final Logger LOG = LoggerFactory.getLogger(PointDeVenteService.class);

    private final PointDeVenteRepository pointDeVenteRepository;

    private final PointDeVenteMapper pointDeVenteMapper;

    public PointDeVenteService(PointDeVenteRepository pointDeVenteRepository, PointDeVenteMapper pointDeVenteMapper) {
        this.pointDeVenteRepository = pointDeVenteRepository;
        this.pointDeVenteMapper = pointDeVenteMapper;
    }

    /**
     * Save a pointDeVente.
     *
     * @param pointDeVenteDTO the entity to save.
     * @return the persisted entity.
     */
    public PointDeVenteDTO save(PointDeVenteDTO pointDeVenteDTO) {
        LOG.debug("Request to save PointDeVente : {}", pointDeVenteDTO);
        PointDeVente pointDeVente = pointDeVenteMapper.toEntity(pointDeVenteDTO);
        pointDeVente = pointDeVenteRepository.save(pointDeVente);
        return pointDeVenteMapper.toDto(pointDeVente);
    }

    /**
     * Update a pointDeVente.
     *
     * @param pointDeVenteDTO the entity to save.
     * @return the persisted entity.
     */
    public PointDeVenteDTO update(PointDeVenteDTO pointDeVenteDTO) {
        LOG.debug("Request to update PointDeVente : {}", pointDeVenteDTO);
        PointDeVente pointDeVente = pointDeVenteMapper.toEntity(pointDeVenteDTO);
        pointDeVente = pointDeVenteRepository.save(pointDeVente);
        return pointDeVenteMapper.toDto(pointDeVente);
    }

    /**
     * Partially update a pointDeVente.
     *
     * @param pointDeVenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PointDeVenteDTO> partialUpdate(PointDeVenteDTO pointDeVenteDTO) {
        LOG.debug("Request to partially update PointDeVente : {}", pointDeVenteDTO);

        return pointDeVenteRepository
            .findById(pointDeVenteDTO.getId())
            .map(existingPointDeVente -> {
                pointDeVenteMapper.partialUpdate(existingPointDeVente, pointDeVenteDTO);

                return existingPointDeVente;
            })
            .map(pointDeVenteRepository::save)
            .map(pointDeVenteMapper::toDto);
    }

    /**
     * Get all the pointDeVentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PointDeVenteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PointDeVentes");
        return pointDeVenteRepository.findAll(pageable).map(pointDeVenteMapper::toDto);
    }

    /**
     * Get one pointDeVente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PointDeVenteDTO> findOne(Integer id) {
        LOG.debug("Request to get PointDeVente : {}", id);
        return pointDeVenteRepository.findById(id).map(pointDeVenteMapper::toDto);
    }

    /**
     * Delete the pointDeVente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete PointDeVente : {}", id);
        pointDeVenteRepository.deleteById(id);
    }
}
