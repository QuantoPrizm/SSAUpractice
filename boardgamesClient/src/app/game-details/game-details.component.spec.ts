import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameD } from './game-details';

describe('GameD', () => {
  let component: GameD;
  let fixture: ComponentFixture<GameD>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GameD]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameD);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
