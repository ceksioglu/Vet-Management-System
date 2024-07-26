package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.dto.AnimalDTO;
import ceksioglu.vet_management_sys.entity.Animal;
import ceksioglu.vet_management_sys.entity.Customer;
import ceksioglu.vet_management_sys.repository.AnimalRepository;
import ceksioglu.vet_management_sys.repository.CustomerRepository;
import ceksioglu.vet_management_sys.service.abstracts.AnimalService;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalManager implements AnimalService {

    private final AnimalRepository animalRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AnimalManager(AnimalRepository animalRepository, CustomerRepository customerRepository) {
        this.animalRepository = animalRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public AnimalDTO saveAnimal(AnimalDTO animalDTO) {
        Customer customer = customerRepository.findById(animalDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + animalDTO.getCustomerId()));

        Animal animal = new Animal();
        animal.setName(animalDTO.getName());
        animal.setSpecies(animalDTO.getSpecies());
        animal.setBreed(animalDTO.getBreed());
        animal.setGender(Animal.Gender.valueOf(animalDTO.getGender()));
        animal.setColor(animalDTO.getColor());
        animal.setDateOfBirth(animalDTO.getDateOfBirth());
        animal.setCustomer(customer);

        Animal savedAnimal = animalRepository.save(animal);
        return convertToDTO(savedAnimal);
    }

    @Override
    public AnimalDTO updateAnimal(Long id, AnimalDTO animalDTO) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + id));

        Customer customer = customerRepository.findById(animalDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + animalDTO.getCustomerId()));

        animal.setName(animalDTO.getName());
        animal.setSpecies(animalDTO.getSpecies());
        animal.setBreed(animalDTO.getBreed());
        animal.setGender(Animal.Gender.valueOf(animalDTO.getGender()));
        animal.setColor(animalDTO.getColor());
        animal.setDateOfBirth(animalDTO.getDateOfBirth());
        animal.setCustomer(customer);

        Animal updatedAnimal = animalRepository.save(animal);
        return convertToDTO(updatedAnimal);
    }

    @Override
    public void deleteAnimal(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Animal not found with id: " + id);
        }
        animalRepository.deleteById(id);
    }

    @Override
    public AnimalDTO getAnimalById(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + id));
        return convertToDTO(animal);
    }

    @Override
    public List<AnimalDTO> getAllAnimals() {
        return animalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDTO> getAnimalsByName(String name) {
        return animalRepository.findByName(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDTO> getAnimalsByCustomerId(Long customerId) {
        return animalRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AnimalDTO convertToDTO(Animal animal) {
        AnimalDTO dto = new AnimalDTO();
        dto.setId(animal.getId());
        dto.setName(animal.getName());
        dto.setSpecies(animal.getSpecies());
        dto.setBreed(animal.getBreed());
        dto.setGender(animal.getGender().toString());
        dto.setColor(animal.getColor());
        dto.setDateOfBirth(animal.getDateOfBirth());
        dto.setCustomerId(animal.getCustomer().getId());
        return dto;
    }
}