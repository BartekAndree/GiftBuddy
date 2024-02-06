import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { Observable } from 'rxjs';
import { ParticipantDetailsResponseDTO } from '../../interfaces/participant';

@Injectable({
  providedIn: 'root',
})
export class ParticipantService {

  constructor(private apiService: ApiService) {
  }

  getParticipantsByEventId(eventId: string): Observable<ParticipantDetailsResponseDTO[]> {
    return this.apiService.get(`/participants/${eventId}`);
  }

  settleAllSelectedParticipants(participantIds: string[], eventId: string): Observable<any> {
    return this.apiService.put(`/participants/settle/${eventId}`, participantIds);
  }
}
