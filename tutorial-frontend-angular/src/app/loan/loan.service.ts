import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { Loan } from './model/Loan';
import { LoanPage } from './model/LoanPage';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor(private http: HttpClient) { }

  private baseUrl = 'http://localhost:8080/loan';

  getLoans(gameId: number, clientId: number, date: Date, pageable: Pageable): Observable<LoanPage> {
    return this.http.post<LoanPage>(this.composeFindUrl(gameId, clientId, date), { pageable: pageable });
  }

  saveLoan(loan: Loan): Observable<Loan> {
    const url = this.baseUrl;
    return this.http.put<Loan>(url, loan);
  }

  deleteLoan(idLoan: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idLoan}`);
  }

  private composeFindUrl(gameId?: number, clientId?: number, date?: Date): string {
    const params = new URLSearchParams();
    if (gameId) { params.set('idGame', gameId.toString()); }
    if (clientId) { params.set('idClient', clientId.toString()); }
    if (date) { params.set('filterDate', date.toDateString()); }

    const queryString = params.toString();
    return queryString ? `${this.baseUrl}?${queryString}` : this.baseUrl;
  }
}
