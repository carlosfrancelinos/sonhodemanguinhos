
package br.com.sonhode.manguinhos.web;

import br.com.sonhode.manguinhos.domain.Booking;
import br.com.sonhode.manguinhos.domain.Checkin;
import br.com.sonhode.manguinhos.repo.BookingRepository;
import br.com.sonhode.manguinhos.repo.CheckinRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
public class BookingController {
    private final BookingRepository bookingRepo;
    private final CheckinRepository checkinRepo;

    public BookingController(BookingRepository bookingRepo, CheckinRepository checkinRepo) {
        this.bookingRepo = bookingRepo; this.checkinRepo = checkinRepo;
    }

    @GetMapping("/bookings")
    public List<Booking> all() { return bookingRepo.findAll(); }

    @PostMapping("/bookings")
    public Booking create(@RequestBody Booking b) { return bookingRepo.save(b); }

    @GetMapping("/bookings/{id}")
    public Booking byId(@PathVariable Long id) { return bookingRepo.findById(id).orElseThrow(); }

    @GetMapping(value="/bookings/{id}/qrcode", produces= MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> qrcode(@PathVariable Long id) throws WriterException, IOException {
        var b = bookingRepo.findById(id).orElseThrow();
        if (b.getQrtoken()==null) {
            b.setQrtoken(UUID.randomUUID().toString().replace("-", ""));
            bookingRepo.save(b);
        }
        String url = "/public/checkin/" + b.getQrtoken();
        BufferedImage image = new com.google.zxing.client.j2se.MatrixToImageWriter()
                .toBufferedImage(new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return ResponseEntity.ok(baos.toByteArray());
    }

    @PostMapping("/bookings/{id}/checkin")
    public ResponseEntity<Void> checkin(@PathVariable Long id) {
        var b = bookingRepo.findById(id).orElseThrow();
        if (b.getStatus()== Booking.Status.CHECKED_IN) return ResponseEntity.status(409).build();
        b.setStatus(Booking.Status.CHECKED_IN);
        bookingRepo.save(b);
        checkinRepo.save(new Checkin(b, Instant.now(), "AUTH", "local", "panel"));
        return ResponseEntity.ok().build();
    }
}
