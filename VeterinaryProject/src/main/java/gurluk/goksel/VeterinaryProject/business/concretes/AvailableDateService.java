package gurluk.goksel.VeterinaryProject.business.concretes;

import gurluk.goksel.VeterinaryProject.business.abstracts.IAvailableDateService;
import gurluk.goksel.VeterinaryProject.core.config.exeption.NotFoundException;
import gurluk.goksel.VeterinaryProject.core.config.utiles.Msg;
import gurluk.goksel.VeterinaryProject.dao.AvailableDateRepository;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.AvailableDate;
import gurluk.goksel.VeterinaryProject.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailableDateService implements IAvailableDateService {

    private final AvailableDateRepository availableDateRepo;

    public AvailableDateService(AvailableDateRepository availableDateRepo) {
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public AvailableDate save(AvailableDate availableDate) {
        return availableDateRepo.save(availableDate);
    }

    @Override
    public AvailableDate get(Long id) {
        return this.availableDateRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }
    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        // Sayfalama için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.availableDateRepo.findAll(pageable);// AnimalRepo'nun findAll metodu kullanılır
    }
    @Override
    public AvailableDate update(AvailableDate availableDate) {
        this.get(availableDate.getId());
        return this.availableDateRepo.save(availableDate);//  save metodu kullanılır
    }

    @Override
    public boolean delete(long id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }
    @Override
    public boolean isDoctorAvailableOnDate(Long doctorId, LocalDate date) {
        List<AvailableDate> availableDates = availableDateRepo.findByDoctorIdAndAvailableDate(doctorId, date);
        return !availableDates.isEmpty();
    }

    @Override
    public List<AvailableDate> getDatesByDoctorId(Long doctorId) {
        {
            return this.availableDateRepo.findByDoctorId(doctorId);
        }
    }
}
