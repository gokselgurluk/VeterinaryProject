package gurluk.goksel.VeterinaryProject.business.abstracts;

import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IAnimalService {
    Animal save(Animal animal);
    Animal get(Long id);
    Page<Animal> cursor(int page, int pageSize);
    Animal update (Animal animal);
    boolean delete (long id);
    List<Animal> getAnimalsByName(String name);
    List<Animal> getAnimalsByCustomerId(Long customerId);
    Optional<Animal> getAnimalByName(String name);
}
