# 💎 Gold & Silver Investment Tracker

A comprehensive web application for tracking your precious metal investments with real-time profit/loss calculations, built with Spring Boot and modern web technologies.

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1+-blue.svg)
![H2 Database](https://img.shields.io/badge/H2-In--Memory-yellow.svg)

## 📖 Overview

Gold & Silver Investment Tracker is a full-stack web application designed to help you manage and track your precious metal investments. Whether you're investing in gold, silver, or both, this application provides detailed insights into your portfolio with accurate profit/loss calculations based on stored purchase prices.

### 🎯 Key Highlights

- **Investment Tracking**: Record gold and silver purchases with precise gram measurements (up to 5 decimal places)
- **Profit/Loss Calculation**: Real-time profit/loss calculations using stored purchase prices
- **Price History**: Maintain daily price records for reference and auto-fill
- **Dual Metal Support**: Separate tracking for gold and silver investments
- **Beautiful UI**: Modern, responsive design with glassmorphism effects and smooth animations
- **High Precision**: BigDecimal-based calculations ensuring accuracy to the rupee

---

## ✨ Features

### 📊 Dashboard
- **Portfolio Overview**: View total invested amount, current value, and overall profit/loss
- **Metal-Specific Stats**: Separate profit/loss tracking for gold and silver
- **Visual Indicators**: Green (▲) for profits, Red (▼) for losses
- **Percentage Tracking**: See profit/loss as both absolute values and percentages
- **Quick Actions**: Fast navigation to add investments or update prices

### 🟡 Gold & ⚪ Silver Pages
- **Investment History**: Complete list of all metal purchases
- **Detailed View**: Date, amount invested, grams purchased, and purchase price
- **Summary Cards**: Total grams and total invested for each metal
- **Daily Price History**: Track historical prices for reference
- **CRUD Operations**: Add, edit, and delete investment entries

### 💰 Investment Management
- **Add Investments**: Record purchases with date, amount, grams, and price
- **Auto-Fill Prices**: Current market prices auto-populate for convenience
- **Edit Capabilities**: Update investment details as needed
- **Delete Functionality**: Remove incorrect or outdated entries

### 💲 Price Management
- **Market Price Updates**: Set current gold and silver prices for reference
- **Auto-Fill Support**: New investments auto-populate with latest prices
- **Price History**: Optional daily price tracking for trend analysis

---

## 🛠️ Technology Stack

### Backend
- **Java 17**: Modern Java features and performance
- **Spring Boot 3.2.1**: Rapid application development framework
- **Spring Data JPA**: Database abstraction and ORM
- **H2 Database**: In-memory database (easily switchable to MySQL/PostgreSQL)
- **Lombok**: Reduce boilerplate code
- **Bean Validation**: Input validation and data integrity

### Frontend
- **Thymeleaf**: Server-side template engine
- **Vanilla CSS**: Custom styling with CSS variables
- **Google Fonts (Inter)**: Professional typography
- **Responsive Design**: Mobile-friendly layout
- **Modern UI/UX**: Glassmorphism, animations, and visual feedback

### Architecture
- **MVC Pattern**: Clean separation of concerns
- **Repository Pattern**: Data access abstraction
- **DTO Pattern**: Data transfer optimization
- **Service Layer**: Business logic encapsulation

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (or use included `mvnw` wrapper)
- **Git** (for cloning the repository)
- **Web Browser** (Chrome, Firefox, Edge, Safari)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/gold-silver-tracker.git
   cd gold-silver-tracker
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```
   *On Windows:*
   ```bash
   mvnw.cmd clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   *On Windows:*
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. **Access the application**
   
   Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

---

## 📚 Usage Guide

### Getting Started

1. **Update Market Prices** (Optional but Recommended)
   - Click "Update Prices" in the navigation
   - Enter current gold and silver prices per gram
   - These prices will auto-fill when adding investments

2. **Add Your First Investment**
   - Navigate to Gold or Silver page
   - Click "➕ Add New Investment"
   - Fill in the form:
     - **Date**: Date of purchase
     - **Amount Invested**: Total amount in ₹
     - **Grams Purchased**: Exact grams (check your receipt)
     - **Today's Price**: Price per gram at purchase (auto-filled from market prices)
   - Click "💰 Add Investment"

3. **View Dashboard**
   - See your complete portfolio overview
   - Check profit/loss for each metal separately
   - Monitor overall investment performance

### Understanding Calculations

#### Current Value Formula
```
Current Value = Total Grams × Price at Purchase
```

#### Profit/Loss Formula
```
Profit/Loss = Current Value - Total Invested
```

#### Percentage Formula
```
Percentage = (Profit/Loss ÷ Total Invested) × 100
```

**Important Notes:**
- ✅ Each investment stores its **own price at purchase**
- ✅ Profit/loss is calculated using **stored prices**, not current market prices
- ✅ This ensures accurate historical tracking
- ✅ No premature rounding - calculations use high-precision BigDecimal

---

## 📁 Project Structure

```
gold-silver-tracker/
│
├── src/
│   ├── main/
│   │   ├── java/com/investment/goldsilver/
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   └── InvestmentController.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── DashboardStats.java
│   │   │   │   └── InvestmentDto.java
│   │   │   ├── entity/              # JPA Entities
│   │   │   │   ├── DailyPrice.java
│   │   │   │   ├── Investment.java
│   │   │   │   ├── MetalPrice.java
│   │   │   │   └── MetalType.java
│   │   │   ├── repository/          # Data Repositories
│   │   │   │   ├── DailyPriceRepository.java
│   │   │   │   ├── InvestmentRepository.java
│   │   │   │   └── MetalPriceRepository.java
│   │   │   ├── service/             # Business Logic
│   │   │   │   └── InvestmentService.java
│   │   │   └── GoldSilverTrackerApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/css/
│   │       │   └── style.css        # Custom CSS
│   │       ├── templates/           # Thymeleaf Templates
│   │       │   ├── dashboard.html
│   │       │   ├── metal-investments.html
│   │       │   ├── add-investment.html
│   │       │   ├── edit-investment.html
│   │       │   └── prices.html
│   │       └── application.properties
│   │
│   └── test/                        # Unit Tests
│
├── pom.xml                          # Maven Dependencies
├── mvnw                             # Maven Wrapper (Unix)
├── mvnw.cmd                         # Maven Wrapper (Windows)
└── README.md
```

---

## 🗄️ Database Schema

### Investment Table
```sql
CREATE TABLE investments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    metal_type VARCHAR(10) NOT NULL,
    purchase_date DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    grams DECIMAL(10,5) NOT NULL,
    today_price_per_gram DECIMAL(10,2) NOT NULL
);
```

### Metal Price Table (Reference Prices)
```sql
CREATE TABLE metal_prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    metal_type VARCHAR(10) UNIQUE NOT NULL,
    price_per_gram DECIMAL(10,2) NOT NULL,
    updated_on DATE NOT NULL
);
```

### Daily Price Table (Optional Price History)
```sql
CREATE TABLE daily_prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    metal_type VARCHAR(10) NOT NULL,
    price_date DATE NOT NULL,
    price_per_gram DECIMAL(10,2) NOT NULL,
    UNIQUE(metal_type, price_date)
);
```

---

## 🔧 Configuration

### Application Properties

Default configuration (`application.properties`):

```properties
# Application Settings
spring.application.name=Gold Silver Investment Tracker
server.port=8080

# H2 Database (In-Memory)
spring.datasource.url=jdbc:h2:mem:goldsilverdb
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=org.h2.Driver

# JPA/Hibernate Settings
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

# H2 Console (Development Only)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Switching to MySQL/PostgreSQL

**For MySQL:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/goldsilverdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

**For PostgreSQL:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/goldsilverdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

---

## 🎨 UI Features

### Design Principles
- **Modern & Clean**: Professional interface with attention to detail
- **Responsive**: Works seamlessly on desktop, tablet, and mobile
- **Visual Feedback**: Smooth animations and transitions
- **Color Coding**: Intuitive green/red for profit/loss
- **Accessibility**: High contrast and readable typography

### Color Scheme
```css
--primary-color: #6366f1 (Indigo)
--gold-color: #ffd700
--silver-color: #c0c0c0
--success-color: #10b981 (Green)
--danger-color: #ef4444 (Red)
--text-primary: #e5e7eb
--bg-primary: #0f172a (Dark Blue)
```

---

## 🧪 Testing

Run unit tests:
```bash
./mvnw test
```

Run with coverage:
```bash
./mvnw clean test jacoco:report
```

---

## 📈 Roadmap

### Planned Features
- [ ] User authentication and multi-user support
- [ ] Export to Excel/PDF
- [ ] Investment charts and graphs
- [ ] Email notifications for price changes
- [ ] Mobile app (React Native)
- [ ] API for third-party integrations
- [ ] Automatic price updates from market APIs
- [ ] Tax calculation and reporting
- [ ] Investment goals and targets

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Write unit tests for new features

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Built with [Spring Boot](https://spring.io/projects/spring-boot)
- UI inspired by modern web design trends
- Icons from Unicode emoji set
- Fonts from [Google Fonts](https://fonts.google.com/)

---

## 📧 Contact

**Project Maintainer**: Your Name  
**Email**: your.email@example.com  
**GitHub**: [@yourusername](https://github.com/yourusername)

---

## 💡 Tips & Best Practices

### For Accurate Tracking
1. ✅ **Always check your purchase receipt** for exact gram values
2. ✅ **Update market prices regularly** for better auto-fill experience
3. ✅ **Enter the exact price you paid** per gram when adding investments
4. ✅ **Keep daily price history** to track market trends
5. ✅ **Regular backups** if using persistent database

### Common Scenarios

**Scenario 1: I bought gold at one price, but the market price changed**
- ✅ Your profit/loss will be calculated based on the **price you paid** at purchase
- ✅ Update market prices separately for future purchases

**Scenario 2: My profit/loss doesn't match my expectations**
- ✅ Check that you entered the **correct grams** (not rounded)
- ✅ Verify the **price per gram** you entered at purchase
- ✅ Formula: `(grams × price at purchase) - amount invested`

**Scenario 3: I want to switch to a production database**
- ✅ Update `application.properties` with MySQL/PostgreSQL settings
- ✅ Change `spring.jpa.hibernate.ddl-auto` to `update` or `validate`
- ✅ Backup your data before migration

---

## 🐛 Known Issues

- None currently reported

**Found a bug?** Please [open an issue](https://github.com/yourusername/gold-silver-tracker/issues).

---

## 📊 Screenshots

### Dashboard
> Main dashboard showing portfolio overview with profit/loss by metal type

### Gold Investments
> Detailed gold investment tracking with purchase history

### Add Investment
> Form to add new investments with auto-filled market prices

### Price Management
> Update current market prices for reference

---

<div align="center">

**Made with ❤️ for precious metal investors**

⭐ Star this repo if you find it useful!

</div>
"# gold-silver-tracker" 
