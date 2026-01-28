
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { BookingService, Booking } from '../services/booking.service';

@Component({
  standalone: true,
  selector: 'app-reservas',
  imports: [CommonModule, HttpClientModule, TableModule, ButtonModule],
  template: `
  <div class="wrap">
    <h2>Reservas</h2>
    <p-table [value]="rows">
      <ng-template pTemplate="header">
        <tr>
          <th>Código</th><th>Entrada</th><th>Saída</th><th>Status</th><th>Ações</th>
        </tr>
      </ng-template>
      <ng-template pTemplate="body" let-r>
        <tr>
          <td>{{r.code}}</td>
          <td>{{r.startDate}}</td>
          <td>{{r.endDate}}</td>
          <td>{{r.status}}</td>
          <td>
            <button pButton type="button" label="QR Code" (click)="qr(r)"></button>
          </td>
        </tr>
      </ng-template>
    </p-table>
  </div>
  `,
  styles:[`.wrap{padding:16px;max-width:980px;margin:auto}`]
})
export class ReservasComponent implements OnInit {
  rows: Booking[] = [];
  constructor(private api: BookingService){}
  ngOnInit(){ this.api.list().subscribe(d => this.rows = d); }
  async qr(r: Booking){
    const blob = await this.api.qrcode(r.id).toPromise();
    if (!blob) return;
    const url = URL.createObjectURL(blob);
    const w = window.open();
    if (w) { w.document.write('<img src="'+url+'" style="max-width:100%"/>'); }
  }
}
