import { Component, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Patient } from '../patient.model';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-patient-card',
  imports: [DatePipe, FormsModule],
  templateUrl: './patient-card.html',
  styleUrl: './patient-card.css'
})
export class PatientCard {
  patient = signal<Patient>({
    id: 1,
    prenom: 'Marie',
    nom: 'Curie',
    email: 'marie.curie@example.com',
    dateNaissance: new Date(1867, 10, 7).toISOString()
  });

  nom = signal('Curie');
}