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

@Service
public class DoctorManager implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorManager(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorDTO saveDoctor(DoctorDTO doctorDTO) {
        if (doctorRepository.existsByMail(doctorDTO.getMail())) {
            throw new ResourceAlreadyExistsException("Doctor with this email already exists");
        }
        Doctor doctor = convertToEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

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

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return convertToDTO(doctor);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

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