import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../api/api.service';
import { EventRequestDTO, EventResponseDTO } from '../../interfaces/event';


@Injectable({
  providedIn: 'root',
})
export class EventService {

  constructor(private apiService: ApiService) {
  }

  getEvents(): Observable<EventResponseDTO[]> {
    return this.apiService.get('/events');
  }

  getEventById(id: string): Observable<EventResponseDTO> {
    return this.apiService.get(`/events/${id}`);
  }

  createEvent(eventData: EventRequestDTO): Observable<EventResponseDTO> {
    return this.apiService.post('/events', eventData);
  }

  updateEvent(id: string, eventData: EventRequestDTO): Observable<EventResponseDTO> {
    return this.apiService.put(`/events/${id}`, eventData);
  }

  deleteEvent(id: string): Observable<any> {
    return this.apiService.delete(`/events/${id}`);
  }
}
