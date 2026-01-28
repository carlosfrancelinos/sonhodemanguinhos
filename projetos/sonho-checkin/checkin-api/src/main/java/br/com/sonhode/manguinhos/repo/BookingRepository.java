
package br.com.sonhode.manguinhos.repo;

import br.com.sonhode.manguinhos.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByQrtoken(String qrtoken);
}
