import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { AuthorsService } from '../../services/authors.service';
import { PublishersService } from '../../services/publishers.service';

@Component({
  selector: 'app-soumya',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './soumya.component.html',
  styleUrls: ['./soumya.component.css']
})
export class SoumyaComponent {
  isModalOpen = false;
  currentModalTitle = '';
  currentModalType: 'GET_ALL' | 'GET_ID' | 'POST' | 'PUT' | 'DELETE' | 'GET_TITLES' | 'GET_EMPLOYEES' | 'REPORT_SALES_PUBLISHER' | null = null;
  currentEntity: 'AUTHOR' | 'PUBLISHER' | 'REPORT' | null = null;

  apiResult: any = null;
  isLoading = false;
  errorMessage = '';
  isEditMode = false;

  protected readonly Array = Array;
  protected readonly Object = Object;
  isArr(val: any): boolean { return Array.isArray(val); }

  filterCity = '';
  filterState = '';
  filterContract: number | null = null;
  searchId = '';

  newAuthor: any = { auId: '', auFname: '', auLname: '', phone: '', address: '', city: '', state: '', zip: '', contract: 0 };
  newPublisher: any = { pubId: '', pubName: '', city: '', state: '', country: '' };

  constructor(
    private authorsService: AuthorsService,
    private publishersService: PublishersService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private router: Router
  ) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  openModal(
    entity: 'AUTHOR' | 'PUBLISHER' | 'REPORT',
    type: 'GET_ALL' | 'GET_ID' | 'POST' | 'PUT' | 'DELETE' | 'GET_TITLES' | 'GET_EMPLOYEES' | 'REPORT_SALES_PUBLISHER',
    title: string
  ) {
    this.currentEntity = entity;
    this.currentModalType = type;
    this.currentModalTitle = title;
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
    this.currentModalType = null;
    this.currentEntity = null;
    this.apiResult = null;
    this.errorMessage = '';
    this.isEditMode = false;
    this.filterCity = '';
    this.filterState = '';
    this.filterContract = null;
    this.searchId = '';
    this.newAuthor = { auId: '', auFname: '', auLname: '', phone: '', address: '', city: '', state: '', zip: '', contract: 0 };
    this.newPublisher = { pubId: '', pubName: '', city: '', state: '', country: '' };
  }

  executeAction() {
    this.isLoading = true;
    this.errorMessage = '';
    this.apiResult = null;

    const safeContract = this.filterContract !== null ? this.filterContract : undefined;

    if (this.currentModalType === 'GET_ALL') {
      const obs = this.currentEntity === 'AUTHOR'
        ? this.authorsService.getAllAuthors(this.filterCity, this.filterState, safeContract)
        : this.publishersService.getAllPublishers();

      obs.subscribe({
        next: (res: any) => {
          const finalArray = res.data || res;
          this.apiResult = (!finalArray || finalArray.length === 0) ? null : finalArray;
          if (!this.apiResult) this.errorMessage = 'No data found.';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: () => { this.errorMessage = 'Fetch failed.'; this.isLoading = false; this.cdr.detectChanges(); }
      });
    } else if (this.currentModalType === 'GET_ID') {
      if (!this.searchId) { this.errorMessage = 'ID required.'; this.isLoading = false; return; }

      const obs = this.currentEntity === 'AUTHOR'
        ? this.authorsService.getAuthorById(this.searchId)
        : this.publishersService.getPublisherById(this.searchId);

      obs.subscribe({
        next: (res: any) => {
          const data = res.data || res;
          this.apiResult = Array.isArray(data) ? data : [data];
          this.isLoading = false; this.cdr.detectChanges();
        },
        error: () => { this.errorMessage = 'Record not found.'; this.isLoading = false; this.cdr.detectChanges(); }
      });
    } else if (this.currentModalType === 'POST') {
      const payload = this.currentEntity === 'AUTHOR' ? this.newAuthor : this.newPublisher;
      const obs = this.currentEntity === 'AUTHOR'
        ? this.authorsService.createAuthor(payload)
        : this.publishersService.createPublisher(payload);

      obs.subscribe({
        next: (res: any) => { this.apiResult = res.data || res; this.isLoading = false; this.cdr.detectChanges(); },
        error: (err: any) => { this.errorMessage = err.error?.message || 'Create failed.'; this.isLoading = false; this.cdr.detectChanges(); }
      });
    } else if (this.currentModalType === 'PUT') {
      if (!this.isEditMode) {
        if (!this.searchId) { this.errorMessage = 'ID required.'; this.isLoading = false; return; }
        const obs = this.currentEntity === 'AUTHOR'
          ? this.authorsService.getAuthorById(this.searchId)
          : this.publishersService.getPublisherById(this.searchId);

        obs.subscribe({
          next: (res: any) => {
            const data = res.data || res;
            if (this.currentEntity === 'AUTHOR') this.newAuthor = { ...data };
            else this.newPublisher = { ...data };
            this.isEditMode = true;
            this.isLoading = false; this.cdr.detectChanges();
          },
          error: () => { this.errorMessage = 'Record not found.'; this.isLoading = false; this.cdr.detectChanges(); }
        });
      } else {
        const payload = this.currentEntity === 'AUTHOR' ? this.newAuthor : this.newPublisher;
        const obs = this.currentEntity === 'AUTHOR'
          ? this.authorsService.updateAuthor(this.searchId, payload)
          : this.publishersService.updatePublisher(this.searchId, payload);

        obs.subscribe({
          next: (res: any) => { this.apiResult = res.data || res; this.isLoading = false; this.cdr.detectChanges(); },
          error: (err: any) => { this.errorMessage = err.error?.message || 'Update failed.'; this.isLoading = false; this.cdr.detectChanges(); }
        });
      }
    } else if (this.currentModalType === 'DELETE') {
      if (!this.searchId) { this.errorMessage = 'ID required to delete.'; this.isLoading = false; return; }

      const obs = this.currentEntity === 'AUTHOR'
        ? this.authorsService.deleteAuthor(this.searchId)
        : this.publishersService.deletePublisher(this.searchId);

      obs.subscribe({
        next: (res: any) => {
          this.apiResult = { message: res.message || 'Deleted successfully.' };
          this.isLoading = false; this.cdr.detectChanges();
        },
        error: () => { this.errorMessage = 'Delete failed.'; this.isLoading = false; this.cdr.detectChanges(); }
      });
    } else if (this.currentModalType === 'GET_TITLES') {
      if (!this.searchId) { this.errorMessage = 'ID required.'; this.isLoading = false; return; }
      const obs = this.currentEntity === 'AUTHOR'
        ? this.authorsService.getTitlesByAuthor(this.searchId)
        : this.publishersService.getTitlesByPublisher(this.searchId);

      obs.subscribe({
        next: (res: any) => {
          const data = res.data || res;
          this.apiResult = Array.isArray(data) ? data : [data];
          this.isLoading = false; this.cdr.detectChanges();
        },
        error: () => { this.errorMessage = 'Titles not found.'; this.isLoading = false; this.cdr.detectChanges(); }
      });
    } else if (this.currentModalType === 'GET_EMPLOYEES' && this.currentEntity === 'PUBLISHER') {
      if (!this.searchId) { this.errorMessage = 'Publisher ID required.'; this.isLoading = false; return; }
      this.publishersService.getEmployeesByPublisher(this.searchId).subscribe({
        next: (res: any) => {
          const data = res.data || res;
          this.apiResult = Array.isArray(data) ? data : [data];
          this.isLoading = false; this.cdr.detectChanges();
        },
        error: () => { this.errorMessage = 'Employees not found.'; this.isLoading = false; this.cdr.detectChanges(); }
      });
    } else if (this.currentModalType === 'REPORT_SALES_PUBLISHER') {
      this.publishersService.getSalesReport().subscribe({
        next: (res: any) => {
          const finalArray = res.data || res;
          this.apiResult = (!finalArray || finalArray.length === 0) ? null : finalArray;
          if (!this.apiResult) this.errorMessage = 'No sales data found for publishers.';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: () => { this.errorMessage = 'Error fetching publisher sales report.'; this.isLoading = false; this.cdr.detectChanges(); }
      });
    }
  }
}
