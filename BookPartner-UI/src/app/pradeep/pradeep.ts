import { Component, ChangeDetectorRef, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { pradeepService } from './pradeep-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-pradeep',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pradeep.html',
  styleUrls: ['./pradeep.css']
})
export class PradeepComponent {

  // controls if the modal is open or not
  showModal = false;
  modalTitle = '';
  method = '';
  paramType = '';
  endpointKey = '';

  // filter inputs inside the modal form
  titleInput = '';
  storeIdInput = '';
  discountStoreFilter = '';
  discountTypeFilter = '';
  discountMinValue: number | null = null;
  discountMaxValue: number | null = null;
  discountMinLowQty: number | null = null;
  discountMaxHighQty: number | null = null;

  // table data and status flags
  tableData: any[] = [];
  errorMessage = '';
  successMessage = '';
  isLoading = false;
  isDiscountEditMode = false;
  isRoyaltyEditMode = false;

  // holds all fetched royalty slabs so user can pick which one to edit
  royaltySlabs: any[] = [];

  // form for creating or updating a discount
  updateDiscountForm = {
    discountType: '',
    storId: '',
    lowqty: null as number | null,
    highqty: null as number | null,
    discount: null as number | null
  };

  // form for creating or updating a royalty
  updateRoyaltyForm = {
    royaltyId: null as number | null,
    titleId: '',
    lorange: null as number | null,
    hirange: null as number | null,
    royalty: null as number | null
  };

  // keeps key order stable in *ngFor, angular sorts them alphabetically otherwise
  originalOrder = (): number => 0;

  // returns tailwind classes per cell based on what type of data the column holds
  getCellClass(key: unknown, value: any): Record<string, boolean> {
    const k = String(key ?? '').toLowerCase();
    const isEmpty = value === null || value === undefined || value === '';

    const isId = k.endsWith('id') || k.includes('id') || k.includes('type') || k.includes('title');
    const isMoney = k.includes('price') || k.includes('revenue') || k.includes('advance') || k.includes('discount');
    const isPercent = k.includes('royalty') || k.includes('percent') || k.includes('%');
    const isQty = k.includes('qty') || k.includes('quantity') || k.includes('low') || k.includes('high') || k.includes('range');
    const isDate = k.includes('date');
    const isNumber = typeof value === 'number' || (!isEmpty && !Number.isNaN(Number(value)));

    return {
      'font-mono': isId && !isEmpty,
      'text-blue-400': isId && !isEmpty,
      'text-slate-500': isEmpty,
      'text-emerald-400 font-semibold': (isMoney || isPercent || isQty) && isNumber && !isEmpty,
      'text-slate-400': isDate && !isEmpty
    };
  }

  constructor(
    private service: pradeepService,
    private cdr: ChangeDetectorRef,
    private zone: NgZone,
    private authService: AuthService,
    private router: Router
  ) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  // resets everything and opens the modal for the given endpoint
  openModal(method: string, title: string, paramType: string, endpoint: string) {
    this.method = method;
    this.modalTitle = title;
    this.paramType = paramType;
    this.endpointKey = endpoint;
    this.titleInput = '';
    this.storeIdInput = '';
    this.discountStoreFilter = '';
    this.discountTypeFilter = '';
    this.discountMinValue = null;
    this.discountMaxValue = null;
    this.discountMinLowQty = null;
    this.discountMaxHighQty = null;
    this.tableData = [];
    this.errorMessage = '';
    this.successMessage = '';
    this.isLoading = false;
    this.isDiscountEditMode = false;
    this.isRoyaltyEditMode = false;
    this.royaltySlabs = [];
    this.updateDiscountForm = {
      discountType: '',
      storId: '',
      lowqty: null,
      highqty: null,
      discount: null
    };
    this.updateRoyaltyForm = {
      royaltyId: null,
      titleId: '',
      lorange: null,
      hirange: null,
      royalty: null
    };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.isDiscountEditMode = false;
    this.isRoyaltyEditMode = false;
  }

  // maps raw api data into the discount form, handles both camelCase and lowercase field names
  private loadDiscountIntoForm(discountData: any) {
    this.updateDiscountForm = {
      discountType: discountData?.discounttype ?? discountData?.discountType ?? '',
      storId: discountData?.storId ?? discountData?.storeId ?? '',
      lowqty: discountData?.lowqty ?? null,
      highqty: discountData?.highqty ?? null,
      discount: discountData?.discount ?? null
    };
  }

  // normalises the api response into a flat array so the table always has consistent data
  handleResponse(res: any) {
    this.zone.run(() => {
      console.log('api response', res);

      // unwrap envelope shapes like { content: [] } or { data: [] }, otherwise wrap single object
      let finalArray = res;
      if (res && !Array.isArray(res)) {
        if (res.content) finalArray = res.content;
        else if (res.data) finalArray = res.data;
        else finalArray = [res];
      }

      this.tableData = Array.isArray(finalArray) ? finalArray : [];
      this.isLoading = false;
      this.errorMessage = '';
      // only show success banner if there are no rows to display
      this.successMessage = this.tableData.length === 0 ? 'Operation completed successfully.' : '';
      this.cdr.detectChanges();
    });
  }

  handleError(err: any) {
    this.zone.run(() => {
      console.error('api error', err);
      this.errorMessage = this.getFriendlyErrorMessage(err);
      this.tableData = [];
      this.isLoading = false;
      this.cdr.detectChanges();
    });
  }

  // converts raw HTTP errors into user-friendly messages based on status code and current endpoint
  private getFriendlyErrorMessage(err: any): string {
    // if the backend sent a meaningful message in the response body, prefer that
    const backendMsg = err?.error?.message;
    if (backendMsg && !backendMsg.includes('localhost') && !backendMsg.includes('127.0.0.1')) {
      return backendMsg;
    }

    const status = err?.status;

    // build a context hint based on which endpoint was called
    let contextHint = '';
    switch (this.endpointKey) {
      case 'getDiscountsByStore':
        contextHint = 'The Store ID you entered does not exist.';
        break;
      case 'getRoyaltyByTitle':
        contextHint = 'The Title ID you entered does not exist.';
        break;
      case 'getAllDiscounts':
        contextHint = 'No discounts matched your filters.';
        break;
      case 'createDiscount':
        contextHint = 'Could not create the discount. Please check your input values.';
        break;
      case 'updateDiscount':
        contextHint = 'Could not update the discount. The discount type may not exist.';
        break;
      case 'createRoyalty':
        contextHint = 'Could not create the royalty. Please check your input values.';
        break;
      case 'updateRoyalty':
        contextHint = 'Could not update the royalty. The royalty ID may not exist.';
        break;
      case 'getAuthorRoyalties':
        contextHint = 'Could not load the author royalties report.';
        break;
      default:
        contextHint = 'Please verify your input and try again.';
    }

    switch (status) {
      case 0:
        return 'Unable to reach the server. Please make sure the backend is running.';
      case 400:
        return `Bad Request — ${contextHint}`;
      case 404:
        return `Not Found — ${contextHint}`;
      case 409:
        return `Conflict — A record with the same key already exists.`;
      case 422:
        return `Invalid Data — ${contextHint}`;
      case 500:
        return `Server Error — Something went wrong on the server. Please try again later.`;
      case 503:
        return `Service Unavailable — The server is temporarily down. Please try again later.`;
      default:
        return contextHint || 'An unexpected error occurred. Please try again.';
    }
  }

  // main dispatcher, figures out which service call to make based on endpointKey
  execute() {
    console.log('executing', this.endpointKey);

    this.tableData = [];
    this.errorMessage = '';
    this.successMessage = '';
    this.isLoading = true;

    let request$;

    switch (this.endpointKey) {

      case 'getAllDiscounts':
        // all filters are optional here
        request$ = this.service.getAllDiscounts({
          storeId: this.discountStoreFilter || undefined,
          type: this.discountTypeFilter || undefined,
          minLowQty: this.discountMinLowQty,
          maxHighQty: this.discountMaxHighQty,
          minDiscount: this.discountMinValue,
          maxDiscount: this.discountMaxValue
        });
        break;

      case 'createDiscount': {
        // discountType is the only required field
        if (!this.updateDiscountForm.discountType) {
          this.errorMessage = 'Discount Type is required';
          this.isLoading = false;
          return;
        }
        const body = {
          discounttype: this.updateDiscountForm.discountType,
          storId: this.updateDiscountForm.storId,
          lowqty: this.updateDiscountForm.lowqty,
          highqty: this.updateDiscountForm.highqty,
          discount: this.updateDiscountForm.discount
        };
        request$ = this.service.createDiscount(body);
        break;
      }

      case 'getDiscountsByStore':
        if (!this.storeIdInput) {
          this.errorMessage = 'Store ID is required';
          this.isLoading = false;
          return;
        }
        request$ = this.service.getDiscountsByStore(this.storeIdInput);
        break;

      case 'getRoyaltyByTitle':
        if (!this.titleInput) {
          this.errorMessage = 'Title is required';
          this.isLoading = false;
          return;
        }
        request$ = this.service.getRoyaltyByTitle(this.titleInput);
        break;

      case 'getAuthorRoyalties':
        // no params needed for the full report
        request$ = this.service.getAuthorRoyalties();
        break;

      default:
        this.errorMessage = 'Unknown endpoint';
        this.isLoading = false;
        return;
    }

    request$.subscribe({
      next: (res: any) => this.handleResponse(res),
      error: (err: any) => this.handleError(err)
    });
  }

  // two-phase update: first call fetches and pre-fills the form, second call saves it
  executeUpdateDiscount() {
    console.log('update discount, edit mode:', this.isDiscountEditMode);

    if (!this.updateDiscountForm.discountType) {
      this.errorMessage = 'Discount Type is required';
      return;
    }

    // phase 1: fetch the existing discount and load it into the form
    if (!this.isDiscountEditMode) {
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';
      this.tableData = [];

      this.service.getAllDiscounts({ type: this.updateDiscountForm.discountType }).subscribe({
        next: (res: any) => {
          const rows = Array.isArray(res) ? res : (res?.content || res?.data || []);
          const normalizedInput = this.updateDiscountForm.discountType.trim().toLowerCase();
          // try exact match on type name, fall back to first row if nothing matches
          const matchedDiscount = rows.find((item: any) =>
            String(item?.discounttype ?? item?.discountType ?? '').trim().toLowerCase() === normalizedInput
          ) || rows[0];

          if (!matchedDiscount) {
            this.errorMessage = `No discount found for type: ${this.updateDiscountForm.discountType}`;
            this.isLoading = false;
            this.cdr.detectChanges();
            return;
          }

          this.loadDiscountIntoForm(matchedDiscount);
          this.isDiscountEditMode = true;
          this.successMessage = 'Discount fetched. You can edit the fields now.';
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err: any) => this.handleError(err)
      });
      return;
    }

    // phase 2: send the edited form as a PUT
    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const body = {
      discounttype: this.updateDiscountForm.discountType,
      storId: this.updateDiscountForm.storId,
      lowqty: this.updateDiscountForm.lowqty,
      highqty: this.updateDiscountForm.highqty,
      discount: this.updateDiscountForm.discount
    };

    this.service.updateDiscount(this.updateDiscountForm.discountType, body).subscribe({
      next: (res: any) => {
        this.isDiscountEditMode = false; // reset so button goes back to its default label
        this.handleResponse(res);
      },
      error: (err: any) => this.handleError(err)
    });
  }

  // straightforward POST, titleId is the only required field
  executeCreateRoyalty() {
    console.log('creating royalty for:', this.updateRoyaltyForm.titleId);

    if (!this.updateRoyaltyForm.titleId) {
      this.errorMessage = 'Title ID is required';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const body = {
      titleId: this.updateRoyaltyForm.titleId,
      lorange: this.updateRoyaltyForm.lorange,
      hirange: this.updateRoyaltyForm.hirange,
      royalty: this.updateRoyaltyForm.royalty
    };

    this.service.createRoyalty(body).subscribe({
      next: (res: any) => this.handleResponse(res),
      error: (err: any) => this.handleError(err)
    });
  }

  // same two-phase pattern as discount update: fetch first, then PUT on second submit
  executeUpdateRoyalty() {
    console.log('update royalty, edit mode:', this.isRoyaltyEditMode);

    if (!this.isRoyaltyEditMode && !this.updateRoyaltyForm.titleId) {
      this.errorMessage = 'Title ID is required to fetch royalty details';
      return;
    }

    // phase 1: load ALL royalty slabs for this title so the user can pick one
    if (!this.isRoyaltyEditMode) {
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';
      this.tableData = [];
      this.royaltySlabs = [];

      this.service.getRoyaltyByTitle(this.updateRoyaltyForm.titleId).subscribe({
        next: (res: any) => {
          const rows = Array.isArray(res) ? res : (res?.content || res?.data || []);

          if (!rows || rows.length === 0) {
            this.errorMessage = `No royalty slab found for title: ${this.updateRoyaltyForm.titleId}`;
            this.isLoading = false;
            this.cdr.detectChanges();
            return;
          }

          // if only one slab, auto-select it
          if (rows.length === 1) {
            this.selectRoyaltyRow(rows[0]);
          } else {
            // multiple slabs: show them in the table, let user click to pick
            this.royaltySlabs = rows;
            this.tableData = rows;
            this.successMessage = `${rows.length} royalty slabs found. Click a row below to select which one to edit.`;
          }

          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err: any) => this.handleError(err)
      });
      return;
    }

    // phase 2: royaltyId should have been set in phase 1
    if (!this.updateRoyaltyForm.royaltyId) {
      this.errorMessage = 'Royalty ID is required';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const body = {
      titleId: this.updateRoyaltyForm.titleId,
      lorange: this.updateRoyaltyForm.lorange,
      hirange: this.updateRoyaltyForm.hirange,
      royalty: this.updateRoyaltyForm.royalty
    };

    this.service.updateRoyalty(this.updateRoyaltyForm.royaltyId, body).subscribe({
      next: (res: any) => {
        this.isRoyaltyEditMode = false; // reset back to default state
        this.handleResponse(res);
      },
      error: (err: any) => this.handleError(err)
    });
  }

  // called when user clicks a row in the royalty slabs table to select it for editing
  selectRoyaltyRow(row: any) {
    this.updateRoyaltyForm = {
      royaltyId: row?.royschedId ?? row?.royaltyId ?? null,
      titleId: row?.titleId ?? this.updateRoyaltyForm.titleId,
      lorange: row?.lorange ?? null,
      hirange: row?.hirange ?? null,
      royalty: row?.royalty ?? null
    };
    this.isRoyaltyEditMode = true;
    this.royaltySlabs = [];
    this.tableData = [];
    this.successMessage = 'Royalty slab selected. Edit the fields and click Update Royalty.';
    this.cdr.detectChanges();
  }
}