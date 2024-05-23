package gurluk.goksel.VeterinaryProject.api;

import gurluk.goksel.VeterinaryProject.business.abstracts.IAnimalService;
import gurluk.goksel.VeterinaryProject.business.abstracts.IVaccineService;
import gurluk.goksel.VeterinaryProject.core.config.modelMapper.IModelMapperService;
import gurluk.goksel.VeterinaryProject.core.config.result.Result;
import gurluk.goksel.VeterinaryProject.core.config.result.ResultData;
import gurluk.goksel.VeterinaryProject.core.config.utiles.ResultHelper;
import gurluk.goksel.VeterinaryProject.dto.CursorResponse;
import gurluk.goksel.VeterinaryProject.dto.request.Vaccine.VaccineSaveRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Vaccine.VaccineUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.response.CustomerResponse;
import gurluk.goksel.VeterinaryProject.dto.response.VaccineResponse;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Appointment;
import gurluk.goksel.VeterinaryProject.entity.Customer;
import gurluk.goksel.VeterinaryProject.entity.Vaccine;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccines")
@RequiredArgsConstructor
public class VaccineController {
    private final IVaccineService vaccineService;
    private final IModelMapperService modelMapper;
    private final IAnimalService animalService ;

    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest ){
        Vaccine saveVaccine = this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        Long animalId = vaccineSaveRequest.getAnimalId();
        // Hayvanın aynı kodla herhangi bir aktif aşısı var mı diye kontrol et

        if (vaccineService.existsActiveVaccineByAnimalIdAndVaccineCode(animalId, vaccineSaveRequest.getCode())) {
            return ResultHelper.errorWithData("Bu hayvan için koruma süresi dolmamış bir aşı zaten mevcut.",null,HttpStatus.BAD_REQUEST);
        }
        Vaccine savedVaccine = this.vaccineService.save(saveVaccine, animalId);
        return ResultHelper.created(this.modelMapper.forResponse().map(savedVaccine, VaccineResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get (@PathVariable("id") Long id) {
        Vaccine vaccine = this.vaccineService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(vaccine,VaccineResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.vaccineService.delete(id);
        return ResultHelper.Ok();
    }
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest ){
        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class);
        // Güncellenen hayvanın müşteri ID'sini al
        long animalId = vaccineUpdateRequest.getAnimalId();

        // Müşteriyi veritabanından al
        Animal animal = this.animalService.get(animalId);

        // Güncellenen hayvana müşteriyi ata
        updateVaccine.setAnimal(animal);


        this.vaccineService.update(updateVaccine);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class));
    }


    @GetMapping("/All/List")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false,defaultValue = "5") int pageSize
    ){
        // Sayfalama için ilgili servis metodu çağrılıyor
        Page<Vaccine> vaccinePage = this.vaccineService.cursor(page,pageSize);
        Page<VaccineResponse> vaccineResponsePage = vaccinePage
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine,VaccineResponse.class));
        // Sonuç dönüşü yapılıyor
        return  ResultHelper.cursor(vaccineResponsePage);
    }
   /* @GetMapping("/animal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalId(@PathVariable("animalId") Long animalId) {
        List<Vaccine> vaccines = this.vaccineService.getVaccinesByAnimalId(animalId);

        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(vaccineResponses);
    }*/
    @GetMapping("/vaccines/List/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesForAnimal(@PathVariable("animalId") Long id) {
        Animal animal = this.animalService.get(id);

        // Burada, animal.getVaccines() şeklinde hayvana ait tüm aşı kayıtlarını alabilirsiniz
        List<Vaccine> vaccines = animal.getVaccines();
        if (vaccines.isEmpty()) {
            return ResultHelper.errorWithData("Belirtilen hayvan için aşı bulunamadı.", null, HttpStatus.NOT_FOUND);
        }

        // Bu aşı kayıtlarını VaccineResponse'a dönüştürerek döndürebilirsiniz
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(vaccineResponses);
    }

    // Belirli bir tarih aralığındaki aşı kayıtlarını getirir.
    @GetMapping("/dates-range")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByDateRange(
            @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Vaccine> vaccines = vaccineService.getByProtectionStartDateBetween(startDate, endDate);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(vaccineResponses);
    }
    @GetMapping("/animal/filter/vaccineFinishDate")
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalAndDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "animalId", required = false) Long animalId) {

        List<Vaccine> vaccines;

        if (animalId != null) {
            vaccines = vaccineService.findByAnimalIdAndProtectionStartDateBetween(animalId, startDate, endDate);
        } else {
            vaccines = vaccineService.getVaccinesByFinishDateRange(startDate, endDate);
        }

        if (vaccines.isEmpty()) {
            return ResultHelper.errorWithData("Belirtilen tarih aralığında kayıt bulunamadı.", null, HttpStatus.NOT_FOUND);
        }

        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(vaccineResponses);
    }
}

