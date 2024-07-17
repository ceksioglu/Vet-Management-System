package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.entity.AvailableDate;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.repository.AvailableDateRepository;
import ceksioglu.vet_management_sys.service.abstracts.AvailableDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AvailableDateManager, AvailableDateService arayüzünü implement eden sınıftır.
 * Doktorların müsait günleriyle ilgili iş mantığını içerir.
 */
@Service
public class AvailableDateManager implements AvailableDateService {

    private final AvailableDateRepository availableDateRepository;

    @Autowired
    public AvailableDateManager(AvailableDateRepository availableDateRepository) {
        this.availableDateRepository = availableDateRepository;
    }

    /**
     * Tüm müsait günleri getirir.
     *
     * @return Tüm müsait günlerin listesi
     */
    @Override
    public List<AvailableDate> getAllAvailableDates() {
        return availableDateRepository.findAll();
    }

    /**
     * Belirli bir ID'ye sahip müsait günü getirir.
     *
     * @param id Müsait günün ID'si
     * @return Müsait gün nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip müsait gün bulunamadığında fırlatılır
     */
    @Override
    public AvailableDate getAvailableDateById(Long id) {
        return availableDateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Uyan tarih bulunamadı. " + id));
    }

    /**
     * Yeni bir müsait gün oluşturur.
     *
     * @param availableDate Oluşturulacak müsait gün nesnesi
     * @return Oluşturulan müsait gün nesnesi
     */
    @Override
    public AvailableDate createAvailableDate(AvailableDate availableDate) {
        return availableDateRepository.save(availableDate);
    }

    /**
     * Belirli bir ID'ye sahip müsait günü günceller.
     *
     * @param id Güncellenecek müsait günün ID'si
     * @param updatedAvailableDate Güncellenmiş müsait gün nesnesi
     * @return Güncellenmiş müsait gün nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip müsait gün bulunamadığında fırlatılır
     */
    @Override
    public AvailableDate updateAvailableDate(Long id, AvailableDate updatedAvailableDate) {
        return availableDateRepository.findById(id).map(availableDate -> {
            availableDate.setAvailableDate(updatedAvailableDate.getAvailableDate());
            return availableDateRepository.save(availableDate);
        }).orElseThrow(() -> new ResourceNotFoundException("Uyan tarih bulunamadı. " + id));
    }

    /**
     * Belirli bir ID'ye sahip müsait günü siler.
     *
     * @param id Silinecek müsait günün ID'si
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip müsait gün bulunamadığında fırlatılır
     */
    @Override
    public void deleteAvailableDate(Long id) {
        if (!availableDateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Uyan tarih bulunamadı. " + id);
        }
        availableDateRepository.deleteById(id);
    }
}
