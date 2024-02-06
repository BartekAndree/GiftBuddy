import { Component } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import { SessionService } from '../../../services/session/session.service';
import { EventResponseDTO } from '../../../interfaces/event';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss'],
})
export class EventListComponent {
  eventsList: EventResponseDTO[] = [];

  constructor(
    private eventService: EventService,
    private sessionService: SessionService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
  ) {
  }

  ngOnInit(): void {
    this.fetchEvents();
  }

  fetchEvents(): void {
    this.eventService.getEvents().subscribe((events: EventResponseDTO[]) => {
      this.eventsList = events.sort((a, b) =>
        new Date(a.endDate).getTime() - new Date(b.endDate).getTime(),
      );
    });
  }

  deleteEvent(eventId: string): void {
    this.eventService.deleteEvent(eventId).subscribe({
      next: (): void => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Event deleted successfully',
        });
        this.fetchEvents();
      },
      error: (err: any): void => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'An error occurred while deleting the event',
        });
        console.error('Error deleting event:', err);
      },
    });
  }

  confirmDelete(eventId: string): void {
    this.confirmationService.confirm({
      message: 'Do you want to delete this event?',
      header: 'Delete Confirmation',
      icon: 'pi pi-trash',
      rejectButtonStyleClass: 'p-button-outlined',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.deleteEvent(eventId);
        this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Event deleted' });
      },
      reject: () => {
        this.messageService.add({ severity: 'warn', summary: 'Cancelled', detail: 'You have cancelled' });
      },
    });
  }

  isUserOrganizer(organizerExternalId: string): boolean {
    const userUUID: string | null = this.sessionService.getUserUUID();
    return userUUID !== null && organizerExternalId === userUUID;
  }

}
