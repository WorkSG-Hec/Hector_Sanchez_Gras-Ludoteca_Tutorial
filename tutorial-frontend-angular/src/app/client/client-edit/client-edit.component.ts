import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClientService } from '../client.service';
import { Client } from '../model/Client';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-client-edit',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './client-edit.component.html',
  styleUrl: './client-edit.component.scss'
})
export class ClientEditComponent implements OnInit {
  client: Client;
  
  constructor(
    public dialogRef: MatDialogRef<ClientEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {client: Client},
    private clientService: ClientService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.client = this.data.client ? Object.assign({}, this.data.client) : new Client();
  }

  onSave() {
    this.clientService.saveClient(this.client).subscribe({
      next: () => {
        this.dialogRef.close();
      },
      error: (error) => {
        if (error.status === 400 && error.error) {
          this.snackBar.open(error.error?.message, 'Cerrar', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      }
    });
  }

  onClose() {
    this.dialogRef.close();
  }
}
