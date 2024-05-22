const { defineConfig } = require('cypress');

module.exports = defineConfig({
    e2e: {
        setupNodeEvents(on, config) {
            // implement node event listeners here
        },
        baseUrl: 'http://localhost:8090', // Replace with your base URL
        specPattern: 'cypress/e2e/**/*.cy.{js,jsx,ts,tsx}'
    }
});
