describe('Project Creation', () => {
    // Uncaught exception 핸들링 설정
    Cypress.on('uncaught:exception', (err, runnable) => {
        // returning false here prevents Cypress from failing the test
        return false;
    });

    beforeEach(() => {
        // 로그인 페이지로 이동
        cy.visit('/login');

        // 로그인 수행
        cy.get('input[name="email"]').type('test-account@gmail.com');
        cy.get('input[name="password"]').type('test123!!');
        cy.get('button[type="submit"]').click();

        // 로그인 후 프로젝트 생성 페이지로 이동
        cy.url().should('not.include', '/login');
        cy.visit('/project/create');
    });

    it('should create a project with valid inputs', () => {
        cy.get('#projectName').type('New Project');
        cy.get('#description').type('Description of the project');
        cy.get('#startDate').type('2024-05-23T10:00');
        cy.get('#endDate').type('2024-06-01T10:00');
        cy.get('#category').select('Software Development');
        cy.get('#highPriority').check();
        cy.get('#status').select('Planning');
        
        // 팀 멤버 추가를 위한 메서드 가정
        cy.intercept('GET', '/api/user/search-members*').as('getMembers');
        cy.get('#teamMemberSearch').type('test@test.com');
        cy.wait('@getMembers');

        cy.get('#searchResults').contains('test@test.com').click();
        
        cy.get('#createProjectForm').submit();
    });
});
