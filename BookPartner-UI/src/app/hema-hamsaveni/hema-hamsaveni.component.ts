import { Component, ChangeDetectorRef } from '@angular/core'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { TitleService } from '../../services/title.service'; 
import { TitleAuthorService } from '../../services/title-author.service'; 

@Component({
  selector: 'app-hema-hamsaveni',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './hema-hamsaveni.component.html',
  styleUrls: ['./hema-hamsaveni.component.css']
})
export class HemaHamsaveniComponent {
  
  isModalOpen = false;
  currentModalTitle = '';
  currentModalType: 'GET_ALL' | 'GET_ID' | 'POST' | 'PUT' | 'DELETE' | 'GET_AUTHORS' | 'GET_SALES' | 'GET_ROYALTIES' | 'REPORT_SALES_TITLE' | null = null;
  currentEntity: 'TITLE' | 'TITLE_AUTHOR' | 'REPORT' | null = null;

  apiResult: any = null;
  isLoading: boolean = false;
  errorMessage: string = '';
  isEditMode: boolean = false; 
  
  protected readonly Array = Array;
  protected readonly Object = Object;
  isArr(val: any): boolean { return Array.isArray(val); }

  filterType: string = '';
  filterPublisher: string = '';
  filterMinPrice: number | null = null;
  filterMaxPrice: number | null = null;
  
  searchId: string = '';
  searchAuthorId: string = '';

  newTitle: any = {
    titleId: '', title: '', type: 'UNDECIDED', pubId: '', price: 0.0, advance: 0.0, royalty: 0, ytdSales: 0, notes: '', pubdate: '' 
  };

  newLink: any = {
    auId: '', titleId: '', auOrd: 1, royaltyper: 0
  };

  constructor(
    private titleService: TitleService,
    private titleAuthorService: TitleAuthorService,
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

  openModal(
    entity: 'TITLE' | 'TITLE_AUTHOR' | 'REPORT', 
    type: 'GET_ALL' | 'GET_ID' | 'POST' | 'PUT' | 'DELETE' | 'GET_AUTHORS' | 'GET_SALES' | 'GET_ROYALTIES' | 'REPORT_SALES_TITLE', 
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
    this.filterType = '';
    this.filterPublisher = '';
    this.filterMinPrice = null;
    this.filterMaxPrice = null;
    this.searchId = '';
    this.searchAuthorId = '';
    this.newTitle = { titleId: '', title: '', type: 'UNDECIDED', pubId: '', price: 0.0, advance: 0.0, royalty: 0, ytdSales: 0, notes: '', pubdate: '' };
    this.newLink = { auId: '', titleId: '', auOrd: 1, royaltyper: 0 };
  }

  private validateTitlePayload(): string | null {
    if (!this.newTitle.titleId || this.newTitle.titleId.trim() === '') return 'Title ID is required.';
    if (this.newTitle.titleId.length > 10) return `Title ID cannot exceed 10 characters. Received: ${this.newTitle.titleId}`;
    if (!this.newTitle.title || this.newTitle.title.trim() === '') return 'Book Name is required.';
    if (this.newTitle.title.length > 80) return `Book Name cannot exceed 80 characters. Received length: ${this.newTitle.title.length}`;
    if (!this.newTitle.type || this.newTitle.type.trim() === '') return 'Type is required.';
    if (this.newTitle.type.length > 12) return `Type cannot exceed 12 characters. Received: ${this.newTitle.type}`;
    if (this.newTitle.pubId && this.newTitle.pubId.length > 4) return `Publisher ID cannot exceed 4 characters. Received: ${this.newTitle.pubId}`;
    if (this.newTitle.price !== null && this.newTitle.price < 0) return `Price cannot be negative. Received: ${this.newTitle.price}`;
    if (this.newTitle.advance !== null && this.newTitle.advance < 0) return `Advance cannot be negative. Received: ${this.newTitle.advance}`;
    if (this.newTitle.royalty !== null && this.newTitle.royalty < 0) return `Royalty percentage cannot be negative. Received: ${this.newTitle.royalty}`;
    if (this.newTitle.ytdSales !== null && this.newTitle.ytdSales < 0) return `Year-to-date sales cannot be negative. Received: ${this.newTitle.ytdSales}`;
    if (this.newTitle.notes && this.newTitle.notes.length > 200) return `Notes cannot exceed 200 characters. Received length: ${this.newTitle.notes.length}`;
    if (!this.newTitle.pubdate) return 'Publication date is required.';
    return null; 
  }

  executeAction() {
    this.isLoading = true;
    this.errorMessage = '';
    this.apiResult = null;

    // --- HANDLE: GET ALL TITLES ---
    if (this.currentModalType === 'GET_ALL') {
      
      // Prevent invalid price ranges
      if (this.filterMinPrice !== null && this.filterMaxPrice !== null && this.filterMinPrice > this.filterMaxPrice) {
        this.errorMessage = 'Minimum price cannot be greater than maximum price.';
        this.isLoading = false; return;
      }

      this.titleService.getAllTitles(this.filterType, this.filterPublisher, this.filterMinPrice, this.filterMaxPrice).subscribe({
        next: (data) => { 
          let finalArray = data;
          if (data && !Array.isArray(data)) {
            finalArray = data.content || data.data || [data];
          }
          this.apiResult = (!finalArray || finalArray.length === 0) ? null : finalArray;
          if (!this.apiResult) this.errorMessage = 'No data found.';
          this.isLoading = false; 
          this.cdr.detectChanges(); 
        },
        error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, 'Fetch failed.'); this.isLoading = false; this.cdr.detectChanges(); }
      });
    }
    
    // --- HANDLE: GET TITLE BY ID ---
    else if (this.currentModalType === 'GET_ID') {
      if (!this.searchId) { this.errorMessage = 'ID required.'; this.isLoading = false; return; }
      this.titleService.getTitleById(this.searchId).subscribe({
        next: (data) => { 
          this.apiResult = Array.isArray(data) ? data : [data]; 
          this.isLoading = false; this.cdr.detectChanges(); 
        },
        error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, `Title not found with ID: ${this.searchId}`); this.isLoading = false; this.cdr.detectChanges(); }
      });
    }

    // --- HANDLE: POST (CREATE) TITLE ---
    else if (this.currentModalType === 'POST' && this.currentEntity === 'TITLE') {
      const validationError = this.validateTitlePayload();
      if (validationError) { this.errorMessage = validationError; this.isLoading = false; return; }
      const payload = { ...this.newTitle, pubdate: this.newTitle.pubdate + 'T00:00:00' };
      this.titleService.createTitle(payload).subscribe({
        next: (data) => { this.apiResult = data; this.isLoading = false; this.cdr.detectChanges(); },
        error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, 'Create failed.'); this.isLoading = false; this.cdr.detectChanges(); }
      });
    }

    // --- HANDLE: PUT (UPDATE) TITLE ---
    else if (this.currentModalType === 'PUT' && this.currentEntity === 'TITLE') {
      if (!this.isEditMode) {
        if (!this.searchId) { this.errorMessage = 'ID required.'; this.isLoading = false; return; }
        this.titleService.getTitleById(this.searchId).subscribe({
          next: (data: any) => {
            this.newTitle = { ...data };
            if (this.newTitle.pubdate) this.newTitle.pubdate = this.newTitle.pubdate.substring(0, 10);
            this.isEditMode = true; 
            this.isLoading = false; this.cdr.detectChanges();
          },
          error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, `Title not found with ID: ${this.searchId}`); this.isLoading = false; this.cdr.detectChanges(); }
        });
      } 
      else {
        const validationError = this.validateTitlePayload();
        if (validationError) { this.errorMessage = validationError; this.isLoading = false; return; }
        const payload = { ...this.newTitle, pubdate: this.newTitle.pubdate + 'T00:00:00' };
        this.titleService.updateTitle(this.searchId, payload).subscribe({
          next: (data) => { this.apiResult = data; this.isLoading = false; this.cdr.detectChanges(); },
          error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, 'Update failed.'); this.isLoading = false; this.cdr.detectChanges(); }
        });
      }
    }

    // --- HANDLE: POST (LINK AUTHOR) ---
    else if (this.currentModalType === 'POST' && this.currentEntity === 'TITLE_AUTHOR') {
      
      //Frontend Validations matching Backend DTO
      const auIdRegex = /^[0-9]{3}-[0-9]{2}-[0-9]{4}$/;
      if (!this.newLink.auId || !auIdRegex.test(this.newLink.auId)) {
        this.errorMessage = `Author ID is required and must be in XXX-XX-XXXX format. Received: ${this.newLink.auId || '(empty)'}`;
        this.isLoading = false; return;
      }
      if (!this.newLink.titleId || this.newLink.titleId.length > 10) {
        this.errorMessage = `Title ID is required and cannot exceed 10 characters. Received: ${this.newLink.titleId || '(empty)'}`;
        this.isLoading = false; return;
      }
      if (this.newLink.auOrd !== null && this.newLink.auOrd < 1) {
        this.errorMessage = `Author order must be at least 1. Received: ${this.newLink.auOrd}`;
        this.isLoading = false; return;
      }
      if (this.newLink.royaltyper !== null && (this.newLink.royaltyper < 0 || this.newLink.royaltyper > 100)) {
        this.errorMessage = `Royalty percentage must be between 0 and 100. Received: ${this.newLink.royaltyper}`;
        this.isLoading = false; return;
      }

      this.titleAuthorService.createTitleAuthor(this.newLink).subscribe({
        next: (data) => { this.apiResult = data; this.isLoading = false; this.cdr.detectChanges(); },
        error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, `Link failed for Author ID: ${this.newLink.auId} and Title ID: ${this.newLink.titleId}`); this.isLoading = false; this.cdr.detectChanges(); }
      });
    }

    // --- HANDLE: DELETE (REMOVE LINK) ---
    else if (this.currentModalType === 'DELETE' && this.currentEntity === 'TITLE_AUTHOR') {
      if (!this.searchAuthorId || !this.searchId) {
        this.errorMessage = 'Both Author ID and Title ID are required to delete a link.';
        this.isLoading = false; return;
      }
      this.titleAuthorService.deleteTitleAuthor(this.searchAuthorId, this.searchId).subscribe({
        next: () => { this.apiResult = { success: true, message: 'Unlinked successfully.' }; this.isLoading = false; this.cdr.detectChanges(); },
        error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, `Title-Author link not found for Author ID: ${this.searchAuthorId} and Title ID: ${this.searchId}`); this.isLoading = false; this.cdr.detectChanges(); }
      });
    }

    // --- RELATIONSHIP GETTERS ---
    else if (this.currentModalType === 'GET_AUTHORS' || this.currentModalType === 'GET_SALES' || this.currentModalType === 'GET_ROYALTIES') {
      if (!this.searchId) { this.errorMessage = 'Title ID required.'; this.isLoading = false; return; }
      const obs = this.currentModalType === 'GET_AUTHORS' ? this.titleService.getAuthorsByTitle(this.searchId) :
                  this.currentModalType === 'GET_SALES' ? this.titleService.getSalesByTitle(this.searchId) :
                  this.titleService.getRoyaltiesByTitle(this.searchId);
      
      obs.subscribe({
        next: (data) => { this.apiResult = Array.isArray(data) ? data : [data]; this.isLoading = false; this.cdr.detectChanges(); },
        error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, `Data not found for Title with ID: ${this.searchId}`); this.isLoading = false; this.cdr.detectChanges(); }
      });
    }

    // HANDLE TOTAL SALES REPORT
    else if (this.currentModalType === 'REPORT_SALES_TITLE') {
      this.titleService.getTotalSalesByTitle().subscribe({
        next: (data) => {
          let finalArray = data;
          if (data && !Array.isArray(data)) {
            finalArray = data.content || data.data || [data];
          }
          this.apiResult = (!finalArray || finalArray.length === 0) ? null : finalArray;
          if (!this.apiResult) this.errorMessage = 'No sales data found.';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err: any) => { this.errorMessage = this.resolveErrorMessage(err, 'Error fetching report.'); this.isLoading = false; this.cdr.detectChanges(); }
      });
    }
  }
}