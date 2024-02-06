import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ParticipantService } from '../../../services/participant/participant.service';
import { ParticipantDetailsResponseDTO } from '../../../interfaces/participant';
import { MessageService } from 'primeng/api';
import { HTTPError } from '../../../services/api/api.service';
import { EmailbufferService } from '../../../services/emailbuffer/emailbuffer.service';
import { EmailBufferResponseDTO, EmailRequestDTO, SaveEmailResponseDTO } from '../../../interfaces/emailbuffer';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-participants-list',
  templateUrl: './participants-list.component.html',
  styleUrls: ['./participants-list.component.scss'],
})
export class ParticipantsListComponent {
  @Input({ required: true }) eventId!: string;
  @Input() isOrganizer: boolean = false;
  @Output() requestFetchEventDetails = new EventEmitter<void>();
  participantsList: ParticipantDetailsResponseDTO[] = [];
  emailbuffersList: EmailBufferResponseDTO[] = [];
  isLoading: boolean = false;
  selectedParticipantsIds: string[] = [];
  emailControl: FormControl = new FormControl('', [Validators.required, Validators.email]);


  constructor(
    private participantService: ParticipantService,
    private emailbufferService: EmailbufferService,
    private messageService: MessageService,
  ) {
  }

  handleSaveEmailToBuffer(): void {
    if (this.emailControl.valid) {
      const email: EmailRequestDTO = {
        email: this.emailControl.value,
      };

      this.emailbufferService.saveEmail(this.eventId, email).subscribe({
        next: (response: SaveEmailResponseDTO): void => {
          this.emailControl.reset();

          let messageDetail = '';
          if (response.emailBufferResponseDTO) {
            console.log('Email Buffer Response:', response.emailBufferResponseDTO);
            messageDetail = `Email saved to buffer: ${response.emailBufferResponseDTO.email}`;
            this.fetchEmailbuffer();
          } else if (response.participantResponseDTO) {
            console.log('Participant Response:', response.participantResponseDTO);
            messageDetail = `Participant created: ${response.participantResponseDTO.id}`;
            this.fetchParticipants();
          }

          this.messageService.add({ severity: 'success', summary: 'Success', detail: messageDetail });
        },
        error: (error: HTTPError): void => {
          this.emailControl.reset();
          console.error(error);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.originalError?.error?.message || error.httpErrorMessage,
          });
        },
      });
    }
  }


  removeEmailFromBuffer(emailbufferId: string): void {
    this.emailbufferService.removeEmail(this.eventId, emailbufferId).subscribe({
      next: (): void => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Email deleted' });
        this.fetchEmailbuffer();
      },
      error: (error: HTTPError): void => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.httpErrorMessage });
      },
    });
  }

  settleSelectedParticipants(): void {
    this.isLoading = true;
    this.participantService.settleAllSelectedParticipants(this.selectedParticipantsIds, this.eventId)
      .subscribe({
        next: (): void => {
          this.fetchParticipants();
          this.requestFetchEventDetails.emit();
        },
        error: (error: HTTPError): void => {
          this.isLoading = false;
          console.log(error.httpErrorMessage);
        },
      });
  }

  private fetchParticipants(): void {
    this.participantService.getParticipantsByEventId(this.eventId).subscribe({
      next: (data: ParticipantDetailsResponseDTO[]): void => {
        this.participantsList = data;
        this.isLoading = false;
      },
      error: (error: HTTPError): void => {
        this.isLoading = false;
        console.log(error.httpErrorMessage);
      },
    });
  }

  private fetchEmailbuffer(): void {
    this.emailbufferService.getEmail(this.eventId).subscribe({
      next: (data: EmailBufferResponseDTO[]): void => {
        this.emailbuffersList = data;
        this.isLoading = false;
      },
      error: (error: HTTPError): void => {
        this.isLoading = false;
        console.log(error.httpErrorMessage);
      },
    });
  }

  ngOnInit(): void {
    if (this.eventId && this.isOrganizer) {
      this.fetchParticipants();
      this.fetchEmailbuffer();
    } else {
      this.fetchParticipants();
    }
  }

}
