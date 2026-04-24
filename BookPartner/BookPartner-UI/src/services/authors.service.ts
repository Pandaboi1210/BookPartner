import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthorsService {
  private baseUrl = 'http://localhost:8082/api/v1/authors';

  constructor(private http: HttpClient) {}

  getAllAuthors(city?: string, state?: string, contract?: number) {
    const params: Record<string, string | number> = {};
    if (city) params['city'] = city;
    if (state) params['state'] = state;
    if (contract !== undefined && contract !== null) params['contract'] = contract;

    return this.http.get<any>(this.baseUrl, { params });
  }

  getAuthorById(id: string) {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createAuthor(data: any) {
    return this.http.post(this.baseUrl, data);
  }

  updateAuthor(id: string, data: any) {
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }

  deleteAuthor(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  getTitlesByAuthor(id: string) {
    return this.http.get(`${this.baseUrl}/${id}/titles`);
  }
}
