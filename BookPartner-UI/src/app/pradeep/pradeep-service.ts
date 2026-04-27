import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class pradeepService {

  private baseUrl = 'http://localhost:8082/api/v1';

  constructor(private http: HttpClient) {}

  // fetches all discounts, any of the filters are optional
  getAllDiscounts(filters?: {
    storeId?: string;
    type?: string;
    minLowQty?: number | null;
    maxHighQty?: number | null;
    minDiscount?: number | null;
    maxDiscount?: number | null;
  }): Observable<any> {
    console.log('get all discounts');

    let params = new HttpParams();
    if (filters?.storeId) params = params.set('storeId', filters.storeId);
    if (filters?.type) params = params.set('type', filters.type);
    if (filters?.minLowQty != null) params = params.set('minLowQty', String(filters.minLowQty));
    if (filters?.maxHighQty != null) params = params.set('maxHighQty', String(filters.maxHighQty));
    if (filters?.minDiscount != null) params = params.set('minDiscount', String(filters.minDiscount));
    if (filters?.maxDiscount != null) params = params.set('maxDiscount', String(filters.maxDiscount));

    return this.http.get(`${this.baseUrl}/discounts`, { params });
  }

  // creates a new discount record
  createDiscount(body: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/discounts`, body);
  }

  // updates an existing discount by type
  updateDiscount(type: string, body: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/discounts/${type}`, body);
  }

  // gets all discounts belonging to a specific store
  getDiscountsByStore(storeId: string): Observable<any> {
    console.log('get discounts by store', storeId);
    return this.http.get(`${this.baseUrl}/discounts/store/${storeId}`);
  }

  // creates a new royalty slab
  createRoyalty(body: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/royalties`, body);
  }

  // gets all royalty slabs for a given title
  getRoyaltyByTitle(title: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/royalties/title/${title}`);
  }

  // updates a royalty slab by its id
  updateRoyalty(id: number, body: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/royalties/${id}`, body);
  }

  // pulls the full author royalties report, no params needed
  getAuthorRoyalties(): Observable<any> {
    return this.http.get(`${this.baseUrl}/reports/authors/royalties`);
  }
}