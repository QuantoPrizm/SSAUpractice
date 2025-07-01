import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameL } from './game-list';

describe('GameL', () => {
  let component: GameL;
  let fixture: ComponentFixture<GameL>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GameL]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameL);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
