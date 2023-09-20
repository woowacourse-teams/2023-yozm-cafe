/// <reference types="cypress" />
describe('LikeButton', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('좋아요 버튼을 누르면 좋아요 버튼이 활성화되고, 좋아요 카운트가 1 증가한다.', () => {
    cy.get('[data-testid="likeButton"]').click({ multiple: true, force: true });

    cy.get('[data-testid="likeButton"]').should('have.css', 'color', 'rgb(255, 255, 255)');
    cy.get('[aria-live="assertive"]').should('contain.text', '좋아요가 추가되었습니다.');
  });

  it('좋아요한 버튼을 다시 누르면 좋아요 버튼이 비활성화되고, 좋아요 카운트가 1 감소한다', () => {
    cy.get('[data-testid="likeButton"]').click({ multiple: true, force: true });

    cy.get('[data-testid="likeButton"]').should('have.css', 'color', 'rgb(255, 255, 255)');
    cy.get('[aria-live="assertive"]').should('contain.text', '좋아요 취소되었습니다.');
  });
});
