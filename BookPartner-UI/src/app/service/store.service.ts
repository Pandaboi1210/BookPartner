import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StoreService {
  private baseUrl = 'http://localhost:8082/api/v1/stores';

  constructor(private http: HttpClient) {}

  createStore(dto: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, dto);
  }

  getAllStores(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  getStoreById(storeId: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/${storeId}`);
  }

  updateStore(storeId: string, dto: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${storeId}`, dto);
  }

  getSalesByStore(storeId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${storeId}/sales`);
  }

  getDiscountsByStore(storeId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${storeId}/discounts`);
  }
}