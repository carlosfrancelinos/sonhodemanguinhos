
package br.com.sonhode.manguinhos.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Checkin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name="booking_id")
    private Booking booking;

    private Instant checkinAt;
    private String method;
    private String ip;
    private String deviceInfo;

    public Checkin() {}
    public Checkin(Booking b, Instant at, String method, String ip, String device) {
        this.booking = b; this.checkinAt = at; this.method = method; this.ip = ip; this.deviceInfo = device;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public Instant getCheckinAt() { return checkinAt; }
    public void setCheckinAt(Instant checkinAt) { this.checkinAt = checkinAt; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getDeviceInfo() { return deviceInfo; }
    public void setDeviceInfo(String deviceInfo) { this.deviceInfo = deviceInfo; }
}
