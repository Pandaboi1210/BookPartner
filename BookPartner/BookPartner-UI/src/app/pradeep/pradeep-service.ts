import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class pradeepService {

  private baseUrl =
    'http://localhost:8082/api/v1';

  constructor(private http: HttpClient) {}


  getAllDiscounts(filters?: {
    storeId?: string;
    type?: string;
    minLowQty?: number | null;
    maxHighQty?: number | null;
    minDiscount?: number | null;
    maxDiscount?: number | null;
  }): Observable<any> {

    console.log("SERVICE: GET ALL DISCOUNTS");

    let params = new HttpParams();
    if (filters?.storeId) params = params.set('storeId', filters.storeId);
    if (filters?.type) params = params.set('type', filters.type);
    if (filters?.minLowQty !== null && filters?.minLowQty !== undefined) params = params.set('minLowQty', String(filters.minLowQty));
    if (filters?.maxHighQty !== null && filters?.maxHighQty !== undefined) params = params.set('maxHighQty', String(filters.maxHighQty));
    if (filters?.minDiscount !== null && filters?.minDiscount !== undefined) params = params.set('minDiscount', String(filters.minDiscount));
    if (filters?.maxDiscount !== null && filters?.maxDiscount !== undefined) params = params.set('maxDiscount', String(filters.maxDiscount));

    return this.http.get(`${this.baseUrl}/discounts`, { params });

  }

  createDiscount(body: any): Observable<any> {

    return this.http.post(
      `${this.baseUrl}/discounts`,
      body
    );

  }

  updateDiscount(
    type: string,
    body: any
  ): Observable<any> {

    return this.http.put(
      `${this.baseUrl}/discounts/${type}`,
      body
    );

  }

  getDiscountsByStore(storeId: string): Observable<any> {

    console.log("SERVICE: GET DISCOUNTS BY STORE", storeId);

    return this.http.get(
      `${this.baseUrl}/discounts/store/${storeId}`
    );

  }

  createRoyalty(body: any): Observable<any> {
    // POST to /royalties to create a royalty slab
    return this.http.post(
      `${this.baseUrl}/royalties`,
      body
    );
  }

  getRoyaltyByTitle(
    title: string
  ): Observable<any> {

    return this.http.get(
      `${this.baseUrl}/royalties/title/${title}`
    );

  }

  updateRoyalty(
    id: number,
    body: any
  ): Observable<any> {

    return this.http.put(
      `${this.baseUrl}/royalties/${id}`,
      body
    );

  }

  getAuthorRoyalties(): Observable<any> {

    return this.http.get(
      `${this.baseUrl}/reports/authors/royalties`
    );

  }

}
