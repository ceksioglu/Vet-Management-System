package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.entity.Animal;
import ceksioglu.vet_management_sys.service.concretes.AnimalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AnimalController, hayvanlarla ilgili API endpointlerini sağlar.
 */
@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalManager animalManager;

    @Autowired
    public AnimalController(AnimalManager animalManager) {
        this.animalManager = animalManager;
    }

    /**
     * Tüm hayvanları getirir.
     *
     * @return Tüm hayvanların listesi
     */
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalManager.getAllAnimals();
    }

    /**
     * Belirli bir ID'ye sahip hayvanı getirir.
     *
     * @param id Hayvanın ID'si
     * @return Hayvan nesnesi
     */
    @GetMapping("/{id}")
    public Animal getAnimalById(@PathVariable Long id) {
        return animalManager.getAnimalById(id);
    }

    /**
     * Yeni bir hayvan oluşturur.
     *
     * @param animal Oluşturulacak hayvan nesnesi
     * @return Oluşturulan hayvan nesnesi
     */
    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalManager.createAnimal(animal);
    }

    /**
     * Belirli bir ID'ye sahip hayvanı günceller.
     *
     * @param id Güncellenecek hayvanın ID'si
     * @param animal Güncellenmiş hayvan nesnesi
     * @return Güncellenmiş hayvan nesnesi
     */
    @PutMapping("/{id}")
    public Animal updateAnimal(@PathVariable Long id, @RequestBody Animal animal) {
        return animalManager.updateAnimal(id, animal);
    }

    /**
     * Belirli bir ID'ye sahip hayvanı siler.
     *
     * @param id Silinecek hayvanın ID'si
     * @return NoContent durumunda HTTP yanıtı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalManager.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
