import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { BehaviorSubject } from 'rxjs';
import { KeycloakProfile } from 'keycloak-js';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private userUUID$: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

  constructor(private keycloakService: KeycloakService) {
  }

  async initializeService(): Promise<void> {
    await this.extractAndSetUUID();
  }

  private async extractAndSetUUID(): Promise<void> {
    if (await this.keycloakService.isLoggedIn()) {
      try {
        const userProfile: KeycloakProfile = await this.keycloakService.loadUserProfile();

        if (userProfile && userProfile.id) {
          this.setUserUUID(userProfile.id);
        }
      } catch (error) {
        console.error('Error loading user profile:', error);
      }
    }
  }

  setUserUUID(uuid: string): void {
    this.userUUID$.next(uuid);
  }

  getUserUUID(): string | null {
    return this.userUUID$.getValue();
  }
}
