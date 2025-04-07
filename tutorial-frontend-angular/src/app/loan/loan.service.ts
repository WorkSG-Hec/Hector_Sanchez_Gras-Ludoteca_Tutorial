import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { Loan } from './model/Loan';
import { LoanPage } from './model/LoanPage';
import { LOAN_DATA } from './model/mock-loans';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor() { }

  getLoans(pageable: Pageable): Observable<LoanPage> {
    return of(LOAN_DATA);
  }

  saveLoan(loan: Loan): Observable<void> {
    return of(null);
  }

  deleteLoan(idLoan: number): Observable<void> {
    return of(null);
  }
}
