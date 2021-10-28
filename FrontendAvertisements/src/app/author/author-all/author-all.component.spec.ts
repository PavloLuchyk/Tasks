import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorAllComponent } from './author-all.component';

describe('AuthorAllComponent', () => {
  let component: AuthorAllComponent;
  let fixture: ComponentFixture<AuthorAllComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorAllComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
