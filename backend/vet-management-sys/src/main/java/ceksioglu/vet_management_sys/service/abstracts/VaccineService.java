package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.dto.VaccineDTO;
import java.util.Date;
import java.util.List;

public interface VaccineService {
    VaccineDTO saveVaccine(VaccineDTO vaccineDTO);
    VaccineDTO updateVaccine(Long id, VaccineDTO vaccineDTO);
    void deleteVaccine(Long id);
    VaccineDTO getVaccineById(Long id);
    List<VaccineDTO> getAllVaccines();
    List<VaccineDTO> getVaccinesByAnimalId(Long animalId);
    List<VaccineDTO> getVaccinesByProtectionEndDateRange(Date startDate, Date endDate);
}