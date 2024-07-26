package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.dto.AppointmentDTO;
import ceksioglu.vet_management_sys.entity.Appointment;
import ceksioglu.vet_management_sys.entity.Animal;
import ceksioglu.vet_management_sys.entity.Doctor;
import ceksioglu.vet_management_sys.repository.AppointmentRepository;
import ceksioglu.vet_management_sys.repository.AnimalRepository;
import ceksioglu.vet_management_sys.repository.DoctorRepository;
import ceksioglu.vet_management_sys.service.abstracts.AppointmentService;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.core.exception.AppointmentConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AnimalRepository animalRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentManager(AppointmentRepository appointmentRepository,
                              AnimalRepository animalRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.animalRepository = animalRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) {
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + appointmentDTO.getDoctorId()));

        Animal animal = animalRepository.findById(appointmentDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + appointmentDTO.getAnimalId()));

        if (isAppointmentConflict(appointmentDTO)) {
            throw new AppointmentConflictException("Appointment conflicts with an existing appointment");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(savedAppointment);
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + appointmentDTO.getDoctorId()));

        Animal animal = animalRepository.findById(appointmentDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + appointmentDTO.getAnimalId()));

        if (isAppointmentConflict(appointmentDTO) && !appointment.getId().equals(id)) {
            throw new AppointmentConflictException("Appointment conflicts with an existing appointment");
        }

        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(updatedAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return convertToDTO(appointment);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDateRangeAndAnimal(Date startDate, Date endDate, Long animalId) {
        return appointmentRepository.findByAppointmentDateBetweenAndAnimalId(startDate, endDate, animalId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDateRangeAndDoctor(Date startDate, Date endDate, Long doctorId) {
        return appointmentRepository.findByAppointmentDateBetweenAndDoctorId(startDate, endDate, doctorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private boolean isAppointmentConflict(AppointmentDTO appointmentDTO) {
        List<Appointment> conflictingAppointments = appointmentRepository.findByDoctorIdAndAppointmentDate(
                appointmentDTO.getDoctorId(), appointmentDTO.getAppointmentDate());
        return !conflictingAppointments.isEmpty();
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setAnimalId(appointment.getAnimal().getId());
        return dto;
    }
}