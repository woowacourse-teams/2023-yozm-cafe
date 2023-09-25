/// <reference types="cypress" />

describe('CafeDetailBottomSheet test', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('더보기 버튼을 클릭하면 카페 이름, 주소, 영업시간, 상세설명을 볼 수 있다.', () => {
    cy.contains('더보기').click({ force: true });

    cy.get('[data-testid=cafe-name]').should('be.visible');
    cy.get('[data-testid=cafe-address]').should('be.visible');
    cy.get('[data-testid=cafe-openingHours]').should('be.visible');
    cy.get('[data-testid=cafe-description]').should('be.visible');
  });

  it('바텀 시트에서 X 버튼이 보이고, X 버튼을 클릭하면 시트가 닫힌다.', () => {
    cy.wait(2000);
    cy.contains('더보기').click({ force: true });

    cy.get('[data-testid=close-button]').should('be.visible');

    cy.get('[data-testid=close-button]').click();

    cy.get('Container').should('not.exist');
  });
});
