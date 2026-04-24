import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SalesService {
  private baseUrl = 'http://localhost:8082/api/v1/sales';

  constructor(private http: HttpClient) {}

  createSale(dto: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, dto);
  }

  getAllSales(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  getSalesByStore(storeId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/store/${storeId}`);
  }

  getSalesByTitle(titleId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/title/${titleId}`);
  }

  getSalesByDateRange(from: string, to: string): Observable<any[]> {
    const params = new HttpParams().set('from', from).set('to', to);
    return this.http.get<any[]>(`${this.baseUrl}/date-range`, { params });
  }
}