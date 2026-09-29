import { Component, signal } from '@angular/core';
import { Patient } from '../patient.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-patient-list',
  imports: [DatePipe],
  styleUrl: './patient-list.css',
  templateUrl: './patient-list.html',
})
export class PatientList {
  patients = signal<Patient[]>([
    { id: 1, prenom: 'Marie', nom: 'Curie', email: 'marie.curie@example.com', dateNaissance: new Date(1867, 10, 7).toISOString() },
    { id: 2, prenom: 'Alan', nom: 'Turing', email: 'alan.turing@example.com', dateNaissance: new Date(1912, 5, 23).toISOString() },
    { id: 3, prenom: 'Ada', nom: 'Lovelace', email: 'ada.lovelace@example.com', dateNaissance: new Date(1815, 11, 10).toISOString() },
    { id: 4, prenom: 'Linus', nom: 'Torvalds', email: 'linus.torvalds@example.com', dateNaissance: new Date(1969, 11, 28).toISOString() },
    { id: 5, prenom: 'Margaret', nom: 'Hamilton', email: 'margaret.hamilton@example.com', dateNaissance: new Date(1936, 7, 17).toISOString() }
  ]);

  selection = signal<Patient | null>(null);
  estSenior(p: Patient): boolean {
    return new Date(p.dateNaissance).getFullYear() < 1950;
  }
}

