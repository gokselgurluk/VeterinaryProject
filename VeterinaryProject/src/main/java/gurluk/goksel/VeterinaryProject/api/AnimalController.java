package gurluk.goksel.VeterinaryProject.api;

import gurluk.goksel.VeterinaryProject.business.abstracts.IAnimalService;
import gurluk.goksel.VeterinaryProject.business.abstracts.ICustomerService;
import gurluk.goksel.VeterinaryProject.core.config.modelMapper.IModelMapperService;
import gurluk.goksel.VeterinaryProject.core.config.result.Result;
import gurluk.goksel.VeterinaryProject.core.config.result.ResultData;
import gurluk.goksel.VeterinaryProject.core.config.utiles.ResultHelper;
import gurluk.goksel.VeterinaryProject.dto.CursorResponse;
import gurluk.goksel.VeterinaryProject.dto.request.Animal.AnimalSaveRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Animal.AnimalUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.response.AnimalResponse;
import gurluk.goksel.VeterinaryProject.dto.response.VaccineResponse;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Customer;
import gurluk.goksel.VeterinaryProject.entity.Vaccine;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final IAnimalService animalService;
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;

    // Constructor-based dependency injection

    // Belirli bir hayvanın detaylarını getiren endpoint
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get (@PathVariable("id") Long id) {
        Animal animal = this.animalService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(animal,AnimalResponse.class));
    }

    // Yeni hayvan ekleme endpoint'i
  // 12   Proje isterlerine göre hayvan kaydediliyor mu? (4 puan)
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest ){
        Animal saveAnimal = this.modelMapper.forRequest().map(animalSaveRequest,Animal.class);

        Customer customer =this.customerService.get(animalSaveRequest.getCustomerId());
        saveAnimal.setCustomer(customer);

        this.animalService.save(saveAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal,AnimalResponse.class));
    }

    // Hayvan güncelleme endpoint'i
    // Hayvan güncelleme endpoint'i
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest ){
        // Güncellenen hayvanı oluştur
        Animal updateAnimal = this.modelMapper.forRequest().map(animalUpdateRequest,Animal.class);

        // Güncellenen hayvanın müşteri ID'sini al
        long customerId = animalUpdateRequest.getCustomerId();

        // Müşteriyi veritabanından al
        Customer customer = this.customerService.get(customerId);

        // Güncellenen hayvana müşteriyi ata
        updateAnimal.setCustomer(customer);

        // Hayvan güncelleme işlemini yap
        this.animalService.update(updateAnimal);

        // Sonuç döndür
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAnimal,AnimalResponse.class));
    }

    // Hayvan silme endpoint'i
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.animalService.delete(id);
        return ResultHelper.Ok();
    }
    // Hayvana ait aşıları getiren endpoint
    @GetMapping("/vaccines/List/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesForAnimal(@PathVariable("id") Long id) {
        Animal animal = this.animalService.get(id);

        // Burada, animal.getVaccines() şeklinde hayvana ait tüm aşı kayıtlarını alabilirsiniz
        List<Vaccine> vaccines = animal.getVaccines();
        // Eğer randevu bulunamadıysa
        if (vaccines.isEmpty()) {
            return ResultHelper.errorWithData("Belirtilen hayvan için aşı bulunamadı.", null, HttpStatus.NOT_FOUND);
        }

        // Bu aşı kayıtlarını VaccineResponse'a dönüştürerek döndürebilirsiniz
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(vaccineResponses);
    }

    @GetMapping("/All/List")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false,defaultValue = "10") int pageSize
    ){
        // Sayfalama için ilgili servis metodu çağrılıyor
        Page<Animal> animalPage = this.animalService.cursor(page,pageSize);
        Page<AnimalResponse> animalResponsePage = animalPage
                .map(animal -> this.modelMapper.forResponse().map(animal,AnimalResponse.class));
        // Sonuç dönüşü yapılıyor
        return  ResultHelper.cursor(animalResponsePage);
    }
    // 13 Hayvanlar isme göre filtreleniyor mu? (4 Puan)
    @GetMapping("/filter/animal/{name}") //http://localhost:8047/v1/animals/filter?name=
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByName(@RequestParam("name") String name) {
        List<Animal> animals = this.animalService.getAnimalsByName(name);

        // İsmine göre filtrelenmiş hayvanları AnimalResponse formatına dönüştürüyoruz
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(animalResponses);
    }
    // 14 Girilen hayvan sahibinin sistemde kayıtlı tüm hayvanlarını görüntüleme (sadece bir kişiye ait hayvanları görüntüle işlemi) başarılı bir şekilde çalışıyor mu? (4 Puan)
    @GetMapping("/filterCustomerId/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<Animal> animals = this.animalService.getAnimalsByCustomerId(customerId);

        // Sahibinin hayvanlarını AnimalResponse formatına dönüştürüyoruz
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(animalResponses);
    }
    // 14 Girilen hayvan sahibinin sistemde kayıtlı tüm hayvanlarını görüntüleme (sadece bir kişiye ait hayvanları görüntüle işlemi) başarılı bir şekilde çalışıyor mu? (4 Puan)
    @GetMapping("/filterCustomerName/{customerName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByCustomerName(@PathVariable("customerName") String customerName) {
        Optional<Customer> customerOptional = this.customerService.getCustomerByName(customerName);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            List<Animal> animals = this.animalService.getAnimalsByCustomerId(customer.getId());

            List<AnimalResponse> animalResponses = animals.stream()
                    .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                    .collect(Collectors.toList());

            return ResultHelper.success(animalResponses);
        } else {
            return ResultHelper.errorWithData("Kullanıcı bulunamadı.", null, HttpStatus.NOT_FOUND);
        }
    }
}
