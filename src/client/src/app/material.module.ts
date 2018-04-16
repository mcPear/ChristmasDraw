import {NgModule} from '@angular/core';
import {
  MatButtonModule, MatButtonToggleModule, MatCheckboxModule, MatIcon, MatIconModule,
  MatToolbarModule
} from '@angular/material';
import {MatCardModule} from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSliderModule} from '@angular/material/slider';
import {MatListModule} from '@angular/material/list';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

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
    MatCheckboxModule
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
    MatCheckboxModule
  ],
})
export class MaterialModule {
}
