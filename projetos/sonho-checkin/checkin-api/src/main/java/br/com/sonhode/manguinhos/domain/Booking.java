
package br.com.sonhode.manguinhos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank
    private String propertyName = "Sonho de Manguinhos";

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer adults = 2;

    @NotNull
    private Integer children = 0;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(unique = true)
    private String qrtoken;

    @ManyToOne(fetch = FetchType.LAZY)
    private Guest leadGuest;

    private Instant createdAt = Instant.now();

    public enum Status { PENDING, CHECKED_IN, CANCELLED }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Integer getAdults() { return adults; }
    public void setAdults(Integer adults) { this.adults = adults; }
    public Integer getChildren() { return children; }
    public void setChildren(Integer children) { this.children = children; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getQrtoken() { return qrtoken; }
    public void setQrtoken(String qrtoken) { this.qrtoken = qrtoken; }
    public Guest getLeadGuest() { return leadGuest; }
    public void setLeadGuest(Guest leadGuest) { this.leadGuest = leadGuest; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
