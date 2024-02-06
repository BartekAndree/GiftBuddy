import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EventService } from '../../../services/event/event.service';
import { Router } from '@angular/router';
import { EventRequestDTO, EventResponseDTO } from '../../../interfaces/event';
import { HTTPError } from '../../../services/api/api.service';
import { MessageService } from 'primeng/api';
import { SessionService } from '../../../services/session/session.service';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss'],
})
export class CreateEventComponent {
  minDate: Date = new Date();

  constructor(
    private eventService: EventService,
    private router: Router,
    private messageService: MessageService,
    private sessionService: SessionService,
  ) {
  }

  createEventForm: FormGroup = new FormGroup({
    title: new FormControl('', Validators.compose([
      Validators.required,
      Validators.minLength(4),
      Validators.maxLength(100),
    ])),
    description: new FormControl('', Validators.compose([
      Validators.required,
      Validators.minLength(4),
      Validators.maxLength(500),
    ])),
    giftIdea: new FormControl('', Validators.maxLength(100)),
    contribution: new FormControl(0, Validators.compose([
      Validators.required,
      Validators.min(0),
    ])),
    endDate: new FormControl(new Date(), Validators.required),
    terms: new FormControl(false, Validators.requiredTrue),
  });

  ngOnInit(): void {
    this.minDate.setDate(this.minDate.getDate() + 1);
  }

  submitForm(): void {
    if (this.createEventForm.valid) {
      const eventData: EventRequestDTO = this.createEventForm.value as EventRequestDTO;
      this.checkIfUserIsLoggedIn();
      this.eventService.createEvent(eventData).subscribe({
        next: (response: EventResponseDTO | HTTPError): void => {
          if ((response as EventResponseDTO).id) {
            this.showMessage('success', 'Event created successfully!');
            this.router.navigate(['/event', (response as EventResponseDTO).id]);
          } else {
            this.showMessage('error', 'An error occurred while creating the event!');
            console.error('An error occurred:', (response as HTTPError).httpErrorMessage);
          }
        },
        error: (error) => {
          console.error('A network or server error occurred:', error);
        },
      });
    }
  }

  checkIfUserIsLoggedIn(): void {
    if (!this.sessionService.getUserUUID()) {
      this.router.navigate(['/']);
    }
  }

  showMessage(severity: string, detail: string): void {
    this.messageService.add({ severity: severity, summary: severity.toUpperCase(), detail: detail });
  }

}
