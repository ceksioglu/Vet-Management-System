package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.entity.Vaccine;
import ceksioglu.vet_management_sys.service.concretes.VaccineManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * VaccineController, aşılarla ilgili API endpointlerini sağlar.
 */
@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final VaccineManager vaccineManager;

    @Autowired
    public VaccineController(VaccineManager vaccineManager) {
        this.vaccineManager = vaccineManager;
    }

    /**
     * Tüm aşıları getirir.
     *
     * @return Tüm aşıların listesi
     */
    @GetMapping
    public List<Vaccine> getAllVaccines() {
        return vaccineManager.getAllVaccines();
    }

    /**
     * Belirli bir ID'ye sahip aşıyı getirir.
     *
     * @param id Aşının ID'si
     * @return Aşı nesnesi
     */
    @GetMapping("/{id}")
    public Vaccine getVaccineById(@PathVariable Long id) {
        return vaccineManager.getVaccineById(id);
    }

    /**
     * Yeni bir aşı oluşturur.
     *
     * @param vaccine Oluşturulacak aşı nesnesi
     * @return Oluşturulan aşı nesnesi
     */
    @PostMapping
    public Vaccine createVaccine(@RequestBody Vaccine vaccine) {
        return vaccineManager.createVaccine(vaccine);
    }

    /**
     * Belirli bir ID'ye sahip aşıyı günceller.
     *
     * @param id Güncellenecek aşının ID'si
     * @param vaccine Güncellenmiş aşı nesnesi
     * @return Güncellenmiş aşı nesnesi
     */
    @PutMapping("/{id}")
    public Vaccine updateVaccine(@PathVariable Long id, @RequestBody Vaccine vaccine) {
        return vaccineManager.updateVaccine(id, vaccine);
    }

    /**
     * Belirli bir ID'ye sahip aşıyı siler.
     *
     * @param id Silinecek aşının ID'si
     * @return NoContent durumunda HTTP yanıtı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable Long id) {
        vaccineManager.deleteVaccine(id);
        return ResponseEntity.noContent().build();
    }
}
