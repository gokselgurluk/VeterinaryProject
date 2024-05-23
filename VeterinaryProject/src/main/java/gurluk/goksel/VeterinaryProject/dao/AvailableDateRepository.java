package gurluk.goksel.VeterinaryProject.dao;


import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailableDateRepository extends JpaRepository<AvailableDate,Long> {
    List<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate availableDate);

    List<AvailableDate> findByDoctorId(Long doctorId);
}
