package gurluk.goksel.VeterinaryProject.dao;

import gurluk.goksel.VeterinaryProject.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface VaccineRepository extends JpaRepository<Vaccine,Long> {
    List<Vaccine> findByAnimalId(Long animalId);


    @Query("SELECT v FROM Vaccine v WHERE v.animal.id = :animalId AND v.protectionFinishDate BETWEEN :startDate AND :endDate")
    List<Vaccine> findByAnimalIdAndProtectionFinishDateBetween(
            @Param("animalId") Long animalId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);


    @Query("SELECT v FROM Vaccine v WHERE v.protectionStartDate BETWEEN :startDate AND :endDate AND v.protectionFinishDate BETWEEN :startDate AND :endDate")
    List<Vaccine> findByProtectionStartDateAndFinishDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    boolean existsByAnimalIdAndCodeAndProtectionFinishDateAfter(Long animalId, String code, LocalDate protectionFinishDate);

}