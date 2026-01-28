# Contributing to Gold & Silver Investment Tracker

First off, thank you for considering contributing to Gold & Silver Investment Tracker! 🎉

## How Can I Contribute?

### Reporting Bugs 🐛

Before creating bug reports, please check existing issues to avoid duplicates. When you create a bug report, include as many details as possible:

**Bug Report Template:**
```markdown
**Describe the bug**
A clear description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Go to '...'
2. Click on '...'
3. See error

**Expected behavior**
What you expected to happen.

**Screenshots**
If applicable, add screenshots.

**Environment:**
 - OS: [e.g. Windows 11]
 - Browser: [e.g. Chrome 120]
 - Java Version: [e.g. 17]
 - Application Version: [e.g. 1.0.0]
```

### Suggesting Features 💡

Feature suggestions are welcome! Please provide:

1. **Use case**: Why this feature would be useful
2. **Description**: Detailed explanation of the feature
3. **Mockups/Examples**: If applicable, visual examples
4. **Implementation ideas**: Optional technical suggestions

### Pull Requests 🔀

**Before submitting a pull request:**

1. ✅ Check that your code follows the project's coding style
2. ✅ Write/update tests for your changes
3. ✅ Update documentation if needed
4. ✅ Ensure all tests pass
5. ✅ Add a clear commit message

**Pull Request Process:**

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Commit your changes (`git commit -m 'Add amazing feature'`)
5. Push to the branch (`git push origin feature/amazing-feature`)
6. Open a Pull Request

## Development Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

### Getting Started

```bash
# Clone your fork
git clone https://github.com/YOUR_USERNAME/gold-silver-tracker.git
cd gold-silver-tracker

# Add upstream remote
git remote add upstream https://github.com/ORIGINAL_OWNER/gold-silver-tracker.git

# Install dependencies
./mvnw clean install

# Run tests
./mvnw test

# Run application
./mvnw spring-boot:run
```

## Code Style Guidelines

### Java Code Style

**Naming Conventions:**
```java
// Classes: PascalCase
public class InvestmentService { }

// Methods: camelCase
public void calculateProfitLoss() { }

// Constants: UPPER_SNAKE_CASE
private static final String DEFAULT_CURRENCY = "INR";

// Variables: camelCase
private BigDecimal totalAmount;
```

**Formatting:**
- Indentation: 4 spaces (no tabs)
- Line length: Maximum 120 characters
- Braces: Always use braces, even for single-line if statements

**Example:**
```java
// ✅ Good
if (amount.compareTo(BigDecimal.ZERO) > 0) {
    processInvestment(amount);
}

// ❌ Bad
if (amount.compareTo(BigDecimal.ZERO) > 0)
    processInvestment(amount);
```

### Documentation

**JavaDoc for Public Methods:**
```java
/**
 * Calculates the profit or loss for a given investment.
 *
 * @param investment the investment to calculate profit/loss for
 * @return the profit (positive) or loss (negative) as BigDecimal
 * @throws IllegalArgumentException if investment is null
 */
public BigDecimal calculateProfitLoss(Investment investment) {
    // Implementation
}
```

**Comments:**
- Use comments to explain **why**, not **what**
- Keep comments up-to-date with code changes
- Remove commented-out code before committing

### Testing

**Write Tests:**
```java
@Test
void shouldCalculateProfitCorrectly() {
    // Given
    Investment investment = createTestInvestment();
    
    // When
    BigDecimal profit = service.calculateProfitLoss(investment);
    
    // Then
    assertEquals(expected, profit);
}
```

**Test Coverage:**
- Aim for 80%+ code coverage
- Test edge cases and error conditions
- Use meaningful test names

### Commit Messages

**Format:**
```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding/updating tests
- `chore`: Maintenance tasks

**Example:**
```
feat(investment): add support for platinum tracking

Added new metal type PLATINUM to the MetalType enum.
Updated service layer to handle platinum investments.
Added tests for platinum calculations.

Closes #123
```

## Project Structure

```
src/
├── main/
│   ├── java/com/investment/goldsilver/
│   │   ├── controller/     # HTTP Controllers
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── entity/        # JPA Entities
│   │   ├── repository/    # Data Repositories
│   │   └── service/       # Business Logic
│   └── resources/
│       ├── static/        # CSS, JS, Images
│       └── templates/     # Thymeleaf Templates
└── test/
    └── java/              # Unit & Integration Tests
```

## Areas for Contribution

### 🟢 Good First Issues
- Documentation improvements
- UI/UX enhancements
- Bug fixes with clear reproduction steps
- Adding unit tests

### 🟡 Intermediate
- New features (charts, export, etc.)
- Performance optimizations
- Database migration scripts
- API development

### 🔴 Advanced
- Multi-user support with authentication
- Real-time price updates
- Mobile app development
- Microservices architecture

## Questions?

Feel free to:
- Open an issue with the `question` label
- Join our discussion forum (if available)
- Contact the maintainers

## Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inclusive experience for everyone.

### Our Standards

**Positive behaviors:**
- Using welcoming and inclusive language
- Being respectful of differing viewpoints
- Gracefully accepting constructive criticism
- Focusing on what's best for the community

**Unacceptable behaviors:**
- Trolling, insulting/derogatory comments
- Public or private harassment
- Publishing others' private information
- Other conduct which could reasonably be considered inappropriate

## Recognition

Contributors will be recognized in:
- README.md Contributors section
- Release notes
- Project documentation

## Thank You! 🙏

Your contributions make this project better for everyone. We appreciate your time and effort!

---

**Happy Coding!** 💻✨
