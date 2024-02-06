import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApiService, HTTPError } from '../api/api.service';
import { UserProfileRequestDTO, UserProfileResponseDTO } from '../../interfaces/user';

@Injectable({
  providedIn: 'root',
})

export class UserService {
  private endpoint = '/users';

  constructor(private apiService: ApiService) {
  }

  getUserById(userId: string): Observable<UserProfileResponseDTO | HTTPError> {
    return this.apiService.get(`${this.endpoint}/${userId}`);
  }

  createUser(user: UserProfileRequestDTO): Observable<UserProfileResponseDTO | HTTPError> {
    return this.apiService.post(`${this.endpoint}`, user);
  }
}
