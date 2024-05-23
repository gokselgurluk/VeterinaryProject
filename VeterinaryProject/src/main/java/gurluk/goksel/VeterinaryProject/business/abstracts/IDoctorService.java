package gurluk.goksel.VeterinaryProject.business.abstracts;

import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDoctorService {
    Doctor save(Doctor doctor);
    Doctor get(Long id);
    Page<Doctor> cursor(int page, int pageSize);
    Doctor update (Doctor doctor);
    List<Doctor> getDoctorByName(String name);
    boolean delete (long id);
}