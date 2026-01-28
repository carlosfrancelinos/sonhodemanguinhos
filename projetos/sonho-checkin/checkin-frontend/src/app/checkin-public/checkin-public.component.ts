
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookingService } from '../services/booking.service';

@Component({
  standalone: true,
  selector: 'app-checkin-public',
  template: `
    <div class="wrap">
      <h2>Check-in</h2>
      <p *ngIf="done==='ok'">Check-in confirmado! ✅</p>
      <p *ngIf="done==='err'">Token inválido ou já utilizado.</p>
    </div>
  `,
  styles:[`.wrap{padding:16px}`]
})
export class CheckinPublicComponent {
  done: 'ok'|'err'|null = null;
  constructor(private route: ActivatedRoute, private api: BookingService){
    const token = this.route.snapshot.paramMap.get('token')!;
    this.api.checkinPublic(token).subscribe({
      next: _ => this.done = 'ok',
      error: _ => this.done = 'err'
    });
  }
}
