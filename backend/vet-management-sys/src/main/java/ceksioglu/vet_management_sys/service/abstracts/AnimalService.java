package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.dto.AnimalDTO;
import java.util.List;

public interface AnimalService {
    AnimalDTO saveAnimal(AnimalDTO animalDTO);
    AnimalDTO updateAnimal(Long id, AnimalDTO animalDTO);
    void deleteAnimal(Long id);
    AnimalDTO getAnimalById(Long id);
    List<AnimalDTO> getAllAnimals();
    List<AnimalDTO> getAnimalsByName(String name);
    List<AnimalDTO> getAnimalsByCustomerId(Long customerId);
}