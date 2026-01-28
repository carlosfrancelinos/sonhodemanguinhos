
package br.com.sonhode.manguinhos.web;

import java.time.LocalDate;

public record BookingCreateDTO(
    String code, String propertyName,
    LocalDate startDate, LocalDate endDate,
    Integer adults, Integer children,
    Long leadGuestId
) {}
