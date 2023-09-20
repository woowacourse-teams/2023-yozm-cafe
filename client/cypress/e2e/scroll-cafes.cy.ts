/// <reference types="cypress" />

describe('홈 화면에서 카페 이미지를 위로 스크롤하면 다음 카페 이미지가 나온다.', () => {
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

describe('카페 이미지를 좌우로 스크롤하여 다음, 이전 카페 이미지를 불러온다.', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should scroll left and right in the carousel', () => {
    cy.get('[data-testid="cafe-card"]').should('be.visible');

    let currentImageIndex = 0;

    const scrollRight = () => {
      currentImageIndex++;

      cy.get('[data-testid="cafe-card"]')
        .get('[data-testid="carousel-image"]')
        .should('have.attr', 'src', `/images/cafe-image-${currentImageIndex}.png`);
      cy.get('[data-testid="carousel-dots"]').get('[data-testid="dot"]').eq(currentImageIndex);
    };

    const scrollLeft = () => {
      currentImageIndex--;
      const expectedImageSrc = `/images/cafe-image-${currentImageIndex}.png`;

      cy.get('[data-testid="cafe-card"]')
        .find('[data-testid="carousel-image"]')
        .should('have.attr', 'src', expectedImageSrc);

      cy.get('[data-testid="carousel-dots"]')
        .find('[data-testid="dot"]')
        .eq(currentImageIndex)
        .should('have.class', 'active');

      cy.get('[data-testid="cafe-card"]').scrollTo('left');
    };

    scrollRight();
    scrollRight();
    scrollLeft();
    scrollLeft();
  });
});
