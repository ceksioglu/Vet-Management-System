package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.entity.AvailableDate;
import ceksioglu.vet_management_sys.service.concretes.AvailableDateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AvailableDateController, doktorların müsait günleriyle ilgili API endpointlerini sağlar.
 */
@RestController
@RequestMapping("/api/available-dates")
public class AvailableDateController {

    private final AvailableDateManager availableDateManager;

    @Autowired
    public AvailableDateController(AvailableDateManager availableDateManager) {
        this.availableDateManager = availableDateManager;
    }

    /**
     * Tüm müsait günleri getirir.
     *
     * @return Tüm müsait günlerin listesi
     */
    @GetMapping
    public List<AvailableDate> getAllAvailableDates() {
        return availableDateManager.getAllAvailableDates();
    }

    /**
     * Belirli bir ID'ye sahip müsait günü getirir.
     *
     * @param id Müsait günün ID'si
     * @return Müsait gün nesnesi
     */
    @GetMapping("/{id}")
    public AvailableDate getAvailableDateById(@PathVariable Long id) {
        return availableDateManager.getAvailableDateById(id);
    }

    /**
     * Yeni bir müsait gün oluşturur.
     *
     * @param availableDate Oluşturulacak müsait gün nesnesi
     * @return Oluşturulan müsait gün nesnesi
     */
    @PostMapping
    public AvailableDate createAvailableDate(@RequestBody AvailableDate availableDate) {
        return availableDateManager.createAvailableDate(availableDate);
    }

    /**
     * Belirli bir ID'ye sahip müsait günü günceller.
     *
     * @param id Güncellenecek müsait günün ID'si
     * @param availableDate Güncellenmiş müsait gün nesnesi
     * @return Güncellenmiş müsait gün nesnesi
     */
    @PutMapping("/{id}")
    public AvailableDate updateAvailableDate(@PathVariable Long id, @RequestBody AvailableDate availableDate) {
        return availableDateManager.updateAvailableDate(id, availableDate);
    }

    /**
     * Belirli bir ID'ye sahip müsait günü siler.
     *
     * @param id Silinecek müsait günün ID'si
     * @return NoContent durumunda HTTP yanıtı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailableDate(@PathVariable Long id) {
        availableDateManager.deleteAvailableDate(id);
        return ResponseEntity.noContent().build();
    }
}
