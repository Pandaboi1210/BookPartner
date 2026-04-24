import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TitleService {
  private readonly baseUrl = 'http://localhost:8082/api/v1';

  constructor(private http: HttpClient) {}

  getAllTitles(
    type?: string,
    publisher?: string,
    minPrice?: number | null,
    maxPrice?: number | null
  ): Observable<any> {
    let params = new HttpParams();
    if (type) params = params.set('type', type);
    if (publisher) params = params.set('publisher', publisher);
    if (minPrice !== null && minPrice !== undefined) params = params.set('minPrice', String(minPrice));
    if (maxPrice !== null && maxPrice !== undefined) params = params.set('maxPrice', String(maxPrice));

    return this.http.get(`${this.baseUrl}/titles`, { params });
  }

  getTitleById(id: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/titles/${encodeURIComponent(id)}`);
  }

  createTitle(body: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/titles`, body);
  }

  updateTitle(id: string, body: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/titles/${encodeURIComponent(id)}`, body);
  }

  getAuthorsByTitle(titleId: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/titles/${encodeURIComponent(titleId)}/authors`);
  }

  getSalesByTitle(titleId: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/titles/${encodeURIComponent(titleId)}/sales`);
  }

  getRoyaltiesByTitle(titleId: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/titles/${encodeURIComponent(titleId)}/royalties`);
  }

  getTotalSalesByTitle(): Observable<any> {
    return this.http.get(`${this.baseUrl}/reports/sales/by-title`);
  }
}

