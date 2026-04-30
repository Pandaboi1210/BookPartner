import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { StoreService } from '../../services/store.service';
import { SalesService } from '../../services/sales.service';
import { ReportService } from '../../services/report.service';


type ModalEntity = 'STORE' | 'SALES' | 'REPORT';
type ModalType =
  | 'GET_ALL' | 'GET_ID' | 'POST' | 'PUT'
  | 'GET_SALES_BY_STORE' | 'GET_DISCOUNTS'
  | 'GET_BY_STORE' | 'GET_BY_TITLE' | 'GET_BY_DATE'
  | 'SALES_TREND' | 'TOP_AUTHORS';

@Component({
  selector: 'app-harini',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './harini.html'
})
export class HariniComponent {

  isModalOpen = false;
  currentModalTitle = '';
  currentModalType: ModalType | null = null;
  currentEntity: ModalEntity | null = null;

  apiResult: any = null;
  isLoading = false;
  errorMessage = '';
  successMessage = '';
  isEditMode = false;

  // Shared search fields
  searchId = '';
  dateFrom = '';
  dateTo = '';

  // Store form
  newStore: any = {
    storId: '', storName: '', storAddress: '', city: '', state: '', zip: ''
  };

  // Sale form
  newSale: any = {
    storId: '', ordNum: '', titleId: '', ordDate: '', qty: 1, payterms: ''
  };

  constructor(
    private storeService: StoreService,
    private salesService: SalesService,
    private reportService: ReportService,
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
    this.dateFrom = '';
    this.dateTo = '';
    this.newStore = { storId: '', storName: '', storAddress: '', city: '', state: '', zip: '' };
    this.newSale = { storId: '', ordNum: '', titleId: '', ordDate: '', qty: 1, payterms: '' };
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
    this.dateFrom = '';
    this.dateTo = '';
    this.newStore = { storId: '', storName: '', storAddress: '', city: '', state: '', zip: '' };
    this.newSale = { storId: '', ordNum: '', titleId: '', ordDate: '', qty: 1, payterms: '' };
  }

  executeAction() {
    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.apiResult = null;

    // ===== STORE: GET ALL =====
    if (this.currentEntity === 'STORE' && this.currentModalType === 'GET_ALL') {
      this.storeService.getAllStores().subscribe({
        next: (data) => {
          this.apiResult = data;
          if (!data || data.length === 0) this.errorMessage = 'No stores found.';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, 'No stores found.');
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== STORE: GET BY ID =====
    else if (this.currentEntity === 'STORE' && this.currentModalType === 'GET_ID') {
      if (!this.searchId.trim()) {
        this.errorMessage = 'Store ID must not be blank.';
        this.isLoading = false;
        return;
      }
      this.storeService.getStoreById(this.searchId.trim()).subscribe({
        next: (data) => {
          this.apiResult = Array.isArray(data) ? data : [data];
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, `Store not found with ID: ${this.searchId.trim()}`);
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== STORE: POST (CREATE) =====
    else if (this.currentEntity === 'STORE' && this.currentModalType === 'POST') {
      if (!this.newStore.storId?.trim()) {
        this.errorMessage = 'Store ID must not be blank.';
        this.isLoading = false;
        return;
      }
      const payload = {
        storId: this.newStore.storId.trim(),
        storName: this.newStore.storName?.trim() || null,
        storAddress: this.newStore.storAddress?.trim() || null,
        city: this.newStore.city?.trim() || null,
        state: this.newStore.state?.trim() || null,
        zip: this.newStore.zip?.trim() || null
      };
      this.storeService.createStore(payload).subscribe({
        next: (data) => {
          this.apiResult = data;
          this.successMessage = 'Store created successfully!';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, 'Store already exists or invalid data provided.');
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== STORE: PUT (UPDATE) — STEP 1: load =====
    else if (this.currentEntity === 'STORE' && this.currentModalType === 'PUT' && !this.isEditMode) {
      if (!this.searchId.trim()) {
        this.errorMessage = 'Store ID must not be blank.';
        this.isLoading = false;
        return;
      }
      this.storeService.getStoreById(this.searchId.trim()).subscribe({
        next: (data: any) => {
          this.newStore = { ...data };
          this.isEditMode = true;
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, `Store not found with ID: ${this.searchId.trim()}`);
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== STORE: PUT (UPDATE) — STEP 2: save =====
    else if (this.currentEntity === 'STORE' && this.currentModalType === 'PUT' && this.isEditMode) {
      const payload = {
        storId: this.newStore.storId?.trim() || null,
        storName: this.newStore.storName?.trim() || null,
        storAddress: this.newStore.storAddress?.trim() || null,
        city: this.newStore.city?.trim() || null,
        state: this.newStore.state?.trim() || null,
        zip: this.newStore.zip?.trim() || null
      };
      this.storeService.updateStore(this.searchId.trim(), payload).subscribe({
        next: (data) => {
          this.apiResult = data;
          this.successMessage = 'Store updated successfully!';
          this.isEditMode = false;
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, `Store not found with ID: ${this.searchId.trim()}`);
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== STORE: GET SALES BY STORE =====
    else if (this.currentEntity === 'STORE' && this.currentModalType === 'GET_SALES_BY_STORE') {
      if (!this.searchId.trim()) {
        this.errorMessage = 'Store ID must not be blank.';
        this.isLoading = false;
        return;
      }
      this.storeService.getSalesByStore(this.searchId.trim()).subscribe({
        next: (data) => {
          this.apiResult = data;
          if (!data || data.length === 0) this.errorMessage = `No sales found for store ID: ${this.searchId.trim()}`;
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, `No sales found for store ID: ${this.searchId.trim()}`);
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== STORE: GET DISCOUNTS =====
    else if (this.currentEntity === 'STORE' && this.currentModalType === 'GET_DISCOUNTS') {
      if (!this.searchId.trim()) {
        this.errorMessage = 'Store ID must not be blank.';
        this.isLoading = false;
        return;
      }
      this.storeService.getDiscountsByStore(this.searchId.trim()).subscribe({
        next: (data) => {
          this.apiResult = data;
          if (!data || data.length === 0) this.errorMessage = `No discounts found for store ID: ${this.searchId.trim()}`;
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, `No discounts found for store ID: ${this.searchId.trim()}`);
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== SALES: GET ALL =====
    else if (this.currentEntity === 'SALES' && this.currentModalType === 'GET_ALL') {
      this.salesService.getAllSales().subscribe({
        next: (data) => {
          this.apiResult = data;
          if (!data || data.length === 0) this.errorMessage = 'No sales records found.';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, 'No sales records found.');
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== SALES: POST (CREATE) =====
    else if (this.currentEntity === 'SALES' && this.currentModalType === 'POST') {
      if (!this.newSale.storId?.trim()) { this.errorMessage = 'Store ID must not be blank.'; this.isLoading = false; return; }
      if (!this.newSale.ordNum?.trim()) { this.errorMessage = 'Order Number must not be blank.'; this.isLoading = false; return; }
      if (!this.newSale.titleId?.trim()) { this.errorMessage = 'Title ID must not be blank.'; this.isLoading = false; return; }
      if (!this.newSale.ordDate) { this.errorMessage = 'Order date is required.'; this.isLoading = false; return; }
      if (!this.newSale.payterms?.trim()) { this.errorMessage = 'Payment terms must not be blank.'; this.isLoading = false; return; }

      const qtyValue = Number(this.newSale.qty);
      if (isNaN(qtyValue) || qtyValue < 1) { this.errorMessage = 'Quantity must be at least 1.'; this.isLoading = false; return; }

      let ordDateValue: string = this.newSale.ordDate;
      if (ordDateValue && ordDateValue.length === 16) {
        ordDateValue = ordDateValue + ':00';
      }

      const payload = {
        storId: this.newSale.storId.trim(),
        ordNum: this.newSale.ordNum.trim(),
        titleId: this.newSale.titleId.trim().toUpperCase(),
        ordDate: ordDateValue,
        qty: qtyValue,
        payterms: this.newSale.payterms.trim()
      };

      this.salesService.createSale(payload).subscribe({
        next: (data) => {
          this.apiResult = data;
          this.successMessage = 'Sale created successfully!';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, 'Sale already exists or invalid data provided.');
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== SALES: GET BY STORE =====
    else if (this.currentEntity === 'SALES' && this.currentModalType === 'GET_BY_STORE') {
      if (!this.searchId.trim()) { this.errorMessage = 'Store ID must not be blank.'; this.isLoading = false; return; }
      this.salesService.getSalesByStore(this.searchId.trim()).subscribe({
        next: (data) => {
          this.apiResult = data;
          if (!data || data.length === 0) this.errorMessage = `No sales found for store ID: ${this.searchId.trim()}`;
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, `No sales found for store ID: ${this.searchId.trim()}`);
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== SALES: GET BY TITLE =====
    else if (this.currentEntity === 'SALES' && this.currentModalType === 'GET_BY_TITLE') {
  if (!this.searchId.trim()) {
    this.errorMessage = 'Title ID must not be blank.';
    this.isLoading = false;
    return;
  }

  this.salesService.getSalesByTitle(this.searchId.trim()).subscribe({
    next: (data) => {
      this.apiResult = data;

      if (!data || data.length === 0) {
        this.errorMessage =
          `No sales records found for title ID: ${this.searchId.trim()}`;
      }

      this.isLoading = false;
      this.cdr.detectChanges();
    },

    error: (err) => {
      this.errorMessage = this.resolveErrorMessage(
        err,
        `Title ID does not exist: ${this.searchId.trim()}`
      );

      this.isLoading = false;
      this.cdr.detectChanges();
    }
  });
}

// ===== SALES: GET BY DATE RANGE =====
    else if (this.currentEntity === 'SALES' && this.currentModalType === 'GET_BY_DATE') {
      if (!this.dateFrom || !this.dateTo) {
        this.errorMessage = 'Please select both from and to dates.';
        this.isLoading = false;
        return;
      }
      const from = this.dateFrom + 'T00:00:00';
      const to = this.dateTo + 'T23:59:59';
      this.salesService.getSalesByDateRange(from, to).subscribe({
        next: (data) => {
          this.apiResult = data;
          if (!data || data.length === 0) this.errorMessage = 'No sales found in this date range.';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'Error fetching sales by date range.';
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== REPORT: SALES TREND =====
    else if (this.currentEntity === 'REPORT' && this.currentModalType === 'SALES_TREND') {
      if (!this.dateFrom || !this.dateTo) {
        this.errorMessage = 'Both from and to dates are required.';
        this.isLoading = false;
        return;
      }
      this.reportService.getSalesTrend(this.dateFrom, this.dateTo).subscribe({
        next: (data) => {
          this.apiResult = data;
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, 'No sales trend data found for the given date range.');
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    // ===== REPORT: TOP AUTHORS =====
    else if (this.currentEntity === 'REPORT' && this.currentModalType === 'TOP_AUTHORS') {
      this.reportService.getTopAuthorsBySales().subscribe({
        next: (data) => {
          this.apiResult = data;
          if (!data || data.length === 0) this.errorMessage = 'No authors found.';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = this.resolveErrorMessage(err, 'No authors found.');
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }

    else {
      this.isLoading = false;
    }
  }

  isArray(val: any): boolean {
    return Array.isArray(val);
  }

  objectKeys(obj: any): string[] {
    return obj ? Object.keys(obj) : [];
  }
}