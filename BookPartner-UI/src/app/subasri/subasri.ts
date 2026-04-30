
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { EmployeeService } from '../../services/employee.service';
import { JobsService } from '../../services/jobs.service';

type ModalEntity = 'EMPLOYEE' | 'JOB';
type ModalType = 'GET_ALL' | 'GET_ID' | 'POST' | 'PUT' | 'DELETE';

@Component({
  selector: 'app-subasri',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './subasri.html'
})
export class SubasriComponent {

  isModalOpen = false;
  currentModalTitle = '';
  currentModalType: ModalType | null = null;
  currentEntity: ModalEntity | null = null;

  apiResult: any = null;
  isLoading = false;
  errorMessage = '';
  successMessage = '';
  isEditMode = false;

  searchId: string = '';

  newEmployee: any = {
    empId: '', fname: '', lname: '', hireDate: '', jobLvl: '', pubId: '', jobId: ''
  };
  newJob: any = {
    jobId: '', jobDesc: '', minLvl: '', maxLvl: ''
  };

  constructor(
    private empService: EmployeeService,
    private jobService: JobsService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private router: Router
  ) {}

  private resolveErrorMessage(err: any, fallback: string): string {
    if (typeof err?.error === 'string' && err.error.trim().length > 0) {
      return err.error;
    }
    if (err?.error?.message) {
      return err.error.message;
    }
    if (err?.message) {
      return err.message;
    }
    return fallback;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  openModal(entity: ModalEntity, type: ModalType, title: string) {
    this.currentEntity = entity;
    this.currentModalType = type;
    this.currentModalTitle = title;
    this.isModalOpen = true;
    this.apiResult = null;
    this.errorMessage = '';
    this.successMessage = '';
    this.isEditMode = false;
    this.searchId = '';
    this.newEmployee = { empId: '', fname: '', lname: '', hireDate: '', jobLvl: '', pubId: '', jobId: '' };
    this.newJob = { jobId: '', jobDesc: '', minLvl: '', maxLvl: '' };
  }

  closeModal() {
    this.isModalOpen = false;
    this.currentModalType = null;
    this.currentEntity = null;
    this.apiResult = null;
    this.errorMessage = '';
    this.successMessage = '';
    this.isEditMode = false;
    this.searchId = '';
    this.newEmployee = { empId: '', fname: '', lname: '', hireDate: '', jobLvl: '', pubId: '', jobId: '' };
    this.newJob = { jobId: '', jobDesc: '', minLvl: '', maxLvl: '' };
  }

  executeAction() {
    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.apiResult = null;

    // ================= EMPLOYEE =================
    if (this.currentEntity === 'EMPLOYEE') {

      // GET ALL
      if (this.currentModalType === 'GET_ALL') {
        this.empService.getAll().subscribe({
          next: res => {
            this.apiResult = res;
            if (!res || res.length === 0) this.errorMessage = 'No employees found.';
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, 'Fetch failed');
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }

      // GET BY ID
      else if (this.currentModalType === 'GET_ID') {
        if (!this.searchId.trim()) {
          this.errorMessage = 'Please enter an Employee ID.';
          this.isLoading = false;
          return;
        }
        this.empService.getById(this.searchId).subscribe({
          next: res => {
            this.apiResult = [res];
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, 'Employee not found');
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }

      // ADD EMPLOYEE
      else if (this.currentModalType === 'POST') {
        if (!this.newEmployee.empId?.trim()) {
          this.errorMessage = 'Employee ID is required.';
          this.isLoading = false;
          return;
        }
        this.empService.create(this.newEmployee).subscribe({
          next: (data) => {
            this.apiResult = data;
            this.successMessage = 'Employee added successfully!';
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, 'Add failed');
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }

      // UPDATE EMPLOYEE — STEP 1: load
      else if (this.currentModalType === 'PUT' && !this.isEditMode) {
        if (!this.searchId.trim()) {
          this.errorMessage = 'Please enter an Employee ID to update.';
          this.isLoading = false;
          return;
        }
        this.empService.getById(this.searchId.trim()).subscribe({
          next: (data: any) => {
            this.newEmployee = { ...data };
            this.isEditMode = true;
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, `Employee not found with ID: ${this.searchId}`);
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }

      // UPDATE EMPLOYEE — STEP 2: save
      else if (this.currentModalType === 'PUT' && this.isEditMode) {
        this.empService.update(this.newEmployee.empId, this.newEmployee).subscribe({
          next: (data) => {
            this.apiResult = data;
            this.successMessage = 'Employee updated successfully!';
            this.isEditMode = false;
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, 'Update failed');
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }

      // DELETE EMPLOYEE
      else if (this.currentModalType === 'DELETE') {
        if (!this.searchId.trim()) {
          this.errorMessage = 'Please enter an Employee ID to delete.';
          this.isLoading = false;
          return;
        }
        this.empService.delete(this.searchId).subscribe({
          next: () => {
            this.successMessage = 'Employee deleted successfully!';
            this.apiResult = { status: 'Deleted', empId: this.searchId };
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, 'Delete failed');
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }
    }

    // ================= JOB =================
    else if (this.currentEntity === 'JOB') {

      // GET ALL
      if (this.currentModalType === 'GET_ALL') {
        this.jobService.getAll().subscribe({
          next: res => {
            this.apiResult = res;
            if (!res || res.length === 0) this.errorMessage = 'No jobs found.';
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, 'Fetch failed');
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }

      // GET BY ID
      else if (this.currentModalType === 'GET_ID') {
        if (!this.searchId.trim()) {
          this.errorMessage = 'Please enter a Job ID.';
          this.isLoading = false;
          return;
        }
        const jobIdNum = Number(this.searchId);
        if (isNaN(jobIdNum) || jobIdNum < 1 || jobIdNum > 32767) {
          this.errorMessage = `Job ID must be a valid number between 1 and 32767. Received: ${this.searchId}`;
          this.isLoading = false;
          return;
        }
        this.jobService.getById(Number(this.searchId)).subscribe({
          next: res => {
            this.apiResult = [res];
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, `Job not found with ID: ${this.searchId}`);
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }

      // ADD JOB
      else if (this.currentModalType === 'POST') {
        if (!this.newJob.jobId) {
          this.errorMessage = 'Job ID is required.';
          this.isLoading = false;
          return;
        }
        this.jobService.create(this.newJob).subscribe({
          next: (data) => {
            this.apiResult = data;
            this.successMessage = 'Job added successfully!';
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            this.errorMessage = this.resolveErrorMessage(err, 'Add failed');
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        });
      }
    }
  }

  isArray(val: any): boolean {
    return Array.isArray(val);
  }

  objectKeys(obj: any): string[] {
    return obj ? Object.keys(obj) : [];
  }
}