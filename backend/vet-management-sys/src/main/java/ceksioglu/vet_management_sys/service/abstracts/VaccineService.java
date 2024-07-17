package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.entity.Vaccine;

import java.util.List;

public interface VaccineService {
    List<Vaccine> getAllVaccines();
    Vaccine getVaccineById(Long id);
    Vaccine createVaccine(Vaccine vaccine);
    Vaccine updateVaccine(Long id, Vaccine vaccine);
    void deleteVaccine(Long id);
}
