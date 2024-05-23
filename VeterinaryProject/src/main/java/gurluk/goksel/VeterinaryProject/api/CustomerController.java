package gurluk.goksel.VeterinaryProject.api;


import gurluk.goksel.VeterinaryProject.business.abstracts.ICustomerService;
import gurluk.goksel.VeterinaryProject.core.config.EmailAlreadyRegisteredException;
import gurluk.goksel.VeterinaryProject.core.config.modelMapper.IModelMapperService;
import gurluk.goksel.VeterinaryProject.core.config.result.Result;
import gurluk.goksel.VeterinaryProject.core.config.result.ResultData;
import gurluk.goksel.VeterinaryProject.core.config.utiles.ResultHelper;
import gurluk.goksel.VeterinaryProject.dto.CursorResponse;
import gurluk.goksel.VeterinaryProject.dto.request.Customer.CustomerSaveRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Customer.CustomerUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.response.CustomerResponse;
import gurluk.goksel.VeterinaryProject.entity.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
    public class CustomerController {
        private final ICustomerService customerService;
        private final IModelMapperService modelMapper;

    // Yeni bir müşteri kaydetmek için POST endpoint'i
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest ){
        if (customerService.existsByMail(customerSaveRequest.getMail())) {
            throw new EmailAlreadyRegisteredException("Email is already registered");
        }
        Customer saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest,Customer.class);
        this.customerService.save(saveCustomer);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveCustomer,CustomerResponse.class));
    }


    // Müşteri detaylarını almak için GET endpoint'i
    @GetMapping("/filter/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get (@PathVariable("id") long id) {
        Customer customer = this.customerService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(customer,CustomerResponse.class));
    }

    // Müşteri bilgilerini güncellemek için PUT endpoint'i
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest ){
        Customer updateCustomer = this.modelMapper.forRequest().map(customerUpdateRequest,Customer.class);
        this.customerService.update(updateCustomer);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateCustomer,CustomerResponse.class));
    }
    // Müşteriyi silmek için DELETE endpoint'i
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        this.customerService.delete(id);
        return ResultHelper.Ok();
    }

    // Sayfalı olarak müşterileri listelemek için GET endpoint'i
    @GetMapping("/List")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page", required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false,defaultValue = "5") int pageSize
    ){
        // Sayfalama için ilgili servis metodu çağrılıyor
        Page<Customer> categoryPage = this.customerService.cursor(page,pageSize);
        Page<CustomerResponse> customerResponsePage = categoryPage
                .map(category -> this.modelMapper.forResponse().map(category,CustomerResponse.class));
        // Sonuç dönüşü yapılıyor
        return  ResultHelper.cursor(customerResponsePage);
    }
    //müşterileri isme göre filtreleyen bir endpoint
    @GetMapping("/filter/Name") //http://localhost:8047/v1/customers/filter?name=John Doe örnek aram URL'si
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> getCustomersByName(@RequestParam("name") String name) {
        List<Customer> customers = this.customerService.getCustomersByName(name);
// Eğer randevu bulunamadıysa
        if (customers.isEmpty()) {
            return ResultHelper.errorWithData("Belirtilen isim bulunamadı.", null, HttpStatus.NOT_FOUND);
        }
        // Bu isme göre filtrelenmiş müşterileri CustomerResponse formatına dönüştürüyoruz
        List<CustomerResponse> customerResponses = customers.stream()
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(customerResponses);
    }

}
