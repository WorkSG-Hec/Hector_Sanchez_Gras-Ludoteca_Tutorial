import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthorService } from '@author/author.service';
import { Author } from '@author/model/Author';
import { NoWhitespaceValidatorDirective } from '@core/validators/whitespace.validator';

@Component({
  selector: 'app-author-edit',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    NoWhitespaceValidatorDirective,
  ],
  templateUrl: './author-edit.component.html',
  styleUrl: './author-edit.component.scss'
})
export class AuthorEditComponent implements OnInit {
  author: Author;

  constructor(
    public dialogRef: MatDialogRef<AuthorEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private authorService: AuthorService
  ) { }

  ngOnInit(): void {
    this.author = this.data.author ? Object.assign({}, this.data.author) : new Author();
  }

  onSave() {
    this.authorService.saveAuthor(this.author).subscribe(() => {
      this.dialogRef.close();
    });
  }

  onClose() {
    this.dialogRef.close();
  }
}
