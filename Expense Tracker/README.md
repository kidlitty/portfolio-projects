# Expense Tracker 💸

[![Java](https://img.shields.io/badge/Java-22-blue)](https://openjdk.org/projects/jdk/22/)
[![Maven](https://img.shields.io/badge/Maven-3.9.6-blue)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A robust command-line expense tracking application with persistent storage and monthly reporting.

## Features ✨
- 💾 JSON persistence using Jackson
- 📅 Monthly expense summaries
- 🔍 CRUD operations for expenses
- 💰 Currency formatting for ZAR
- 📊 Basic financial reporting
- 🚀 Maven build system

## Tech Stack 🛠️
- **Core**: Java 22
- **Serialization**: Jackson (2.15.2)
- **Date/Time**: Java Time API
- **Build**: Maven
- **CI/CD**: GitHub Actions

## Installation ⚙️
```bash
git clone https://github.com/yourusername/expense-tracker.git
cd expense-tracker
mvn clean package
java -jar target/expense-tracker-1.0-SNAPSHOT.jar
```

## Usage Examples 📖
```bash
# Add new expense
Enter your command: add
Enter the title of the expense: Groceries
Enter the amount of expense: 549.95

# View monthly summary
Enter your command: summary
Enter year (e.g 2024): 2024
Enter month (1-12): 3
```

## Design Highlights 🏗️
- **SOLID Principles**: Separated file handling (SRP)
- **Type Safety**: Strong validation for expense entries
- **Error Handling**: Comprehensive input validation
- **Performance**: Stream API for calculations
- **Localization**: ZAR currency formatting

