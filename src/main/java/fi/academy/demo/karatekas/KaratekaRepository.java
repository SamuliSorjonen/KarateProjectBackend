package fi.academy.demo.karatekas;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KaratekaRepository extends JpaRepository <KaratekaEntity, Long> {
}
