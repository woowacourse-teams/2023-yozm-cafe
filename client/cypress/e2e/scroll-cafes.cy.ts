/// <reference types="cypress" />

context('Scroll Cafes', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('처음에 첫 번째 카페가 나타나야 한다.', () => {
    cy.contains('성수 카페 (1호점)').should('be.visible');
  });

  it('스크롤을 하면 두 번째 카페가 나타나야 한다.', () => {
    cy.get('main > ul').scrollTo(0, 400);
    cy.contains('성수 카페2 (1호점)').should('be.visible');
  });

  it('스크롤을 2번 하면 세 번째 카페가 나타나야 한다.', () => {
    cy.get('main > ul').scrollTo(0, 400 * 3);
    cy.contains('성수 카페3 (1호점)').should('be.visible');
  });
});
