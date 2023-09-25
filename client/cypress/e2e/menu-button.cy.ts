describe('CafeMenuBottomSheet', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('메뉴 버튼을 클릭하면 대표메뉴, 메뉴 아이템이 보인다.', () => {
    cy.contains('메뉴').click({ force: true });

    cy.get('[data-testid="menuBottomSheet"]').should('be.visible');

    cy.contains('대표 메뉴').should('be.visible');
    cy.get('[data-testid="menu"]').should('be.visible');
    cy.get('[data-testid="menuItem"]').should('have.length.greaterThan', 0);
  });
  it('바텀 시트에서 X 버튼이 보이고, X 버튼을 클릭하면 시트가 닫힌다.', () => {
    cy.wait(2000);
    cy.contains('더보기').click({ force: true });

    cy.get('[data-testid=close-button]').should('be.visible');

    cy.get('[data-testid=close-button]').click();

    cy.get('Container').should('not.exist');
  });
});
