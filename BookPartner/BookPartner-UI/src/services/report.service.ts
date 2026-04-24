import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private baseUrl = 'http://localhost:8082/api/v1/reports';

  constructor(private http: HttpClient) {}

  getSalesTrend(from: string, to: string): Observable<any> {
    const params = new HttpParams().set('from', from).set('to', to);
    return this.http.get(`${this.baseUrl}/sales/date-range`, { params });
  }

  getTopAuthorsBySales(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/authors/top-selling`);
  }
}
