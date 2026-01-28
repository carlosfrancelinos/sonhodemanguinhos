
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Booking {
  id: number; code: string; propertyName: string;
  startDate: string; endDate: string; adults: number; children: number;
  status: 'PENDING'|'CHECKED_IN'|'CANCELLED'; qrtoken?: string;
}

@Injectable({ providedIn: 'root' })
export class BookingService {
  api = (window as any).__API__ || 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}
  list() { return this.http.get<Booking[]>(`${this.api}/bookings`); }
  create(b: Partial<Booking>) { return this.http.post<Booking>(`${this.api}/bookings`, b); }
  qrcode(id: number) { return this.http.get(`${this.api}/bookings/${id}/qrcode`, { responseType:'blob' }); }
  checkinPublic(token: string) { return this.http.post(`${this.api.replace('/api','')}/public/checkin/${token}`, {}); }
}
