package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.dto.DoctorDTO;
import ceksioglu.vet_management_sys.entity.Doctor;
import ceksioglu.vet_management_sys.repository.DoctorRepository;
import ceksioglu.vet_management_sys.service.abstracts.DoctorService;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.core.exception.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing doctors.
 */
@Service
public class DoctorManager implements DoctorService {

    private final DoctorRepository doctorRepository;

    /**
     * Constructor for DoctorManager.
     *
     * @param doctorRepository the doctor repository
     */
    @Autowired
    public DoctorManager(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Saves a doctor.
     *
     * @param doctorDTO the doctor DTO
     * @return the saved doctor DTO
     * @throws ResourceAlreadyExistsException if a doctor with the same email already exists
     */
    @Override
    public DoctorDTO saveDoctor(DoctorDTO doctorDTO) {
        if (doctorRepository.existsByMail(doctorDTO.getMail())) {
            throw new ResourceAlreadyExistsException("Doctor with this email already exists");
        }
        Doctor doctor = convertToEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

    /**
     * Updates a doctor.
     *
     * @param id the doctor ID
     * @param doctorDTO the doctor DTO
     * @return the updated doctor DTO
     * @throws ResourceNotFoundException if the doctor is not found
     * @throws ResourceAlreadyExistsException if a doctor with the same email already exists
     */
    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        if (!existingDoctor.getMail().equals(doctorDTO.getMail()) &&
                doctorRepository.existsByMail(doctorDTO.getMail())) {
            throw new ResourceAlreadyExistsException("Doctor with this email already exists");
        }

        existingDoctor.setName(doctorDTO.getName());
        existingDoctor.setPhone(doctorDTO.getPhone());
        existingDoctor.setMail(doctorDTO.getMail());
        existingDoctor.setAddress(doctorDTO.getAddress());
        existingDoctor.setCity(doctorDTO.getCity());

        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        return convertToDTO(updatedDoctor);
    }

    /**
     * Deletes a doctor by ID.
     *
     * @param id the doctor ID
     * @throws ResourceNotFoundException if the doctor is not found
     */
    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    /**
     * Gets a doctor by ID.
     *
     * @param id the doctor ID
     * @return the doctor DTO
     * @throws ResourceNotFoundException if the doctor is not found
     */
    @Override
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return convertToDTO(doctor);
    }

    /**
     * Gets all doctors.
     *
     * @return the list of doctor DTOs
     */
    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a doctor entity to a DTO.
     *
     * @param doctor the doctor entity
     * @return the doctor DTO
     */
    private DoctorDTO convertToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setPhone(doctor.getPhone());
        dto.setMail(doctor.getMail());
        dto.setAddress(doctor.getAddress());
        dto.setCity(doctor.getCity());
        return dto;
    }

    /**
     * Converts a doctor DTO to an entity.
     *
     * @param dto the doctor DTO
     * @return the doctor entity
     */
    private Doctor convertToEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setPhone(dto.getPhone());
        doctor.setMail(dto.getMail());
        doctor.setAddress(dto.getAddress());
        doctor.setCity(dto.getCity());
        return doctor;
    }
}
