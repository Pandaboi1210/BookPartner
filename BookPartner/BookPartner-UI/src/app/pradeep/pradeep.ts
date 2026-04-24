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

  showModal     = false;
  modalTitle    = '';
  method        = '';
  paramType     = '';
  endpointKey   = '';

  titleInput    = '';
  storeIdInput  = '';
  discountStoreFilter = '';
  discountTypeFilter = '';
  discountMinValue: number | null = null;
  discountMaxValue: number | null = null;
  discountMinLowQty: number | null = null;
  discountMaxHighQty: number | null = null;

  tableData: any[]   = [];
  errorMessage       = '';
  successMessage     = '';
  isLoading          = false;
  isDiscountEditMode = false;
  isRoyaltyEditMode  = false;

  /* Discount form */
  updateDiscountForm = {
    discountType: '',
    storId:       '',
    lowqty:       null as number | null,
    highqty:      null as number | null,
    discount:     null as number | null
  };

  /* Royalty form */
  updateRoyaltyForm = {
    royaltyId: null as number | null,
    titleId:   '',
    lorange:   null as number | null,
    hirange:   null as number | null,
    royalty:   null as number | null
  };

  originalOrder = (): number => 0;

  getCellClass(key: unknown, value: any): Record<string, boolean> {
    const k = String(key ?? '').toLowerCase();
    const isEmpty = value === null || value === undefined || value === '';

    const isId =
      k.endsWith('id') ||
      k.includes('id') ||
      k.includes('type') ||
      k.includes('title');

    const isMoney =
      k.includes('price') ||
      k.includes('revenue') ||
      k.includes('advance') ||
      k.includes('discount');

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

  /* ─── OPEN MODAL ─── */
  openModal(method: string, title: string, paramType: string, endpoint: string) {
    this.method       = method;
    this.modalTitle   = title;
    this.paramType    = paramType;
    this.endpointKey  = endpoint;

    this.titleInput   = '';
    this.storeIdInput = '';
    this.discountStoreFilter = '';
    this.discountTypeFilter = '';
    this.discountMinValue = null;
    this.discountMaxValue = null;
    this.discountMinLowQty = null;
    this.discountMaxHighQty = null;
    this.tableData    = [];
    this.errorMessage = '';
    this.successMessage = '';
    this.isLoading    = false;
    this.isDiscountEditMode = false;
    this.isRoyaltyEditMode = false;

    this.updateDiscountForm = {
      discountType: '', storId: '', lowqty: null, highqty: null, discount: null
    };
    this.updateRoyaltyForm = {
      royaltyId: null, titleId: '', lorange: null, hirange: null, royalty: null
    };

    this.showModal = true;
  }

  /* ─── CLOSE MODAL ─── */
  closeModal() {
    this.showModal = false;
    this.isDiscountEditMode = false;
    this.isRoyaltyEditMode = false;
  }

  private loadDiscountIntoForm(discountData: any) {
    this.updateDiscountForm = {
      discountType: discountData?.discounttype ?? discountData?.discountType ?? '',
      storId: discountData?.storId ?? discountData?.storeId ?? '',
      lowqty: discountData?.lowqty ?? null,
      highqty: discountData?.highqty ?? null,
      discount: discountData?.discount ?? null
    };
  }

  /* ─── HANDLE SUCCESS RESPONSE ─── */
    handleResponse(res: any) {
    this.zone.run(() => {
      console.log('HANDLE RESPONSE →', res);
 
      let finalArray = res;
      if (res && !Array.isArray(res)) {
        if      (res.content) finalArray = res.content;
        else if (res.data)    finalArray = res.data;
        else                  finalArray = [res];
      }
 
      this.tableData      = Array.isArray(finalArray) ? finalArray : [];
      this.isLoading      = false;
      this.errorMessage   = '';
      this.successMessage = this.tableData.length === 0 ? 'Operation completed successfully.' : '';
 
      this.cdr.detectChanges();
    });
  }
  /* ─── HANDLE ERROR ─── */
  handleError(err: any) {
    this.zone.run(() => {
      console.error('ERROR →', err);
      this.errorMessage = err?.error?.message || err?.message || 'Request failed.';
      this.tableData    = [];
      this.isLoading    = false;
      this.cdr.detectChanges();
    });
  }

  /* ─── EXECUTE (GET endpoints + createDiscount via form) ─── */
  execute() {
    console.log('EXECUTE →', this.endpointKey);

    this.tableData      = [];
    this.errorMessage   = '';
    this.successMessage = '';
    this.isLoading      = true;

    let request$;

    switch (this.endpointKey) {

      /* ── GET ALL DISCOUNTS ── */
      case 'getAllDiscounts':
        request$ = this.service.getAllDiscounts({
          storeId: this.discountStoreFilter || undefined,
          type: this.discountTypeFilter || undefined,
          minLowQty: this.discountMinLowQty,
          maxHighQty: this.discountMaxHighQty,
          minDiscount: this.discountMinValue,
          maxDiscount: this.discountMaxValue
        });
        break;

      /* ── CREATE DISCOUNT (form-based) ── */
      case 'createDiscount': {
        if (!this.updateDiscountForm.discountType) {
          this.errorMessage = 'Discount Type is required';
          this.isLoading = false;
          return;
        }
        const body = {
          discounttype: this.updateDiscountForm.discountType,
          storId:       this.updateDiscountForm.storId,
          lowqty:       this.updateDiscountForm.lowqty,
          highqty:      this.updateDiscountForm.highqty,
          discount:     this.updateDiscountForm.discount
        };
        request$ = this.service.createDiscount(body);
        break;
      }

      /* ── GET DISCOUNTS BY STORE ── */
      case 'getDiscountsByStore':
        if (!this.storeIdInput) {
          this.errorMessage = 'Store ID is required';
          this.isLoading = false;
          return;
        }
        request$ = this.service.getDiscountsByStore(this.storeIdInput);
        break;

      /* ── GET ROYALTY BY TITLE ── */
      case 'getRoyaltyByTitle':
        if (!this.titleInput) {
          this.errorMessage = 'Title is required';
          this.isLoading = false;
          return;
        }
        request$ = this.service.getRoyaltyByTitle(this.titleInput);
        break;

      /* ── GET AUTHOR ROYALTIES REPORT ── */
      case 'getAuthorRoyalties':
        request$ = this.service.getAuthorRoyalties();
        break;

      default:
        this.errorMessage = 'Unknown endpoint';
        this.isLoading = false;
        return;
    }

    request$.subscribe({
      next:  (res: any) => this.handleResponse(res),
      error: (err: any) => this.handleError(err)
    });
  }

  /* ─── UPDATE DISCOUNT ─── */
  executeUpdateDiscount() {
    console.log('EXECUTE UPDATE DISCOUNT');

    if (!this.updateDiscountForm.discountType) {
      this.errorMessage = 'Discount Type is required';
      return;
    }

    if (!this.isDiscountEditMode) {
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';
      this.tableData = [];

      this.service.getAllDiscounts({ type: this.updateDiscountForm.discountType })
        .subscribe({
          next: (res: any) => {
            const rows = Array.isArray(res) ? res : (res?.content || res?.data || []);
            const normalizedInputType = this.updateDiscountForm.discountType.trim().toLowerCase();
            const matchedDiscount = rows.find((item: any) =>
              String(item?.discounttype ?? item?.discountType ?? '').trim().toLowerCase() === normalizedInputType
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

    this.isLoading      = true;
    this.errorMessage   = '';
    this.successMessage = '';

    const body = {
      discounttype: this.updateDiscountForm.discountType,
      storId:       this.updateDiscountForm.storId,
      lowqty:       this.updateDiscountForm.lowqty,
      highqty:      this.updateDiscountForm.highqty,
      discount:     this.updateDiscountForm.discount
    };

    this.service.updateDiscount(this.updateDiscountForm.discountType, body)
      .subscribe({
        next:  (res: any) => {
          this.isDiscountEditMode = false;
          this.handleResponse(res);
        },
        error: (err: any) => this.handleError(err)
      });
  }

  /* ─── CREATE ROYALTY ─── */
  executeCreateRoyalty() {
    console.log('EXECUTE CREATE ROYALTY');

    if (!this.updateRoyaltyForm.titleId) {
      this.errorMessage = 'Title ID is required';
      return;
    }

    this.isLoading      = true;
    this.errorMessage   = '';
    this.successMessage = '';

    const body = {
      titleId: this.updateRoyaltyForm.titleId,
      lorange: this.updateRoyaltyForm.lorange,
      hirange: this.updateRoyaltyForm.hirange,
      royalty: this.updateRoyaltyForm.royalty
    };

    this.service.createRoyalty(body)
      .subscribe({
        next:  (res: any) => this.handleResponse(res),
        error: (err: any) => this.handleError(err)
      });
  }

  /* ─── UPDATE ROYALTY ─── */
  executeUpdateRoyalty() {
    console.log('EXECUTE UPDATE ROYALTY');

    if (!this.isRoyaltyEditMode && !this.updateRoyaltyForm.titleId) {
      this.errorMessage = 'Title ID is required to fetch royalty details';
      return;
    }

    if (!this.isRoyaltyEditMode) {
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';
      this.tableData = [];

      this.service.getRoyaltyByTitle(this.updateRoyaltyForm.titleId)
        .subscribe({
          next: (res: any) => {
            const rows = Array.isArray(res) ? res : (res?.content || res?.data || []);
            const matchedRoyalty = rows[0];

            if (!matchedRoyalty) {
              this.errorMessage = `No royalty slab found for title: ${this.updateRoyaltyForm.titleId}`;
              this.isLoading = false;
              this.cdr.detectChanges();
              return;
            }

            this.updateRoyaltyForm = {
              royaltyId: matchedRoyalty?.royschedId ?? matchedRoyalty?.royaltyId ?? null,
              titleId: matchedRoyalty?.titleId ?? this.updateRoyaltyForm.titleId,
              lorange: matchedRoyalty?.lorange ?? null,
              hirange: matchedRoyalty?.hirange ?? null,
              royalty: matchedRoyalty?.royalty ?? null
            };

            this.isRoyaltyEditMode = true;
            this.successMessage = 'Royalty fetched. You can edit the fields now.';
            this.isLoading = false;
            this.cdr.detectChanges();
          },
          error: (err: any) => this.handleError(err)
        });
      return;
    }

    if (!this.updateRoyaltyForm.royaltyId) {
      this.errorMessage = 'Royalty ID is required';
      return;
    }

    this.isLoading      = true;
    this.errorMessage   = '';
    this.successMessage = '';

    const body = {
      titleId: this.updateRoyaltyForm.titleId,
      lorange: this.updateRoyaltyForm.lorange,
      hirange: this.updateRoyaltyForm.hirange,
      royalty: this.updateRoyaltyForm.royalty
    };

    this.service.updateRoyalty(this.updateRoyaltyForm.royaltyId, body)
      .subscribe({
        next:  (res: any) => {
          this.isRoyaltyEditMode = false;
          this.handleResponse(res);
        },
        error: (err: any) => this.handleError(err)
      });
  }
}