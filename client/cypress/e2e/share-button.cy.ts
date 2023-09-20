describe('ShareButton', () => {
  beforeEach(() => {
    cy.visit('/');
    cy.window().then((window) => {
      cy.stub(window.navigator.clipboard, 'writeText').resolves('https://yozm.cafe/cafes/1');
    });
  });

  it('공유하기 버튼을 클릭하면 해당 카페 URL이 복사되고, alert창을 띄운다.', () => {
    cy.contains('공유').click({ force: true });

    cy.on('window:alert', (alertText) => {
      expect(alertText).to.equal('URL이 복사되었습니다!');
    });
  });
});
