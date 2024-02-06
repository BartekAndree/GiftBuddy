import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../../../services/event/event.service';
import { EventResponseDTO } from '../../../interfaces/event';
import { HTTPError } from '../../../services/api/api.service';
import { MessageService } from 'primeng/api';
import { render } from 'creditcardpayments/creditCardPayments';
import { SessionService } from '../../../services/session/session.service';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss'],
})
export class EventDetailsComponent {
  event!: EventResponseDTO;
  isLoading: boolean = true;
  error?: string;
  paypalRendered: boolean = false;

  constructor(
    private eventService: EventService,
    private sessionService: SessionService,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
  ) {
  }

  ngOnInit(): void {
    const eventId: string | null = this.route.snapshot.paramMap.get('eventId');
    if (eventId) {
      this.fetchEventDetails(eventId);
    } else {
      this.displayError('No event ID provided');
    }
  }

  ngAfterViewChecked(): void {
    if (!this.paypalRendered && this.event && this.event.contribution !== undefined) {
      const paypalButtonContainer = document.getElementById('myPaypalButtons');
      if (paypalButtonContainer) {
        this.setupPaypalButtons(this.event.contribution);
        this.paypalRendered = true;
      }
    }
  }

  fetchEventDetails(eventId: string): void {
    this.isLoading = true;
    this.eventService.getEventById(eventId).subscribe({
      next: (data: EventResponseDTO): void => {
        this.event = data;
        this.isLoading = false;
      },
      error: (err: HTTPError): void => {
        this.displayError(err.originalError?.error.message || err.httpErrorMessage || 'An unknown error occurred');
        console.error('Error fetching event details:', err);
      },
    });
  }

  displayError(message: string): void {
    this.error = message;
    this.isLoading = false;
    this.messageService.add({ severity: 'error', summary: 'Error', detail: message });
    this.router.navigate(['/']);
  }

  setupPaypalButtons(contribution: number | undefined): void {
    if (contribution !== undefined) {
      const contributionValue = parseFloat(contribution.toPrecision());
      render({
        id: '#myPaypalButtons',
        currency: 'PLN',
        value: contributionValue.toString(),
        onApprove: (details) => {
          alert('Transakcja udała się!');
          console.log(details);
        },
      });
    }
  }

  onFetchEventDetailsRequest(): void {
    this.fetchEventDetails(this.event.id);
  }

  calculateProgress(): number {
    if (!this.event.targetAmount || !this.event.currentAmount) {
      return 0;
    } else {
      return Math.round((this.event.currentAmount / this.event.targetAmount) * 100);
    }
  }

  isUserOrganizer(): boolean {
    const userUUID: string | null = this.sessionService.getUserUUID();
    return userUUID !== null && this.event.organizerExternalId === userUUID;
  }
}
