import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FilmCommentaires } from './film-commentaires';

describe('FilmCommentaires', () => {
  let component: FilmCommentaires;
  let fixture: ComponentFixture<FilmCommentaires>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilmCommentaires],
    }).compileComponents();

    fixture = TestBed.createComponent(FilmCommentaires);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
