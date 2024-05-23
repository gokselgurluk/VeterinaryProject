package gurluk.goksel.VeterinaryProject.api;

import gurluk.goksel.VeterinaryProject.business.abstracts.IDoctorService;
import gurluk.goksel.VeterinaryProject.core.config.modelMapper.IModelMapperService;
import gurluk.goksel.VeterinaryProject.core.config.result.Result;
import gurluk.goksel.VeterinaryProject.core.config.result.ResultData;
import gurluk.goksel.VeterinaryProject.core.config.utiles.ResultHelper;
import gurluk.goksel.VeterinaryProject.dto.CursorResponse;
import gurluk.goksel.VeterinaryProject.dto.request.Doctor.DoctorSaveRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Doctor.DoctorUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.response.AnimalResponse;
import gurluk.goksel.VeterinaryProject.dto.response.AppointmentResponse;
import gurluk.goksel.VeterinaryProject.dto.response.DoctorResponse;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Appointment;
import gurluk.goksel.VeterinaryProject.entity.Doctor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/doctors")
@RequiredArgsConstructor
public class DoctorConroller {
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    // Constructor based dependency injection

    // Doktorun detaylarını almak için GET endpoint'i
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get (@PathVariable("id") Long id) {
        Doctor doctor = this.doctorService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(doctor,DoctorResponse.class));
    }

    // Yeni bir doktor kaydetmek için POST endpoint'i
    // 15 Proje isterlerine göre doktor kaydediliyor mu? (4 puan)
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest ){
        Doctor saveDoctor = this.modelMapper.forRequest().map(doctorSaveRequest,Doctor.class);
        this.doctorService.save(saveDoctor);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveDoctor,DoctorResponse.class));
    }

    // Doktor bilgilerini güncellemek için PUT endpoint'i
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest ){
        Doctor updateDoctor = this.modelMapper.forRequest().map(doctorUpdateRequest, Doctor.class);
        this.doctorService.update(updateDoctor);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateDoctor, DoctorResponse.class));
    }
    @GetMapping("/filter/doctor/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<DoctorResponse>> getDoctorByName(@RequestParam("name") String name) {
        List<Doctor> doctor = this.doctorService.getDoctorByName(name);

        // İsmine göre filtrelenmiş hayvanları AnimalResponse formatına dönüştürüyoruz
        List<DoctorResponse> doctorResponses = doctor.stream()
                .map(doctors -> this.modelMapper.forResponse().map(doctors, DoctorResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(doctorResponses);
    }
    @GetMapping("/All/List")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            @RequestParam(name = "page", required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false,defaultValue = "10") int pageSize
    ){
        // Sayfalama için ilgili servis metodu çağrılıyor
        Page<Doctor> doctorPage = this.doctorService.cursor(page,pageSize);
        Page<DoctorResponse> doctorResponsePage = doctorPage
                .map(doctor -> this.modelMapper.forResponse().map(doctor,DoctorResponse.class));
        // Sonuç dönüşü yapılıyor
        return  ResultHelper.cursor(doctorResponsePage);
    }

    // Doktoru silmek için DELETE endpoint'i
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.doctorService.delete(id);
        return ResultHelper.Ok();
    }

}