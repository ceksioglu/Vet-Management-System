package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.dto.VaccineDTO;
import ceksioglu.vet_management_sys.entity.Vaccine;
import ceksioglu.vet_management_sys.entity.Animal;
import ceksioglu.vet_management_sys.repository.VaccineRepository;
import ceksioglu.vet_management_sys.repository.AnimalRepository;
import ceksioglu.vet_management_sys.service.abstracts.VaccineService;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.core.exception.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing vaccines.
 */
@Service
public class VaccineManager implements VaccineService {

    private final VaccineRepository vaccineRepository;
    private final AnimalRepository animalRepository;

    /**
     * Constructor for VaccineManager.
     *
     * @param vaccineRepository the vaccine repository
     * @param animalRepository the animal repository
     */
    @Autowired
    public VaccineManager(VaccineRepository vaccineRepository, AnimalRepository animalRepository) {
        this.vaccineRepository = vaccineRepository;
        this.animalRepository = animalRepository;
    }

    /**
     * Saves a vaccine.
     *
     * @param vaccineDTO the vaccine DTO
     * @return the saved vaccine DTO
     * @throws ResourceNotFoundException if the animal is not found
     * @throws ResourceAlreadyExistsException if the vaccine is already active for the animal
     */
    @Override
    public VaccineDTO saveVaccine(VaccineDTO vaccineDTO) {
        Animal animal = animalRepository.findById(vaccineDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + vaccineDTO.getAnimalId()));

        Date currentDate = new Date();
        if (vaccineDTO.getProtectionFinishDate().before(currentDate)) {
            // Protection period has ended, save directly
            Vaccine vaccine = convertToEntity(vaccineDTO);
            vaccine.setAnimal(animal);
            Vaccine savedVaccine = vaccineRepository.save(vaccine);
            return convertToDTO(savedVaccine);
        } else {
            // Protection period has not ended, check for active vaccine
            if (isVaccineActive(animal.getId(), vaccineDTO.getName(), vaccineDTO.getCode())) {
                throw new ResourceAlreadyExistsException("This vaccine is already active for the animal");
            }
            Vaccine vaccine = convertToEntity(vaccineDTO);
            vaccine.setAnimal(animal);
            Vaccine savedVaccine = vaccineRepository.save(vaccine);
            return convertToDTO(savedVaccine);
        }
    }

    /**
     * Updates a vaccine.
     *
     * @param id the vaccine ID
     * @param vaccineDTO the vaccine DTO
     * @return the updated vaccine DTO
     * @throws ResourceNotFoundException if the vaccine or animal is not found
     * @throws ResourceAlreadyExistsException if the vaccine is already active for the animal
     */
    @Override
    public VaccineDTO updateVaccine(Long id, VaccineDTO vaccineDTO) {
        Vaccine existingVaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with id: " + id));

        Animal animal = animalRepository.findById(vaccineDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + vaccineDTO.getAnimalId()));

        if (!existingVaccine.getAnimal().getId().equals(animal.getId()) &&
                isVaccineActive(animal.getId(), vaccineDTO.getName(), vaccineDTO.getCode())) {
            throw new ResourceAlreadyExistsException("This vaccine is already active for the animal");
        }

        existingVaccine.setName(vaccineDTO.getName());
        existingVaccine.setCode(vaccineDTO.getCode());
        existingVaccine.setProtectionStartDate(vaccineDTO.getProtectionStartDate());
        existingVaccine.setProtectionFinishDate(vaccineDTO.getProtectionFinishDate());
        existingVaccine.setAnimal(animal);

        Vaccine updatedVaccine = vaccineRepository.save(existingVaccine);
        return convertToDTO(updatedVaccine);
    }

    /**
     * Deletes a vaccine by ID.
     *
     * @param id the vaccine ID
     * @throws ResourceNotFoundException if the vaccine is not found
     */
    @Override
    public void deleteVaccine(Long id) {
        if (!vaccineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vaccine not found with id: " + id);
        }
        vaccineRepository.deleteById(id);
    }

    /**
     * Gets a vaccine by ID.
     *
     * @param id the vaccine ID
     * @return the vaccine DTO
     * @throws ResourceNotFoundException if the vaccine is not found
     */
    @Override
    public VaccineDTO getVaccineById(Long id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with id: " + id));
        return convertToDTO(vaccine);
    }

    /**
     * Gets all vaccines.
     *
     * @return the list of vaccine DTOs
     */
    @Override
    public List<VaccineDTO> getAllVaccines() {
        return vaccineRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets vaccines by animal ID.
     *
     * @param animalId the animal ID
     * @return the list of vaccine DTOs
     */
    @Override
    public List<VaccineDTO> getVaccinesByAnimalId(Long animalId) {
        return vaccineRepository.findByAnimalId(animalId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets vaccines by protection end date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the list of vaccine DTOs
     */
    @Override
    public List<VaccineDTO> getVaccinesByProtectionEndDateRange(Date startDate, Date endDate) {
        return vaccineRepository.findByProtectionFinishDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a vaccine is active for the given animal.
     *
     * @param animalId the animal ID
     * @param vaccineName the vaccine name
     * @param vaccineCode the vaccine code
     * @return true if the vaccine is active, false otherwise
     */
    private boolean isVaccineActive(Long animalId, String vaccineName, String vaccineCode) {
        Date currentDate = new Date();
        return vaccineRepository.existsByAnimalIdAndNameAndCodeAndProtectionFinishDateAfter(animalId, vaccineName, vaccineCode, currentDate);
    }

    /**
     * Converts a vaccine entity to a DTO.
     *
     * @param vaccine the vaccine entity
     * @return the vaccine DTO
     */
    private VaccineDTO convertToDTO(Vaccine vaccine) {
        VaccineDTO dto = new VaccineDTO();
        dto.setId(vaccine.getId());
        dto.setName(vaccine.getName());
        dto.setCode(vaccine.getCode());
        dto.setProtectionStartDate(vaccine.getProtectionStartDate());
        dto.setProtectionFinishDate(vaccine.getProtectionFinishDate());
        dto.setAnimalId(vaccine.getAnimal().getId());
        return dto;
    }

    /**
     * Converts a vaccine DTO to an entity.
     *
     * @param dto the vaccine DTO
     * @return the vaccine entity
     */
    private Vaccine convertToEntity(VaccineDTO dto) {
        Vaccine vaccine = new Vaccine();
        vaccine.setName(dto.getName());
        vaccine.setCode(dto.getCode());
        vaccine.setProtectionStartDate(dto.getProtectionStartDate());
        vaccine.setProtectionFinishDate(dto.getProtectionFinishDate());
        return vaccine;
    }
}
