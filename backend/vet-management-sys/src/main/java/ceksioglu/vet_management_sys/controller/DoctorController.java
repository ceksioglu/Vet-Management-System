package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.entity.Doctor;
import ceksioglu.vet_management_sys.service.concretes.DoctorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DoctorController, doktorlarla ilgili API endpointlerini sağlar.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorManager doctorManager;

    @Autowired
    public DoctorController(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }

    /**
     * Tüm doktorları getirir.
     *
     * @return Tüm doktorların listesi
     */
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorManager.getAllDoctors();
    }

    /**
     * Belirli bir ID'ye sahip doktoru getirir.
     *
     * @param id Doktorun ID'si
     * @return Doktor nesnesi
     */
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorManager.getDoctorById(id);
    }

    /**
     * Yeni bir doktor oluşturur.
     *
     * @param doctor Oluşturulacak doktor nesnesi
     * @return Oluşturulan doktor nesnesi
     */
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorManager.createDoctor(doctor);
    }

    /**
     * Belirli bir ID'ye sahip doktoru günceller.
     *
     * @param id Güncellenecek doktorun ID'si
     * @param doctor Güncellenmiş doktor nesnesi
     * @return Güncellenmiş doktor nesnesi
     */
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorManager.updateDoctor(id, doctor);
    }

    /**
     * Belirli bir ID'ye sahip doktoru siler.
     *
     * @param id Silinecek doktorun ID'si
     * @return NoContent durumunda HTTP yanıtı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorManager.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
