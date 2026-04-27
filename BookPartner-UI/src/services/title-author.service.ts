import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TitleAuthorService {
  private readonly baseUrl = 'http://localhost:8082/api/v1';

  constructor(private http: HttpClient) {}

  createTitleAuthor(body: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/title-authors`, body);
  }

  deleteTitleAuthor(auId: string, titleId: string): Observable<any> {
    return this.http.delete(
      `${this.baseUrl}/title-authors/${encodeURIComponent(auId)}/${encodeURIComponent(titleId)}`
    );
  }
}

