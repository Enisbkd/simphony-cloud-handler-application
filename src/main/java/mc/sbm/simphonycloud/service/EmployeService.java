package mc.sbm.simphonycloud.service;

import java.util.Optional;
import mc.sbm.simphonycloud.domain.Employe;
import mc.sbm.simphonycloud.repository.EmployeRepository;
import mc.sbm.simphonycloud.service.dto.EmployeDTO;
import mc.sbm.simphonycloud.service.mapper.EmployeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link mc.sbm.simphonycloud.domain.Employe}.
 */
@Service
@Transactional
public class EmployeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeService.class);

    private final EmployeRepository employeRepository;

    private final EmployeMapper employeMapper;

    public EmployeService(EmployeRepository employeRepository, EmployeMapper employeMapper) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
    }

    /**
     * Save a employe.
     *
     * @param employeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeDTO save(EmployeDTO employeDTO) {
        LOG.debug("Request to save Employe : {}", employeDTO);
        Employe employe = employeMapper.toEntity(employeDTO);
        employe = employeRepository.save(employe);
        return employeMapper.toDto(employe);
    }

    /**
     * Update a employe.
     *
     * @param employeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeDTO update(EmployeDTO employeDTO) {
        LOG.debug("Request to update Employe : {}", employeDTO);
        Employe employe = employeMapper.toEntity(employeDTO);
        employe = employeRepository.save(employe);
        return employeMapper.toDto(employe);
    }

    /**
     * Partially update a employe.
     *
     * @param employeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeDTO> partialUpdate(EmployeDTO employeDTO) {
        LOG.debug("Request to partially update Employe : {}", employeDTO);

        return employeRepository
            .findById(employeDTO.getId())
            .map(existingEmploye -> {
                employeMapper.partialUpdate(existingEmploye, employeDTO);

                return existingEmploye;
            })
            .map(employeRepository::save)
            .map(employeMapper::toDto);
    }

    /**
     * Get all the employes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Employes");
        return employeRepository.findAll(pageable).map(employeMapper::toDto);
    }

    /**
     * Get one employe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeDTO> findOne(Integer id) {
        LOG.debug("Request to get Employe : {}", id);
        return employeRepository.findById(id).map(employeMapper::toDto);
    }

    /**
     * Delete the employe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Employe : {}", id);
        employeRepository.deleteById(id);
    }
}
