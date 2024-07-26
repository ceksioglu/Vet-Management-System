package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.dto.AppointmentDTO;
import java.util.Date;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO);
    AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO);
    void deleteAppointment(Long id);
    AppointmentDTO getAppointmentById(Long id);
    List<AppointmentDTO> getAllAppointments();
    List<AppointmentDTO> getAppointmentsByDateRangeAndAnimal(Date startDate, Date endDate, Long animalId);
    List<AppointmentDTO> getAppointmentsByDateRangeAndDoctor(Date startDate, Date endDate, Long doctorId);
}