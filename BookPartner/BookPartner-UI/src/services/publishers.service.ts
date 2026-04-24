import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PublishersService {
  private baseUrl = 'http://localhost:8082/api/v1';

  constructor(private http: HttpClient) {}

  getAllPublishers() {
    return this.http.get(`${this.baseUrl}/publishers`);
  }

  getPublisherById(id: string) {
    return this.http.get(`${this.baseUrl}/publishers/${id}`);
  }

  createPublisher(data: any) {
    return this.http.post(`${this.baseUrl}/publishers`, data);
  }

  updatePublisher(id: string, data: any) {
    return this.http.put(`${this.baseUrl}/publishers/${id}`, data);
  }

  deletePublisher(id: string) {
    return this.http.delete(`${this.baseUrl}/publishers/${id}`);
  }

  getTitlesByPublisher(id: string) {
    return this.http.get(`${this.baseUrl}/publishers/${id}/titles`);
  }

  getEmployeesByPublisher(id: string) {
    return this.http.get(`${this.baseUrl}/publishers/${id}/employees`);
  }

  getSalesReport() {
    return this.http.get(`${this.baseUrl}/reports/sales/by-publisher`);
  }
}
