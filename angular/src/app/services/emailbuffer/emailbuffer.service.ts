import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { Observable } from 'rxjs';
import { EmailBufferResponseDTO, EmailRequestDTO, SaveEmailResponseDTO } from '../../interfaces/emailbuffer';

@Injectable({
  providedIn: 'root',
})
export class EmailbufferService {

  constructor(private apiService: ApiService) {
  }

  saveEmail(eventId: string, email: EmailRequestDTO): Observable<SaveEmailResponseDTO> {
    return this.apiService.post(`/emailbuffer/${eventId}`, email);
  }

  getEmail(eventId: string): Observable<EmailBufferResponseDTO[]> {
    return this.apiService.get(`/emailbuffer/${eventId}`);
  }

  removeEmail(eventId: string, emailbufferId: string): Observable<void> {
    return this.apiService.delete(`/emailbuffer/${eventId}/${emailbufferId}`);
  }

}
