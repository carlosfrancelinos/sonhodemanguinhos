
package br.com.sonhode.manguinhos.web;

import br.com.sonhode.manguinhos.domain.Booking;
import br.com.sonhode.manguinhos.domain.Checkin;
import br.com.sonhode.manguinhos.repo.BookingRepository;
import br.com.sonhode.manguinhos.repo.CheckinRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = {"*"})
public class PublicController {
    private final BookingRepository bookingRepo;
    private final CheckinRepository checkinRepo;

    public PublicController(BookingRepository bookingRepo, CheckinRepository checkinRepo) {
        this.bookingRepo = bookingRepo; this.checkinRepo = checkinRepo;
    }

    @PostMapping("/checkin/{token}")
    public ResponseEntity<Void> checkinByToken(@PathVariable String token, HttpServletRequest req) {
        var b = bookingRepo.findByQrtoken(token).orElseThrow();
        if (b.getStatus()== Booking.Status.CHECKED_IN) return ResponseEntity.status(409).build();
        b.setStatus(Booking.Status.CHECKED_IN);
        bookingRepo.save(b);
        checkinRepo.save(new Checkin(b, Instant.now(), "ONSITE", req.getRemoteAddr(), req.getHeader("User-Agent")));
        return ResponseEntity.ok().build();
    }
}
