package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.entity.Appointment;
import ceksioglu.vet_management_sys.service.concretes.AppointmentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * AppointmentController, randevularla ilgili API endpointlerini sağlar.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentManager appointmentManager;

    @Autowired
    public AppointmentController(AppointmentManager appointmentManager) {
        this.appointmentManager = appointmentManager;
    }

    /**
     * Tüm randevuları getirir.
     *
     * @return Tüm randevuların listesi
     */
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentManager.getAllAppointments();
    }

    /**
     * Belirli bir ID'ye sahip randevuyu getirir.
     *
     * @param id Randevunun ID'si
     * @return Randevu nesnesi
     */
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentManager.getAppointmentById(id);
    }

    /**
     * Yeni bir randevu oluşturur.
     *
     * @param appointment Oluşturulacak randevu nesnesi
     * @return Oluşturulan randevu nesnesi
     */
    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentManager.createAppointment(appointment);
    }

    /**
     * Belirli bir ID'ye sahip randevuyu günceller.
     *
     * @param id Güncellenecek randevunun ID'si
     * @param appointment Güncellenmiş randevu nesnesi
     * @return Güncellenmiş randevu nesnesi
     */
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        return appointmentManager.updateAppointment(id, appointment);
    }

    /**
     * Belirli bir ID'ye sahip randevuyu siler.
     *
     * @param id Silinecek randevunun ID'si
     * @return NoContent durumunda HTTP yanıtı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentManager.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Belirli bir doktor ve tarih aralığı için randevuları getirir.
     *
     * @param doctorId Doktorun ID'si
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belirli bir tarih aralığında ve doktora ait randevuların listesi
     */
    @GetMapping("/by-doctor-and-date-range")
    public List<Appointment> getAppointmentsByDoctorAndDateRange(@RequestParam Long doctorId, @RequestParam Date startDate, @RequestParam Date endDate) {
        return appointmentManager.getAppointmentsByDoctorAndDateRange(doctorId, startDate, endDate);
    }

    /**
     * Belirli bir hayvan ve tarih aralığı için randevuları getirir.
     *
     * @param animalId Hayvanın ID'si
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belirli bir tarih aralığında ve hayvana ait randevuların listesi
     */
    @GetMapping("/by-animal-and-date-range")
    public List<Appointment> getAppointmentsByAnimalAndDateRange(@RequestParam Long animalId, @RequestParam Date startDate, @RequestParam Date endDate) {
        return appointmentManager.getAppointmentsByAnimalAndDateRange(animalId, startDate, endDate);
    }
}
