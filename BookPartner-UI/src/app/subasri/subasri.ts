import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-subasri',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './subasri.html',
  styleUrls: ['./subasri.css'],
})
export class Subasri {

  selectedAction: string = '';
  selectedEntity: string = '';

  searchId: string = '';
  searchResult: any = null;

  employees = [
    { id: 'E101', name: 'John', job: 'Manager', pubId: 'P01' },
    { id: 'E102', name: 'Alice', job: 'Developer', pubId: 'P02' }
  ];

  jobs = [
    { id: 1, desc: 'Software Engineer', min: 10, max: 50 },
    { id: 2, desc: 'Manager', min: 50, max: 100 }
  ];

  employeeForm = { id: '', name: '', job: '', pubId: '' };
  jobForm = { id: 0, desc: '', min: 0, max: 0 };

  handleClick(entity: string, action: string) {
    this.selectedEntity = entity;
    this.selectedAction = action;
    this.searchResult = null;
  }

  //GET BY ID LOGIC
 search() {
  if (this.selectedEntity === 'employee') {
    this.searchResult = this.employees.find(e => e.id === this.searchId);
  } else {
    this.searchResult = this.jobs.find(j => j.id === Number(this.searchId));
  }
}

  submitEmployee() {
    console.log(this.employeeForm);
  }

  submitJob() {
    console.log(this.jobForm);
  }
}