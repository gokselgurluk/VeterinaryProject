package gurluk.goksel.VeterinaryProject.business.concretes;

import gurluk.goksel.VeterinaryProject.business.abstracts.IVaccineService;
import gurluk.goksel.VeterinaryProject.core.config.exeption.NotFoundException;
import gurluk.goksel.VeterinaryProject.core.config.utiles.Msg;
import gurluk.goksel.VeterinaryProject.dao.AnimalRepository;
import gurluk.goksel.VeterinaryProject.dao.VaccineRepository;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Vaccine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaccineService implements IVaccineService {
    // VaccineRepo bağımlılığını enjekte etmek için constructor
    private final VaccineRepository vaccineRepo;
    private final AnimalRepository animalRepo;
    @Override
    public Vaccine save(Vaccine vaccine, Long animalId) {
       log.info("vaccine : {},animalId : {}",vaccine,animalId);
        Animal animal = animalRepo.findById(animalId)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        vaccine.setAnimal(animal);
        log.info("animal : {}",animal);
        return vaccineRepo.save(vaccine);
    }

    // Aşıyı ID'ye göre getirmek için
    @Override
    public Vaccine get(Long id) {
        return this.vaccineRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalı olarak aşıları getirmek için
    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.vaccineRepo.findAll(pageable);
    }

    // Aşıyı güncellemek için
    @Override
    public Vaccine update(Vaccine vaccine) {
        this.get(vaccine.getId());
        return this.vaccineRepo.save(vaccine);
    }

    // Aşıyı silmek için
    @Override
    public boolean delete(long id) {
        Vaccine vaccine = this.get(id);
        this.vaccineRepo.delete(vaccine);
        return true;
    }

    // Hayvan ID'sine göre aşıları getirmek için
    @Override
    public List<Vaccine> getVaccinesByAnimalId(Long animalId) {
        return vaccineRepo.findByAnimalId(animalId);
    }

    @Override
    public List<Vaccine> getVaccinesByFinishDateRange(LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, "Start date cannot be null");
        Objects.requireNonNull(endDate, "End date cannot be null");

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        return vaccineRepo.findByProtectionFinishDateBetween(startDate, endDate);
    }

    @Override
    public List<Vaccine> getByProtectionStartDateBetween(LocalDate startDate, LocalDate endDate) {
        return vaccineRepo.findByProtectionStartDateAndFinishDateBetween(startDate, endDate);
    }

    @Override
    public List<Vaccine> findByAnimalIdAndProtectionStartDateBetween(Long animalId, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(animalId, "Animal ID cannot be null");
        Objects.requireNonNull(startDate, "Start date cannot be null");
        Objects.requireNonNull(endDate, "End date cannot be null");

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        return vaccineRepo.findByAnimalIdAndProtectionFinishDateBetween(animalId, startDate, endDate);
    }
@Override
    public boolean existsActiveVaccineByAnimalIdAndVaccineCode(Long animalId, String vaccineCode) {
        // Belirtilen hayvan ID'sine ve aşı koduna sahip aktif bir aşının varlığını kontrol et
        return vaccineRepo.existsByAnimalIdAndCodeAndProtectionFinishDateAfter(animalId, vaccineCode, LocalDate.now());
    }


}
