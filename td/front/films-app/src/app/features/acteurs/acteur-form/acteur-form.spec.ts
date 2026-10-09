import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActeurForm } from './acteur-form';

describe('ActeurForm', () => {
  let component: ActeurForm;
  let fixture: ComponentFixture<ActeurForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActeurForm],
    }).compileComponents();

    fixture = TestBed.createComponent(ActeurForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
