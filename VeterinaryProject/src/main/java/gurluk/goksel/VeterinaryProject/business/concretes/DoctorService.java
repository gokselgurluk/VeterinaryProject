package gurluk.goksel.VeterinaryProject.business.concretes;

import gurluk.goksel.VeterinaryProject.business.abstracts.IDoctorService;
import gurluk.goksel.VeterinaryProject.core.config.exeption.NotFoundException;
import gurluk.goksel.VeterinaryProject.core.config.utiles.Msg;
import gurluk.goksel.VeterinaryProject.dao.DoctorRepository;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService implements IDoctorService {
    // DoctorRepo bağımlılığını enjekte etmek için constructor
    private final DoctorRepository doctorRepo;

    // Constructor enjeksiyonu
    public DoctorService(DoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    // Doktoru kaydetmek için
    @Override
    public Doctor save(Doctor doctor) {
        return this.doctorRepo.save(doctor); // DoctorRepo'nun save metodu kullanılır
    }

    // Doktoru ID'ye göre getirmek için
    @Override
    public Doctor get(Long id) {
        // DoctorRepo'daki findById kullanılır, eğer bulunamazsa NotFound exception fırlatılır
        return this.doctorRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalı olarak doktorları getirmek için
    @Override
    public Page<Doctor> cursor(int page, int pageSize) {
        // Sayfalama yapmak için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.doctorRepo.findAll(pageable);// DoctorRepo'nun findAll metodu kullanılır
    }
    @Override
    public List<Doctor> getDoctorByName(String name) {
        return this.doctorRepo.findByNameIgnoreCaseContaining(name);
    }
    // Doktoru güncellemek için
    @Override
    public Doctor update(Doctor doctor) {
        this.get(doctor.getId());
        return this.doctorRepo.save(doctor);// DoctorRepo'nun save metodu kullanılır
    }

    // Doktoru silmek için
    @Override
    public boolean delete(long id) {
        Doctor doctor=this.get(id);
        this.doctorRepo.delete(doctor);// DoctorRepo'nun delete metodu ile silinir
        return true;// Silme işlemi başarılı olduğu için true döndürülür
    }
}
