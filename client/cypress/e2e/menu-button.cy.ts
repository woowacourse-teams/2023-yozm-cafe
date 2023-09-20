describe('CafeMenuBottomSheet', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should open the menu bottom sheet and display menus', () => {
    cy.contains('메뉴').click({ force: true });

    cy.get('[data-testid="menuBottomSheet"]').should('be.visible');

    cy.contains('대표 메뉴').should('be.visible');
    cy.get('[data-testid="menu"]').should('be.visible');
    cy.get('[data-testid="menuItem"]').should('have.length.greaterThan', 0);
  });
});
