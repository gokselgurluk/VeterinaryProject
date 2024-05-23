package gurluk.goksel.VeterinaryProject.api;

import gurluk.goksel.VeterinaryProject.business.abstracts.IAnimalService;
import gurluk.goksel.VeterinaryProject.business.abstracts.IAppointmentService;
import gurluk.goksel.VeterinaryProject.business.abstracts.IAvailableDateService;
import gurluk.goksel.VeterinaryProject.business.abstracts.IDoctorService;
import gurluk.goksel.VeterinaryProject.core.config.modelMapper.IModelMapperService;
import gurluk.goksel.VeterinaryProject.core.config.result.Result;
import gurluk.goksel.VeterinaryProject.core.config.result.ResultData;
import gurluk.goksel.VeterinaryProject.core.config.utiles.ResultHelper;
import gurluk.goksel.VeterinaryProject.dto.CursorResponse;
import gurluk.goksel.VeterinaryProject.dto.request.Appointment.AppointmentSaveRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Appointment.AppointmentUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.request.Customer.CustomerUpdateRequest;
import gurluk.goksel.VeterinaryProject.dto.response.AnimalResponse;
import gurluk.goksel.VeterinaryProject.dto.response.AppointmentResponse;
import gurluk.goksel.VeterinaryProject.dto.response.CustomerResponse;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Appointment;
import gurluk.goksel.VeterinaryProject.entity.Customer;
import gurluk.goksel.VeterinaryProject.entity.Doctor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;
    private final IAnimalService animalService;
    private final IAvailableDateService availableDateService;


    // Endpoint that retrieves appointments by Doctor ID
    @GetMapping("/filter/doctor/{doctorId}")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDoctorId(@PathVariable("doctorId") long doctorId) {
        // Retrieves appointments associated with the given doctor ID
        List<Appointment> appointments = appointmentService.getByDoctorId(doctorId);
        // Maps the appointments to AppointmentResponse list
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }

    // Endpoint that retrieves appointments by Animal ID
    @GetMapping("/filter/animal/{animalId}")
    public ResultData<List<AppointmentResponse>> getAppointmentsByAnimalId(@PathVariable("animalId") long animalId) {
        // Retrieves appointments associated with the given animal ID
        List<Appointment> appointments = appointmentService.getByAnimalId(animalId);
        // Maps the appointments to AppointmentResponse list
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }

    // Endpoint that creates a new appointment record
    // 17 Proje isterlerine göre randevu kaydediliyor mu? (4 puan)
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {

        Appointment saveAppointment = modelMapper.forResponse().map(appointmentSaveRequest, Appointment.class);

        // Doktorun müsait günlerini kontrol etme
        // 18 Randevu oluşturulurken, doktorun o saatte başka bir randevusu var mı, doktorun müsait günü var mı  kontrolü yapılıyor mu? Sadece randevusu yoksa ve müsait günü varsa randevu kaydına izin veriyor mu? (4 Puan)
        Doctor doctor = doctorService.get(appointmentSaveRequest.getDoctorId());
        if (!availableDateService.isDoctorAvailableOnDate(doctor.getId(), appointmentSaveRequest.getAppointmentDateTime().toLocalDate())) {
            return ResultHelper.errorWithData("Doktor müsait değil", null, HttpStatus.BAD_REQUEST);
        }
        if (appointmentService.isAppointmentConflict(doctor.getId(), appointmentSaveRequest.getAppointmentDateTime())) {
            return ResultHelper.errorWithData("Doktorun bu saatte başka bir randevusu var", null, HttpStatus.BAD_REQUEST);
        }

        saveAppointment.setDoctor(doctor);
        Animal animal = animalService.get(appointmentSaveRequest.getAnimalId());
        saveAppointment.setAnimal(animal);

        appointmentService.save(saveAppointment);
        return ResultHelper.created(modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.appointmentService.delete(id);
        return ResultHelper.Ok();
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@PathVariable long id, @Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        // Mevcut randevuyu veri tabanından al
        Appointment existingAppointment = this.appointmentService.get(id);

        // Sadece tarih kısmını güncelle
        existingAppointment.setAppointmentDateTime(appointmentUpdateRequest.getAppointmentDateTime());

        // Güncellenmiş randevuyu kaydet
        this.appointmentService.update(existingAppointment);

        // Yanıtı döndür
        return ResultHelper.success(this.modelMapper.forResponse().map(existingAppointment, AppointmentResponse.class));
    }






    // Endpoint that retrieves an appointment by a specific appointment ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        // Retrieves the appointment associated with the given appointment ID
        Appointment appointment = this.appointmentService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));
    }
    @GetMapping("/All/List")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false,defaultValue = "10") int pageSize
    ){
        // Sayfalama için ilgili servis metodu çağrılıyor
        Page<Appointment> appointmentPage = this.appointmentService.cursor(page,pageSize);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage
                .map(appointment -> this.modelMapper.forResponse().map(appointment,AppointmentResponse.class));
        // Sonuç dönüşü yapılıyor
        return  ResultHelper.cursor(appointmentResponsePage);
    }


    // Endpoint that retrieves appointments within a specific date range
    // 20 Randevular kullanıcı tarafından girilen tarih aralığına ve doktora göre filtreleniyor mu? (4 Puan)
    @GetMapping("/filter/dateANDdoctor")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateRangeDoctor(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "doctorId", required = false) Long doctorId) {
        // Randevuları belirtilen tarih aralığında ve opsiyonel olarak doktor ID'sine göre al
        List<Appointment> appointments;
        if (doctorId != null) {
            appointments = appointmentService.getAppointmentsByDateRangeAndDoctorId(startDate, endDate, doctorId);
        } else {
            appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
        }

        // Eğer randevu bulunamadıysa
        if (appointments.isEmpty()) {
            return ResultHelper.errorWithData("Belirtilen tarih aralığında randevu bulunamadı.", null, HttpStatus.NOT_FOUND);
        }

        // Randevuları AppointmentResponse listesine dönüştür
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(appointmentResponses);
    }
    // 19 Randevular kullanıcı tarafından girilen tarih aralığına ve hayvana göre filtreleniyor mu? (4 Puan)
    @GetMapping("/filter/dateANDanimal")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateRangeAnimal(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "animalId", required = false) Long animalId) {

        List<Appointment> appointments;

        if (animalId != null) {
            appointments = appointmentService.getAppointmentsByDateRangeAndAnimalId(startDate, endDate, animalId);
        } else {
            appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
        }

        if (appointments.isEmpty()) {
            return ResultHelper.errorWithData("Belirtilen tarih aralığında randevu bulunamadı.", null, HttpStatus.NOT_FOUND);
        }

        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(appointmentResponses);
    }
    @GetMapping("/filterAnimalName/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getAnimalsByCustomerName(@PathVariable("animalName") String animalName) {
        Optional<Animal> animalOptional = this.animalService.getAnimalByName(animalName);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            // Retrieves appointments associated with the given animal ID
            List<Appointment> appointments = appointmentService.getByAnimalId(animal.getId());
            // Maps the appointments to AppointmentResponse list
            List<AppointmentResponse> appointmentResponses = appointments.stream()
                    .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                    .collect(Collectors.toList());

            return ResultHelper.success(appointmentResponses);

        } else {
            return ResultHelper.errorWithData("Kullanıcı bulunamadı.", null, HttpStatus.NOT_FOUND);
        }
    }


}
