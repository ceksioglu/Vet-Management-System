package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.dto.AppointmentDTO;
import ceksioglu.vet_management_sys.entity.Appointment;
import ceksioglu.vet_management_sys.entity.Animal;
import ceksioglu.vet_management_sys.entity.Doctor;
import ceksioglu.vet_management_sys.entity.AvailableDate;
import ceksioglu.vet_management_sys.repository.AppointmentRepository;
import ceksioglu.vet_management_sys.repository.AnimalRepository;
import ceksioglu.vet_management_sys.repository.DoctorRepository;
import ceksioglu.vet_management_sys.repository.AvailableDateRepository;
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
    private final AvailableDateRepository availableDateRepository;

    @Autowired
    public AppointmentManager(AppointmentRepository appointmentRepository,
                              AnimalRepository animalRepository,
                              DoctorRepository doctorRepository,
                              AvailableDateRepository availableDateRepository) {
        this.appointmentRepository = appointmentRepository;
        this.animalRepository = animalRepository;
        this.doctorRepository = doctorRepository;
        this.availableDateRepository = availableDateRepository;
    }

    @Override
    public AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) {
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + appointmentDTO.getDoctorId()));

        Animal animal = animalRepository.findById(appointmentDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + appointmentDTO.getAnimalId()));

        AvailableDate availableDate = availableDateRepository.findByDoctorIdAndAvailableDate(
                        doctor.getId(), appointmentDTO.getAppointmentDate())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor is not available on this date"));

        if (availableDate.getCurrentAppointmentCount() >= availableDate.getDailyAppointmentLimit()) {
            throw new AppointmentConflictException("Doctor has reached the daily appointment limit for this date");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Randevu sayısını artır
        availableDate.setCurrentAppointmentCount(availableDate.getCurrentAppointmentCount() + 1);
        availableDateRepository.save(availableDate);

        AppointmentDTO savedAppointmentDTO = convertToDTO(savedAppointment);
        savedAppointmentDTO.setAppointmentOrder(availableDate.getCurrentAppointmentCount());

        return savedAppointmentDTO;
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + appointmentDTO.getDoctorId()));

        Animal animal = animalRepository.findById(appointmentDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + appointmentDTO.getAnimalId()));

        AvailableDate availableDate = availableDateRepository.findByDoctorIdAndAvailableDate(
                        doctor.getId(), appointmentDTO.getAppointmentDate())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor is not available on this date"));

        if (!appointment.getDoctor().getId().equals(doctor.getId()) ||
                !appointment.getAppointmentDate().equals(appointmentDTO.getAppointmentDate())) {

            // Eski randevu tarihindeki sayıyı azalt
            AvailableDate oldAvailableDate = availableDateRepository.findByDoctorIdAndAvailableDate(
                            appointment.getDoctor().getId(), appointment.getAppointmentDate())
                    .orElseThrow(() -> new ResourceNotFoundException("Old available date not found"));
            oldAvailableDate.setCurrentAppointmentCount(oldAvailableDate.getCurrentAppointmentCount() - 1);
            availableDateRepository.save(oldAvailableDate);

            // Yeni tarih için kontrol
            if (availableDate.getCurrentAppointmentCount() >= availableDate.getDailyAppointmentLimit()) {
                throw new AppointmentConflictException("Doctor has reached the daily appointment limit for the new date");
            }

            // Yeni randevu tarihindeki sayıyı artır
            availableDate.setCurrentAppointmentCount(availableDate.getCurrentAppointmentCount() + 1);
            availableDateRepository.save(availableDate);
        }

        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        AppointmentDTO updatedAppointmentDTO = convertToDTO(updatedAppointment);
        updatedAppointmentDTO.setAppointmentOrder(availableDate.getCurrentAppointmentCount());

        return updatedAppointmentDTO;
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        // Randevu sayısını azalt
        AvailableDate availableDate = availableDateRepository.findByDoctorIdAndAvailableDate(
                        appointment.getDoctor().getId(), appointment.getAppointmentDate())
                .orElseThrow(() -> new ResourceNotFoundException("Available date not found"));
        availableDate.setCurrentAppointmentCount(availableDate.getCurrentAppointmentCount() - 1);
        availableDateRepository.save(availableDate);

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

    @Override
    public Integer getAppointmentOrder(Date appointmentDate, Long doctorId) {
        AvailableDate availableDate = availableDateRepository.findByDoctorIdAndAvailableDate(doctorId, appointmentDate)
                .orElseThrow(() -> new ResourceNotFoundException("Available date not found for doctor on this date"));
        return availableDate.getCurrentAppointmentCount();
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setAnimalId(appointment.getAnimal().getId());

        // Randevu sırasını bulmak için
        AvailableDate availableDate = availableDateRepository.findByDoctorIdAndAvailableDate(
                        appointment.getDoctor().getId(), appointment.getAppointmentDate())
                .orElseThrow(() -> new ResourceNotFoundException("Available date not found"));
        dto.setAppointmentOrder(availableDate.getCurrentAppointmentCount());

        return dto;
    }
}