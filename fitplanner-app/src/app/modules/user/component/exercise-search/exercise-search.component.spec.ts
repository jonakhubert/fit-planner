import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExerciseSearchComponent } from './exercise-search.component';

describe('ExerciseSearchComponent', () => {
  let component: ExerciseSearchComponent;
  let fixture: ComponentFixture<ExerciseSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExerciseSearchComponent]
    });
    fixture = TestBed.createComponent(ExerciseSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
