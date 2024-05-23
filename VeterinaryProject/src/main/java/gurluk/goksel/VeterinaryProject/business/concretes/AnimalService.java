package gurluk.goksel.VeterinaryProject.business.concretes;

import gurluk.goksel.VeterinaryProject.business.abstracts.IAnimalService;
import gurluk.goksel.VeterinaryProject.core.config.exeption.NotFoundException;
import gurluk.goksel.VeterinaryProject.core.config.utiles.Msg;
import gurluk.goksel.VeterinaryProject.dao.AnimalRepository;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService implements IAnimalService {
    // AnimalRepo bağımlılığını enjekte etmek için constructor
    private final AnimalRepository animalRepo;

    // Constructor enjeksiyonu

    // Yeni bir hayvan kaydetmek için
    @Override
    public Animal save(Animal animal) {
        return animalRepo.save(animal);// AnimalRepo'nun save metodu kullanılır
    }

    // Hayvanı ID'ye göre getirmek için
    @Override
    public Animal get(Long id) {
        // AnimalRepo'daki findById kullanılır, eğer bulunamazsa NotFound exception fırlatılır
        return this.animalRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalı olarak hayvanları getirmek için
    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        // Sayfalama için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.animalRepo.findAll(pageable);// AnimalRepo'nun findAll metodu kullanılır
    }

    // Hayvanı güncellemek için
    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal);// AnimalRepo'nun save metodu kullanılır
    }

    // Hayvanı silmek için
    @Override
    public boolean delete(long id) {// ID'ye göre hayvan getirilir
        Animal animal =this.get(id); // AnimalRepo'nun delete metodu ile silinir
        this.animalRepo.delete(animal);// Silme işlemi başarılı olduğu için true döndürülür
        return true;
    }

    @Override
    public List<Animal> getAnimalsByName(String name) {
        return this.animalRepo.findByNameIgnoreCaseContaining(name);
    }
    @Override
    public List<Animal> getAnimalsByCustomerId(Long customerId) {
        return this.animalRepo.findByCustomerId(customerId);
    }

    @Override
    public Optional<Animal> getAnimalByName(String name) {
            return animalRepo.findByName(name);
        }
    }


