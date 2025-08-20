import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Event} from "../../model/event";
import {EventService} from "../../services/event/event.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-add-event',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './add-event.component.html',
  styleUrl: './add-event.component.css'
})
export class AddEventComponent {
  addEventForm: FormGroup;
  private readonly eventService = inject(EventService);
  eventCreated = false;

  constructor(private fb: FormBuilder) {
    this.addEventForm = this.fb.group({
      stockCode: ['', [Validators.required]],
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      price: [0, [Validators.required]]
    })
  }

  onSubmit(): void {
    if (this.addEventForm.valid) {
      const event: Event = {
        stockCode: this.addEventForm.get('stockCode')?.value,
        name: this.addEventForm.get('name')?.value,
        description: this.addEventForm.get('description')?.value,
        price: this.addEventForm.get('price')?.value
      }
      this.eventService.createEvent(event).subscribe(event => {
        this.eventCreated = true;
        this.addEventForm.reset();
      })
    } else {
      console.log('Form is not valid');
    }
  }

  get stockCode() {
    return this.addEventForm.get('stockCode');
  }

  get name() {
    return this.addEventForm.get('name');
  }

  get description() {
    return this.addEventForm.get('description');
  }

  get price() {
    return this.addEventForm.get('price');
  }
}
