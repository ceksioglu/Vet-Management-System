package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.entity.Doctor;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.repository.DoctorRepository;
import ceksioglu.vet_management_sys.service.abstracts.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DoctorManager, DoctorService arayüzünü implement eden sınıftır.
 * Doktorlarla ilgili iş mantığını içerir.
 */
@Service
public class DoctorManager implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorManager(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Tüm doktorları getirir.
     *
     * @return Tüm doktorların listesi
     */
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Belirli bir ID'ye sahip doktoru getirir.
     *
     * @param id Doktorun ID'si
     * @return Doktor nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip doktor bulunamadığında fırlatılır
     */
    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bu ID'ye ait doktor bulunamadı: " + id));
    }

    /**
     * Yeni bir doktor oluşturur.
     *
     * @param doctor Oluşturulacak doktor nesnesi
     * @return Oluşturulan doktor nesnesi
     */
    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    /**
     * Belirli bir ID'ye sahip doktoru günceller.
     *
     * @param id Güncellenecek doktorun ID'si
     * @param updatedDoctor Güncellenmiş doktor nesnesi
     * @return Güncellenmiş doktor nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip doktor bulunamadığında fırlatılır
     */
    @Override
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        return doctorRepository.findById(id).map(doctor -> {
            doctor.setName(updatedDoctor.getName());
            doctor.setPhone(updatedDoctor.getPhone());
            doctor.setMail(updatedDoctor.getMail());
            doctor.setAddress(updatedDoctor.getAddress());
            doctor.setCity(updatedDoctor.getCity());
            return doctorRepository.save(doctor);
        }).orElseThrow(() -> new ResourceNotFoundException("Bu ID'ye ait doktor bulunamadı: " + id));
    }

    /**
     * Belirli bir ID'ye sahip doktoru siler.
     *
     * @param id Silinecek doktorun ID'si
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip doktor bulunamadığında fırlatılır
     */
    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bu ID'ye ait doktor bulunamadı: " + id);
        }
        doctorRepository.deleteById(id);
    }
}
