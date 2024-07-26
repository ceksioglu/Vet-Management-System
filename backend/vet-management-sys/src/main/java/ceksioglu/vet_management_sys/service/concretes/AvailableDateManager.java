package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.dto.AvailableDateDTO;
import ceksioglu.vet_management_sys.entity.AvailableDate;
import ceksioglu.vet_management_sys.entity.Doctor;
import ceksioglu.vet_management_sys.repository.AvailableDateRepository;
import ceksioglu.vet_management_sys.repository.DoctorRepository;
import ceksioglu.vet_management_sys.service.abstracts.AvailableDateService;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.core.exception.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailableDateManager implements AvailableDateService {

    private final AvailableDateRepository availableDateRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AvailableDateManager(AvailableDateRepository availableDateRepository, DoctorRepository doctorRepository) {
        this.availableDateRepository = availableDateRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public AvailableDateDTO saveAvailableDate(AvailableDateDTO availableDateDTO) {
        Doctor doctor = doctorRepository.findById(availableDateDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + availableDateDTO.getDoctorId()));

        if (availableDateRepository.existsByDoctorIdAndAvailableDate(doctor.getId(), availableDateDTO.getAvailableDate())) {
            throw new ResourceAlreadyExistsException("This date is already available for the doctor");
        }

        AvailableDate availableDate = new AvailableDate();
        availableDate.setAvailableDate(availableDateDTO.getAvailableDate());
        availableDate.setDoctor(doctor);
        availableDate.setDailyAppointmentLimit(availableDateDTO.getDailyAppointmentLimit());
        availableDate.setCurrentAppointmentCount(0);

        AvailableDate savedAvailableDate = availableDateRepository.save(availableDate);
        return convertToDTO(savedAvailableDate);
    }

    @Override
    public AvailableDateDTO updateAvailableDate(Long id, AvailableDateDTO availableDateDTO) {
        AvailableDate availableDate = availableDateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Available date not found with id: " + id));

        Doctor doctor = doctorRepository.findById(availableDateDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + availableDateDTO.getDoctorId()));

        if (!availableDate.getDoctor().getId().equals(doctor.getId()) &&
                availableDateRepository.existsByDoctorIdAndAvailableDate(doctor.getId(), availableDateDTO.getAvailableDate())) {
            throw new ResourceAlreadyExistsException("This date is already available for the doctor");
        }

        availableDate.setAvailableDate(availableDateDTO.getAvailableDate());
        availableDate.setDoctor(doctor);
        availableDate.setDailyAppointmentLimit(availableDateDTO.getDailyAppointmentLimit());
        // Mevcut randevu sayısını koruyoruz
        // availableDate.setCurrentAppointmentCount(availableDateDTO.getCurrentAppointmentCount());

        AvailableDate updatedAvailableDate = availableDateRepository.save(availableDate);
        return convertToDTO(updatedAvailableDate);
    }

    @Override
    public void deleteAvailableDate(Long id) {
        if (!availableDateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Available date not found with id: " + id);
        }
        availableDateRepository.deleteById(id);
    }

    @Override
    public AvailableDateDTO getAvailableDateById(Long id) {
        AvailableDate availableDate = availableDateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Available date not found with id: " + id));
        return convertToDTO(availableDate);
    }

    @Override
    public List<AvailableDateDTO> getAllAvailableDates() {
        return availableDateRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailableDateDTO> getAvailableDatesByDoctorId(Long doctorId) {
        return availableDateRepository.findByDoctorId(doctorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getCurrentAppointmentCount(Date date, Long doctorId) {
        AvailableDate availableDate = availableDateRepository.findByDoctorIdAndAvailableDate(doctorId, date)
                .orElseThrow(() -> new ResourceNotFoundException("Available date not found for doctor on this date"));
        return availableDate.getCurrentAppointmentCount();
    }

    private AvailableDateDTO convertToDTO(AvailableDate availableDate) {
        AvailableDateDTO dto = new AvailableDateDTO();
        dto.setId(availableDate.getId());
        dto.setAvailableDate(availableDate.getAvailableDate());
        dto.setDoctorId(availableDate.getDoctor().getId());
        dto.setDailyAppointmentLimit(availableDate.getDailyAppointmentLimit());
        dto.setCurrentAppointmentCount(availableDate.getCurrentAppointmentCount());
        return dto;
    }
}