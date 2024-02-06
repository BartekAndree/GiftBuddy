import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent {
  items: MenuItem[] | undefined;
  ngOnInit() {
    this.items = [
      {
        label: 'Create an account',
        routerLink: 'personal'
      },
      {
        label: 'Create an event',
        routerLink: 'seat'
      },
      {
        label: 'Invite friends',
        routerLink: 'payment'
      },
      {
        label: 'Track progress',
        routerLink: 'confirmation'
      }
    ];
  }
}
