package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for Appointment entity.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAppointmentDateBetweenAndAnimalId(Date startDate, Date endDate, Long animalId);
    List<Appointment> findByAppointmentDateBetweenAndDoctorId(Date startDate, Date endDate, Long doctorId);
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, Date appointmentDate);
}