package gurluk.goksel.VeterinaryProject.dao;

import gurluk.goksel.VeterinaryProject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByNameContainingIgnoreCase(String name);
    boolean existsByMail(String email);
    Optional<Customer> findByName(String name);

}
