import { Component } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EventRequestDTO, EventResponseDTO } from '../../../interfaces/event';
import { MessageService } from 'primeng/api';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HTTPError } from '../../../services/api/api.service';

@Component({
  selector: 'app-edit-event',
  templateUrl: './edit-event.component.html',
  styleUrls: ['./edit-event.component.scss'],
})
export class EditEventComponent {
  eventId: string | null = this.route.snapshot.paramMap.get('eventId');
  event?: EventResponseDTO;
  minDate: Date = new Date();
  uploadedFiles: any[] = [];
  isLoading: boolean = true;
  error?: string;

  constructor(
    private eventService: EventService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
  ) {
  }

  editEventForm: FormGroup = new FormGroup({
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
      ]),
    ),
    targetAmount: new FormControl(0),
    endDate: new FormControl(new Date(), Validators.compose([
      Validators.required,
    ])),
    terms: new FormControl(false, Validators.requiredTrue),
  });

  ngOnInit(): void {
    this.minDate.setDate(this.minDate.getDate() + 1);
    if (this.eventId) {
      this.loadEventData(this.eventId);
      if (this.event?.isActive) {
        this.editEventForm.disable();
      }
    } else {
      this.displayError('No event ID provided');
    }
  }

  loadEventData(eventId: string): void {
    this.eventService.getEventById(eventId).subscribe({
      next: (event: EventResponseDTO) => {
        this.event = event;
        this.editEventForm.patchValue({
          title: event.title,
          description: event.description,
          giftIdea: event.giftIdea,
          contribution: event.contribution,
          targetAmount: event.targetAmount,
          endDate: new Date(event.endDate),
        });
        this.isLoading = false;
      },
      error: (error: HTTPError) => {
        this.displayError(error.httpErrorMessage || 'An unknown error occurred');
      },
    });
  }

  submitForm(): void {
    if (this.editEventForm.valid) {
      console.log(this.editEventForm);
      const eventData: EventRequestDTO = this.editEventForm.value;
      this.eventService.updateEvent(this.eventId!, eventData).subscribe({
          next: (response: EventResponseDTO | HTTPError): void => {
            if ((response as EventResponseDTO).id) {
              this.showMessage('success', 'Event updated successfully!');
              this.router.navigate(['/event', (response as EventResponseDTO).id]);
            } else {
              this.showMessage('error', 'An error occurred while updating the event!');
              console.error('An error occurred:', (response as HTTPError).httpErrorMessage);
            }
          },
          error: (error) => {
            console.error('A network or server error occurred:', error);
          },
        },
      );
    } else {
      this.showMessage('error', 'Form is invalid!');
    }
  }

  displayError(message: string): void {
    this.error = message;
    this.isLoading = false;
    this.messageService.add({ severity: 'error', summary: 'Error', detail: message });
    this.router.navigate(['/']);
  }

  showMessage(severity: string, detail: string): void {
    this.messageService.add({ severity: severity, summary: severity.toUpperCase(), detail: detail });
  }

}
