import { Component } from '@angular/core';
import { PatientCard } from './patient-card/patient-card';
import { PatientList } from './patient-list/patient-list';

@Component({
  selector: 'app-root',
  imports: [PatientCard, PatientList],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App { }