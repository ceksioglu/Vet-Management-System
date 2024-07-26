package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.dto.DoctorDTO;
import java.util.List;

public interface DoctorService {
    DoctorDTO saveDoctor(DoctorDTO doctorDTO);
    DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO);
    void deleteDoctor(Long id);
    DoctorDTO getDoctorById(Long id);
    List<DoctorDTO> getAllDoctors();
}