package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.entity.Animal;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.repository.AnimalRepository;
import ceksioglu.vet_management_sys.service.abstracts.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AnimalManager, AnimalService arayüzünü implement eden sınıftır.
 * Hayvanlarla ilgili iş mantığını içerir.
 */
@Service
public class AnimalManager implements AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalManager(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Tüm hayvanları getirir.
     *
     * @return Tüm hayvanların listesi
     */
    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    /**
     * Belirli bir ID'ye sahip hayvanı getirir.
     *
     * @param id Hayvanın ID'si
     * @return Hayvan nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip hayvan bulunamadığında fırlatılır
     */
    @Override
    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bu ID'ye ait hayvan bulunamadı: " + id));
    }

    /**
     * Yeni bir hayvan oluşturur.
     *
     * @param animal Oluşturulacak hayvan nesnesi
     * @return Oluşturulan hayvan nesnesi
     */
    @Override
    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    /**
     * Belirli bir ID'ye sahip hayvanı günceller.
     *
     * @param id Güncellenecek hayvanın ID'si
     * @param updatedAnimal Güncellenmiş hayvan nesnesi
     * @return Güncellenmiş hayvan nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip hayvan bulunamadığında fırlatılır
     */
    @Override
    public Animal updateAnimal(Long id, Animal updatedAnimal) {
        return animalRepository.findById(id).map(animal -> {
            animal.setName(updatedAnimal.getName());
            animal.setSpecies(updatedAnimal.getSpecies());
            animal.setBreed(updatedAnimal.getBreed());
            animal.setGender(updatedAnimal.getGender());
            animal.setColor(updatedAnimal.getColor());
            animal.setDateOfBirth(updatedAnimal.getDateOfBirth());
            return animalRepository.save(animal);
        }).orElseThrow(() -> new ResourceNotFoundException("Bu ID'ye ait hayvan bulunamadı: " + id));
    }

    /**
     * Belirli bir ID'ye sahip hayvanı siler.
     *
     * @param id Silinecek hayvanın ID'si
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip hayvan bulunamadığında fırlatılır
     */
    @Override
    public void deleteAnimal(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bu ID'ye ait hayvan bulunamadı: " + id);
        }
        animalRepository.deleteById(id);
    }
}
