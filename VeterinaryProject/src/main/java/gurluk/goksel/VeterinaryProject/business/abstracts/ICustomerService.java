package gurluk.goksel.VeterinaryProject.business.abstracts;

import gurluk.goksel.VeterinaryProject.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Customer save(Customer customer);
    Customer get(long id);
    Page<Customer> cursor(int page, int pageSize);
    Customer update (Customer customer);
    boolean delete (long id);

    boolean existsByMail(String email);
    List<Customer> getCustomersByName(String name);
    Optional<Customer> getCustomerByName(String name);
}
