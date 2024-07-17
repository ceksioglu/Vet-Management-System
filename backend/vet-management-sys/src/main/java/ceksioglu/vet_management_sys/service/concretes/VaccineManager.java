package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.entity.Vaccine;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.repository.VaccineRepository;
import ceksioglu.vet_management_sys.service.abstracts.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VaccineManager, VaccineService arayüzünü implement eden sınıftır.
 * Aşılarla ilgili iş mantığını içerir.
 */
@Service
public class VaccineManager implements VaccineService {

    private final VaccineRepository vaccineRepository;

    @Autowired
    public VaccineManager(VaccineRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    /**
     * Tüm aşıları getirir.
     *
     * @return Tüm aşıların listesi
     */
    @Override
    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    /**
     * Belirli bir ID'ye sahip aşıyı getirir.
     *
     * @param id Aşının ID'si
     * @return Aşı nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip aşı bulunamadığında fırlatılır
     */
    @Override
    public Vaccine getVaccineById(Long id) {
        return vaccineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bu ID'ye sahip aşı bulunamadı: " + id));
    }

    /**
     * Yeni bir aşı oluşturur.
     *
     * @param vaccine Oluşturulacak aşı nesnesi
     * @return Oluşturulan aşı nesnesi
     */
    @Override
    public Vaccine createVaccine(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }

    /**
     * Belirli bir ID'ye sahip aşıyı günceller.
     *
     * @param id Güncellenecek aşının ID'si
     * @param updatedVaccine Güncellenmiş aşı nesnesi
     * @return Güncellenmiş aşı nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip aşı bulunamadığında fırlatılır
     */
    @Override
    public Vaccine updateVaccine(Long id, Vaccine updatedVaccine) {
        return vaccineRepository.findById(id).map(vaccine -> {
            vaccine.setName(updatedVaccine.getName());
            vaccine.setCode(updatedVaccine.getCode());
            vaccine.setProtectionStartDate(updatedVaccine.getProtectionStartDate());
            vaccine.setProtectionFinishDate(updatedVaccine.getProtectionFinishDate());
            return vaccineRepository.save(vaccine);
        }).orElseThrow(() -> new ResourceNotFoundException("Bu ID'ye sahip aşı bulunamadı: " + id));
    }

    /**
     * Belirli bir ID'ye sahip aşıyı siler.
     *
     * @param id Silinecek aşının ID'si
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip aşı bulunamadığında fırlatılır
     */
    @Override
    public void deleteVaccine(Long id) {
        if (!vaccineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bu ID'ye sahip aşı bulunamadı: " + id);
        }
        vaccineRepository.deleteById(id);
    }
}
