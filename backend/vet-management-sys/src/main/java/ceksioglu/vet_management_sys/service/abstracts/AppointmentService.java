package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.entity.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Long id);
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointment(Long id, Appointment appointment);
    void deleteAppointment(Long id);

    List<Appointment> getAppointmentsByDoctorAndDateRange(Long doctorId, Date startDate, Date endDate);
    List<Appointment> getAppointmentsByAnimalAndDateRange(Long animalId, Date startDate, Date endDate);
}
