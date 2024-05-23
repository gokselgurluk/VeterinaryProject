package gurluk.goksel.VeterinaryProject.api;

import gurluk.goksel.VeterinaryProject.business.abstracts.IAvailableDateService;
import gurluk.goksel.VeterinaryProject.business.abstracts.IDoctorService;
import gurluk.goksel.VeterinaryProject.core.config.modelMapper.IModelMapperService;
import gurluk.goksel.VeterinaryProject.core.config.result.Result;
import gurluk.goksel.VeterinaryProject.core.config.result.ResultData;
import gurluk.goksel.VeterinaryProject.core.config.utiles.ResultHelper;
import gurluk.goksel.VeterinaryProject.dto.CursorResponse;
import gurluk.goksel.VeterinaryProject.dto.request.Available.AvailableDataUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Available.AvailableDateSaveRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Customer.CustomerUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Doctor.DoctorUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.response.*;
import gurluk.goksel.VeterinaryProject.entity.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/v1/available-dates")
@RequiredArgsConstructor
public class AvailableDateController {
    private  final IAvailableDateService availableDateService;
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDataResponse> get(@PathVariable("id") Long id) {
        // Retrieves the appointment associated with the given appointment ID
        AvailableDate availableDate = this.availableDateService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(availableDate, AvailableDataResponse.class));
    }
    //
    //Mevcut verileri belirli bir kimliğe göre alan end point
    @GetMapping("/filterDoctorId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDataResponse>> getDatesByDoctorId (@PathVariable("id") Long doctorId) {
        List<AvailableDate> dates = this.availableDateService.getDatesByDoctorId(doctorId);

        // Sahibinin hayvanlarını AnimalResponse formatına dönüştürüyoruz
        List<AvailableDataResponse> availableDataResponses = dates.stream()
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDataResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(availableDataResponses);

      //  AvailableDate availableDate = this.availableDateService.get(id);
       // return ResultHelper.success(this.modelMapper.forResponse().map(availableDate,AvailableDataResponse.class));
    }
    @GetMapping("/All/List")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDataResponse>> cursor(
            @RequestParam(name = "page", required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false,defaultValue = "10") int pageSize
    ){
        // Sayfalama için ilgili servis metodu çağrılıyor
        Page<AvailableDate> availableDates = this.availableDateService.cursor(page,pageSize);
        Page<AvailableDataResponse> availableDataResponses = availableDates
                .map(available -> this.modelMapper.forResponse().map(available,AvailableDataResponse.class));
        // Sonuç dönüşü yapılıyor
        return  ResultHelper.cursor(availableDataResponses);
    }




    // Yeni bir kullanılabilir veri kaydı oluşturan  end point
    // 16 Proje isterlerine göre doktor müsait günü kaydediliyor mu? (4 puan)
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDataResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest ){
        // Maps the AvailableDateSaveRequest to AvailableDate class and saves it
        AvailableDate saveAvailableDate = this.modelMapper.forRequest().map(availableDateSaveRequest,AvailableDate.class);
        // Retrieves the doctor associated with the request and sets it in the available date
        Doctor doctor =this.doctorService.get(availableDateSaveRequest.getDoctorId());
        saveAvailableDate.setDoctor(doctor);

        this.availableDateService.save(saveAvailableDate);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAvailableDate,AvailableDataResponse.class));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDataResponse> update(@PathVariable("id") long id, @Valid @RequestBody AvailableDataUpdateRequest availableDataUpdateRequest) {
        // Güncellenecek veriyi veritabanından al
        AvailableDate existingAvailableDate = this.availableDateService.get(id);

        // Sadece availableDate alanını güncelle
        existingAvailableDate.setAvailableDate(availableDataUpdateRequest.getAvailableDate());

        // Güncellenmiş veriyi kaydet
        this.availableDateService.update(existingAvailableDate);

        // Güncellenmiş veriyi dön
        return ResultHelper.success(this.modelMapper.forResponse().map(existingAvailableDate, AvailableDataResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.availableDateService.delete(id);
        return ResultHelper.Ok();
    }

}