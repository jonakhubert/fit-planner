import { Component, EventEmitter, Output } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';

@Component({
  selector: 'app-date-selector',
  templateUrl: './date-selector.component.html',
  styleUrls: ['./date-selector.component.scss']
})
export class DateSelectorComponent {
  selectedDate: Date = new Date();
  formattedDate: string = '';
  @Output() dateOutput = new EventEmitter<string>();

  ngOnInit(): void {
    const storedDate = localStorage.getItem('selectedDate');
    this.selectedDate = storedDate ? new Date(storedDate) : new Date();
    this.displayDate();
    this.emitDateOutput();
  }

  onDateSelected(event: MatDatepickerInputEvent<Date>): void {
    this.selectedDate = event.value ?? new Date();
    this.saveSelectedDate();
    this.displayDate();
    this.emitDateOutput();
  }

  goToDate(direction: 'previous' | 'next'): void {
    const days = direction === 'next' ? 1 : -1;
    this.selectedDate.setDate(this.selectedDate.getDate() + days);
    this.saveSelectedDate();
    this.displayDate();
    this.emitDateOutput();
  }

  formatDate(): string {
    const year = this.selectedDate.getFullYear();
    const month = ('0' + (this.selectedDate.getMonth() + 1)).slice(-2);
    const day = ('0' + this.selectedDate.getDate()).slice(-2);
  
    return `${year}-${month}-${day}`;
  }

  emitDateOutput(): void {
    this.dateOutput.emit(this.formatDate());
  }

  private displayDate(): void {
    const options: Intl.DateTimeFormatOptions = { 
      weekday: 'long', 
      month: 'long', 
      day: 'numeric', 
      year: 'numeric' 
    };

    this.formattedDate = this.selectedDate.toLocaleDateString('en-US', options);
  }

  private saveSelectedDate(): void {
    localStorage.setItem('selectedDate', this.selectedDate.toISOString());
  }
}
