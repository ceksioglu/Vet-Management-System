package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.entity.Appointment;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.core.exception.AppointmentConflictException;
import ceksioglu.vet_management_sys.repository.AppointmentRepository;
import ceksioglu.vet_management_sys.service.abstracts.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * AppointmentManager, AppointmentService arayüzünü implement eden sınıftır.
 * Randevularla ilgili iş mantığını içerir.
 */
@Service
public class AppointmentManager implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentManager(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Tüm randevuları getirir.
     *
     * @return Tüm randevuların listesi
     */
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Belirli bir ID'ye sahip randevuyu getirir.
     *
     * @param id Randevunun ID'si
     * @return Randevu nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip randevu bulunamadığında fırlatılır
     */
    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    /**
     * Yeni bir randevu oluşturur.
     *
     * @param appointment Oluşturulacak randevu nesnesi
     * @return Oluşturulan randevu nesnesi
     * @throws AppointmentConflictException Doktorun aynı saatte başka bir randevusu olduğunda fırlatılır
     */
    @Override
    public Appointment createAppointment(Appointment appointment) {
        if (appointmentRepository.existsByDoctorIdAndAppointmentDate(appointment.getDoctor().getId(), appointment.getAppointmentDate())) {
            throw new AppointmentConflictException("Doctor already has an appointment at this time.");
        }
        return appointmentRepository.save(appointment);
    }

    /**
     * Belirli bir ID'ye sahip randevuyu günceller.
     *
     * @param id Güncellenecek randevunun ID'si
     * @param updatedAppointment Güncellenmiş randevu nesnesi
     * @return Güncellenmiş randevu nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip randevu bulunamadığında fırlatılır
     * @throws AppointmentConflictException Doktorun aynı saatte başka bir randevusu olduğunda fırlatılır
     */
    @Override
    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        return appointmentRepository.findById(id).map(appointment -> {
            if (appointmentRepository.existsByDoctorIdAndAppointmentDate(updatedAppointment.getDoctor().getId(), updatedAppointment.getAppointmentDate())) {
                throw new AppointmentConflictException("Doctor already has an appointment at this time.");
            }
            appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
            appointment.setDoctor(updatedAppointment.getDoctor());
            appointment.setAnimal(updatedAppointment.getAnimal());
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    /**
     * Belirli bir ID'ye sahip randevuyu siler.
     *
     * @param id Silinecek randevunun ID'si
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip randevu bulunamadığında fırlatılır
     */
    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    /**
     * Belirli bir doktor ve tarih aralığı için randevuları getirir.
     *
     * @param doctorId Doktorun ID'si
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belirli bir tarih aralığında ve doktora ait randevuların listesi
     */
    @Override
    public List<Appointment> getAppointmentsByDoctorAndDateRange(Long doctorId, Date startDate, Date endDate) {
        return appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId, startDate, endDate);
    }

    /**
     * Belirli bir hayvan ve tarih aralığı için randevuları getirir.
     *
     * @param animalId Hayvanın ID'si
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belirli bir tarih aralığında ve hayvana ait randevuların listesi
     */
    @Override
    public List<Appointment> getAppointmentsByAnimalAndDateRange(Long animalId, Date startDate, Date endDate) {
        return appointmentRepository.findByAnimalIdAndAppointmentDateBetween(animalId, startDate, endDate);
    }
}
