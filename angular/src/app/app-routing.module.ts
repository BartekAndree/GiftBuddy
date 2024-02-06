import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './components/features/home-page/home-page.component';
import { CreateEventComponent } from './components/features/create-event/create-event.component';
import { EventDetailsComponent } from './components/features/event-details/event-details.component';
import { validUuidGuard } from './utils/guards/valid-uuid.guard';
import { AuthGuard } from './utils/guards/auth.guard';
import { EventListComponent } from './components/features/event-list/event-list.component';
import { EditEventComponent } from './components/features/edit-event/edit-event.component';

const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
  },
  {
    path: 'event',
    component: CreateEventComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'my-events',
    component: EventListComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'event/:eventId',
    component: EventDetailsComponent,
    canActivate: [AuthGuard, validUuidGuard],
  },
  {
    path: 'event/:eventId/edit',
    component: EditEventComponent,
    canActivate: [AuthGuard, validUuidGuard],
  },
  {
    path: '**',
    redirectTo: '',
  },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
