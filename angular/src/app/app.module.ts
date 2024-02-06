import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { HomePageComponent } from './components/features/home-page/home-page.component';
import { EventDetailsComponent } from './components/features/event-details/event-details.component';
import { CreateEventComponent } from './components/features/create-event/create-event.component';
import { VirtualScrollerModule } from 'primeng/virtualscroller';
import { ImageModule } from 'primeng/image';
import { ProgressBarModule } from 'primeng/progressbar';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';
import { FileUploadModule } from 'primeng/fileupload';
import { CheckboxModule } from 'primeng/checkbox';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { ToastModule } from 'primeng/toast';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ParticipantsListComponent } from './components/features/participants-list/participants-list.component';
import { NgOptimizedImage } from '@angular/common';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { initializeKeycloak } from './init/keycloak-init.factory';
import { SessionService } from './services/session/session.service';
import { RippleModule } from 'primeng/ripple';
import { CardModule } from 'primeng/card';
import { ToolbarModule } from 'primeng/toolbar';
import { ChipModule } from 'primeng/chip';
import { EventListComponent } from './components/features/event-list/event-list.component';
import { PlnCurrencyPipe } from './utils/pipes/pln-currency.pipe';
import { MenuModule } from 'primeng/menu';
import { EditEventComponent } from './components/features/edit-event/edit-event.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { AvatarModule } from 'primeng/avatar';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    HomePageComponent,
    EventDetailsComponent,
    CreateEventComponent,
    ParticipantsListComponent,
    EventListComponent,
    PlnCurrencyPipe,
    EditEventComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ButtonModule,
    VirtualScrollerModule,
    ImageModule,
    ProgressBarModule,
    DialogModule,
    TableModule,
    CalendarModule,
    FileUploadModule,
    CheckboxModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    InputTextareaModule,
    InputNumberModule,
    ToastModule,
    ProgressSpinnerModule,
    NgOptimizedImage,
    KeycloakAngularModule,
    RippleModule,
    CardModule,
    ToolbarModule,
    ChipModule,
    MenuModule,
    ConfirmDialogModule,
    AvatarModule,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService, SessionService],
    },
    ConfirmationService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
