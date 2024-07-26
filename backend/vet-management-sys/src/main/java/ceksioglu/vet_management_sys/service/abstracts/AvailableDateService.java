package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.dto.AvailableDateDTO;
import java.util.List;

public interface AvailableDateService {
    AvailableDateDTO saveAvailableDate(AvailableDateDTO availableDateDTO);
    AvailableDateDTO updateAvailableDate(Long id, AvailableDateDTO availableDateDTO);
    void deleteAvailableDate(Long id);
    AvailableDateDTO getAvailableDateById(Long id);
    List<AvailableDateDTO> getAllAvailableDates();
    List<AvailableDateDTO> getAvailableDatesByDoctorId(Long doctorId);
}