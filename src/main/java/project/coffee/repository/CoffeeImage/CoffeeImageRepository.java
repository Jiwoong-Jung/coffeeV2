package project.coffee.repository.CoffeeImage;

import org.springframework.data.jpa.repository.JpaRepository;
import project.coffee.domain.entity.CoffeeImage;

public interface CoffeeImageRepository extends JpaRepository<CoffeeImage, Long> {
}
