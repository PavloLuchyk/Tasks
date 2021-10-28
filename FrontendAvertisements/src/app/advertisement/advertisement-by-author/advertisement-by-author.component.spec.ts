import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementByAuthorComponent } from './advertisement-by-author.component';

describe('AdvertisementByAuthorComponent', () => {
  let component: AdvertisementByAuthorComponent;
  let fixture: ComponentFixture<AdvertisementByAuthorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdvertisementByAuthorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvertisementByAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
