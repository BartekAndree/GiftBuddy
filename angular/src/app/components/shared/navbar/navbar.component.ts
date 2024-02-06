import { Component } from '@angular/core';
import { SessionService } from '../../../services/session/session.service';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakLoginOptions, KeycloakRegisterOptions } from 'keycloak-js';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  items: MenuItem[] | undefined;
  keycloakLoginOptions: KeycloakLoginOptions = {
    redirectUri: window.location.origin + '/my-events',
  };
  keycloakRegisterOptions: KeycloakRegisterOptions = {
    redirectUri: window.location.origin + '/my-events',
  };
  logoutRedirectUri: string = window.location.origin + '';


  constructor(
    private keycloakService: KeycloakService,
    private sessionService: SessionService,
  ) {
  }

  ngOnInit(): void {
    this.items = [
      {
        label: 'Actions',
        items: [
          {
            label: 'My Events',
            icon: 'pi pi-calendar',
            routerLink: '/my-events',
          },
          {
            label: 'Create Event',
            icon: 'pi pi-calendar-plus',
            routerLink: '/event',
          },
        ],
      },
      {
        label: 'Options',
        items: [
          {
            label: this.getUsername(),
            icon: 'pi pi-user-edit',
            command: () => {
            },
          },
          {
            label: 'Logout',
            icon: 'pi pi-sign-out',
            command: () => {
              this.handleLogoutClick();
            },
          },
        ],
      },
    ];
  }

  handleLogoutClick(): void {
    this.keycloakService.logout(this.logoutRedirectUri).then(r => {
      console.log('Logout successful');
    });
  }

  handleLoginClick(): void {
    this.keycloakService.login(this.keycloakLoginOptions).then(r => {
      console.log('Login successful');
    });
  }

  handleRegisterClick(): void {
    this.keycloakService.register(this.keycloakRegisterOptions).then(r => {
      console.log('Registration successful');
    });
  }

  isUserLoggedIn(): boolean {
    return this.sessionService.getUserUUID() !== null;
  }

  getUsername(): string {
    return this.keycloakService.getUsername();
  }

}
