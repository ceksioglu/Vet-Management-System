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

@Service
public class VaccineManager implements VaccineService {

    private final VaccineRepository vaccineRepository;
    private final AnimalRepository animalRepository;

    @Autowired
    public VaccineManager(VaccineRepository vaccineRepository, AnimalRepository animalRepository) {
        this.vaccineRepository = vaccineRepository;
        this.animalRepository = animalRepository;
    }

    @Override
    public VaccineDTO saveVaccine(VaccineDTO vaccineDTO) {
        Animal animal = animalRepository.findById(vaccineDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + vaccineDTO.getAnimalId()));

        if (isVaccineActive(animal.getId(), vaccineDTO.getName(), vaccineDTO.getCode())) {
            throw new ResourceAlreadyExistsException("This vaccine is already active for the animal");
        }

        Vaccine vaccine = convertToEntity(vaccineDTO);
        vaccine.setAnimal(animal);
        Vaccine savedVaccine = vaccineRepository.save(vaccine);
        return convertToDTO(savedVaccine);
    }

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

    @Override
    public void deleteVaccine(Long id) {
        if (!vaccineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vaccine not found with id: " + id);
        }
        vaccineRepository.deleteById(id);
    }

    @Override
    public VaccineDTO getVaccineById(Long id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with id: " + id));
        return convertToDTO(vaccine);
    }

    @Override
    public List<VaccineDTO> getAllVaccines() {
        return vaccineRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VaccineDTO> getVaccinesByAnimalId(Long animalId) {
        return vaccineRepository.findByAnimalId(animalId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VaccineDTO> getVaccinesByProtectionEndDateRange(Date startDate, Date endDate) {
        return vaccineRepository.findByProtectionFinishDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private boolean isVaccineActive(Long animalId, String vaccineName, String vaccineCode) {
        Date currentDate = new Date();
        return vaccineRepository.existsByAnimalIdAndNameAndCodeAndProtectionFinishDateAfter(animalId, vaccineName, vaccineCode, currentDate);
    }

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

    private Vaccine convertToEntity(VaccineDTO dto) {
        Vaccine vaccine = new Vaccine();
        vaccine.setName(dto.getName());
        vaccine.setCode(dto.getCode());
        vaccine.setProtectionStartDate(dto.getProtectionStartDate());
        vaccine.setProtectionFinishDate(dto.getProtectionFinishDate());
        return vaccine;
    }
}