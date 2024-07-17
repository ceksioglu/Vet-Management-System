package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.entity.AvailableDate;

import java.util.List;

public interface AvailableDateService {
    List<AvailableDate> getAllAvailableDates();
    AvailableDate getAvailableDateById(Long id);
    AvailableDate createAvailableDate(AvailableDate availableDate);
    AvailableDate updateAvailableDate(Long id, AvailableDate availableDate);
    void deleteAvailableDate(Long id);
}
