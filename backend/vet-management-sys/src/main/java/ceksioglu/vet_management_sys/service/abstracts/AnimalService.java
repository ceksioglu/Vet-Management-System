package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.entity.Animal;

import java.util.List;

public interface AnimalService {
    List<Animal> getAllAnimals();

    Animal getAnimalById(Long id);

    Animal createAnimal(Animal animal);

    Animal updateAnimal(Long id, Animal animal);

    void deleteAnimal(Long id);
}
