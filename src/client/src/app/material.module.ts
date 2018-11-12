import {NgModule} from '@angular/core';
import {
  MatButtonModule, MatButtonToggleModule, MatCheckboxModule, MatDatepickerModule, MatIcon, MatIconModule,
  MatNativeDateModule, MatTableModule,
  MatToolbarModule
} from '@angular/material';
import {MatCardModule} from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSliderModule} from '@angular/material/slider';
import {MatListModule} from '@angular/material/list';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatSnackBarModule} from '@angular/material/snack-bar';

@NgModule({
  imports: [
    MatButtonModule,
    MatToolbarModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSliderModule,
    MatListModule,
    MatProgressSpinnerModule,
    MatButtonToggleModule,
    MatIconModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    ReactiveFormsModule,
    FormsModule,
    MatSnackBarModule
  ],
  exports: [
    MatButtonModule,
    MatToolbarModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSliderModule,
    MatListModule,
    MatProgressSpinnerModule,
    MatButtonToggleModule,
    MatIconModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    ReactiveFormsModule,
    FormsModule,
    MatSnackBarModule
  ],
})
export class MaterialModule {
}
