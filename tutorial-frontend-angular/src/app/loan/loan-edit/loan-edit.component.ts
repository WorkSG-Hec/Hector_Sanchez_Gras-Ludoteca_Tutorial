import { Component, Inject, OnInit } from '@angular/core';
import { Loan } from '../model/Loan';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LoanService } from '../loan.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { Game } from '../../game/model/Game';
import { Client } from '../../client/model/Client';
import { GameService } from '../../game/game.service';
import { ClientService } from '../../client/client.service';

@Component({
  selector: 'app-loan-edit',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatNativeDateModule,
    MatDatepickerModule,
  ],
  templateUrl: './loan-edit.component.html',
  styleUrl: './loan-edit.component.scss',
})
export class LoanEditComponent implements OnInit {
  loan: Loan;

  games: Game[];
  clients: Client[];

  constructor(
    private gameService: GameService,
    private clientService: ClientService,

    public dialogRef: MatDialogRef<LoanEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private loanService: LoanService,
  ) { }

  ngOnInit(): void {
    this.gameService.getGames().subscribe((games) => (this.games = games));
    this.clientService.getClients().subscribe((clients) => (this.clients = clients));

    this.loan = this.data.loan ? Object.assign({}, this.data.loan): new Loan();
  }

  onSave() {
    this.loanService.saveLoan(this.loan).subscribe(() => {
      this.dialogRef.close();
    });
  }

  onClose() {
    this.dialogRef.close();
  }
}