# Project Leaf 🍃
![image](https://github.com/krkarma777/Leaf/assets/149022496/541c8121-3102-46bd-bdb3-dd25936559b2)

Welcome to Project Leaf, where simplicity meets functionality. Our cutting-edge platform merges the robustness of Slack, Jira, and Notion, delivering an all-in-one solution for dynamic team collaboration and project management.

## Why Project Leaf?
- **All-in-One Platform**: Combining communication, task management, and documentation in a sleek, user-friendly interface.
- **Effortlessly Stylish**: Engage with a design that's both attractive and intuitive, supporting both light and dark themes.
- **Enhanced Productivity**: With integrated tools designed for efficiency, your team can achieve more in less time.
- **Secured and Reliable**: Trust in our commitment to your data's security and integrity with advanced encryption and backup systems.

## Key Features

- **Instant Messaging and Calls**: Seamless communication via text, voice, or video.
- **Task and Project Management**: From idea to execution, manage and track your projects with ease.
- **Real-Time Collaboration**: Edit documents together in real-time, with changes synced across all team members.
- **Expandable and Integrative**: Utilize our API for custom integrations, enhancing your workflow.

## Quick Start Guide

1. **Set Up Your Environment**:
   - Ensure Java 17 and Gradle are installed on your machine.
   ```bash
   java -version
   gradle -version
   ```

2. **Clone and Set Up**:
   - Get the code and set up dependencies.
   ```bash
   git clone https://github.com/yourusername/project-leaf.git
   cd project-leaf
   gradle build
   ```

3. **Configure the Database**:
   - Add the following properties to `src/main/resources/application-dev.properties` for local development:
   ```properties
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3306/leaf?serverTimezone=UTC
   spring.datasource.username=leaf
   spring.datasource.password={some password}
   ```

4. **Run the Application**:
   - Launch Project Leaf and start collaborating.
   ```bash
   gradle bootRun
   ```

## Running Tests

To ensure everything is working correctly, follow these steps to run the tests:

1. **Set Up Cypress**:
   - Make sure Cypress is installed. You can add it as a dev dependency if it's not already included.
   ```bash
   npm install cypress --save-dev
   ```

2. **Configure Cypress**:
   - Ensure you have the Cypress configuration in place. Your `cypress.config.js` should look like this:
   ```javascript
   const { defineConfig } = require('cypress');

   module.exports = defineConfig({
       e2e: {
           setupNodeEvents(on, config) {
               // implement node event listeners here
           },
           baseUrl: 'http://localhost:8090',
           specPattern: 'cypress/e2e/**/*.cy.{js,jsx,ts,tsx}'
       }
   });
   ```

3. **Run Cypress Tests**:
   - Open Cypress and run the tests.
   ```bash
   npx cypress open
   ```

## Dive Deeper

Check our [Wiki](https://github.com/krkarma777/Leaf/wiki) for detailed documentation, user guides, and more about our features!

## Join Our Community

Get involved! We're excited to welcome new contributors. Check out our [CONTRIBUTING.md](https://github.com/krkarma777/Leaf/blob/master/CONTRIBUTING.md) for how to start.

## License

Project Leaf is open-sourced under the MIT license. [Read here](https://github.com/krkarma777/Leaf/blob/master/LICENSE) for more details.

## Get Support

Encountering issues? Reach out to us at krkarma777@gmail.com
