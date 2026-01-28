# Gold & Silver Investment Tracker - Detailed Project Description

## Executive Summary

The Gold & Silver Investment Tracker is a sophisticated web-based application designed to help individual investors manage and monitor their precious metal portfolios. Built using modern Java technologies and following industry best practices, this application provides accurate, real-time profit/loss calculations while maintaining complete historical accuracy of investment data.

---

## Problem Statement

### Challenges Faced by Precious Metal Investors

1. **Manual Tracking Complexity**
   - Investors often struggle to track multiple purchases made at different prices
   - Calculating accurate profit/loss requires complex arithmetic with high precision
   - Spreadsheets become unwieldy and error-prone over time

2. **Price Fluctuation Confusion**
   - Market prices change daily, making it difficult to know actual gains/losses
   - Confusion between purchase price and current market price
   - Difficulty in understanding true investment performance

3. **Data Organization**
   - Multiple investments in gold and silver need separate tracking
   - Historical data gets lost or becomes disorganized
   - No easy way to visualize overall portfolio performance

4. **Precision Requirements**
   - Small gram amounts (e.g., 0.0799g) require precise calculations
   - Rounding errors in spreadsheets can misrepresent actual profits
   - Currency calculations need accurate decimal handling

---

## Solution Overview

### Core Concept

The Gold & Silver Investment Tracker solves these problems by providing a **centralized, automated, and highly accurate** system for managing precious metal investments. The application follows a fundamental principle:

> **Each investment stores its own purchase price, ensuring historical accuracy and precise profit/loss calculations.**

### Key Innovation

Unlike traditional portfolio trackers that use current market prices for all calculations, our system:

1. **Stores the exact price paid** at the time of purchase with each investment
2. **Calculates current value** using: `Total Grams × Price at Purchase`
3. **Determines profit/loss** using: `Current Value - Amount Invested`

This approach mirrors how platforms like Jio Gold calculate selling value, providing **realistic and trustworthy** profit/loss figures.

---

## Technical Architecture

### Technology Choices & Rationale

#### Backend Framework: Spring Boot 3.2.1

**Why Spring Boot?**
- **Rapid Development**: Convention-over-configuration approach
- **Production-Ready**: Built-in security, monitoring, and health checks
- **Ecosystem**: Vast library of extensions and integrations
- **Community Support**: Large developer community and extensive documentation
- **Enterprise-Grade**: Battle-tested in production environments worldwide

#### Persistence: Spring Data JPA + Hibernate

**Why JPA?**
- **Database Abstraction**: Easy to switch between H2, MySQL, PostgreSQL
- **Type-Safe Queries**: Compile-time query validation
- **Automatic CRUD**: Reduces boilerplate code significantly
- **Relationship Management**: Handles complex entity relationships
- **Transaction Management**: Built-in transaction support

#### Template Engine: Thymeleaf

**Why Thymeleaf?**
- **Natural Templates**: HTML files work in browsers without a server
- **Server-Side Rendering**: Better SEO and initial page load
- **Spring Integration**: Seamless integration with Spring Security and i18n
- **Expression Language**: Powerful template expressions for dynamic content
- **Fragment Reusability**: Promotes DRY principles in views

#### Database: H2 (Development) / MySQL or PostgreSQL (Production)

**Why H2 for Development?**
- **Zero Configuration**: Embedded in-memory database
- **Fast Iteration**: Instant restart with fresh data
- **Easy Testing**: No external dependencies
- **Admin Console**: Built-in web interface for debugging

**Why MySQL/PostgreSQL for Production?**
- **Data Persistence**: Survives application restarts
- **Scalability**: Handles large datasets efficiently
- **ACID Compliance**: Guarantees data integrity
- **Backup & Recovery**: Enterprise-grade data protection

### Design Patterns

#### 1. Model-View-Controller (MVC)
```
┌─────────────┐      ┌─────────────┐      ┌─────────────┐
│   Browser   │─────▶│ Controller  │─────▶│   Service   │
│   (View)    │◀─────│    (MVC)    │◀─────│  (Business) │
└─────────────┘      └─────────────┘      └─────────────┘
                                                   │
                                                   ▼
                                           ┌─────────────┐
                                           │ Repository  │
                                           │   (Data)    │
                                           └─────────────┘
```

**Benefits:**
- Clear separation of concerns
- Easier testing and maintenance
- Parallel development possible
- Reusable business logic

#### 2. Repository Pattern
```java
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByMetalType(MetalType metalType);
    BigDecimal getTotalInvestedByMetalType(MetalType metalType);
}
```

**Benefits:**
- Abstracts data access logic
- Enables easy database switching
- Centralized query management
- Mockable for unit tests

#### 3. Data Transfer Object (DTO) Pattern
```java
public class InvestmentDto {
    private BigDecimal currentValue;
    private BigDecimal profitLoss;
    
    public static InvestmentDto fromEntity(Investment inv, BigDecimal price) {
        // Transform entity to DTO with calculations
    }
}
```

**Benefits:**
- Separates domain model from presentation
- Reduces over-fetching of data
- Enables custom calculated fields
- API versioning flexibility

#### 4. Service Layer Pattern
```java
@Service
public class InvestmentService {
    public DashboardStats getDashboardStats() {
        // Complex business logic isolated here
    }
}
```

**Benefits:**
- Encapsulates business logic
- Transaction management
- Reusable across controllers
- Easier to test in isolation

---

## Core Features & Implementation

### Feature 1: High-Precision Calculations

#### Problem
Financial calculations require extreme precision. Standard floating-point arithmetic (float/double) can introduce rounding errors:

```java
// ❌ BAD: Floating point arithmetic
double grams = 0.0799;
double price = 16994.55;
double value = grams * price; // Potential precision loss!
```

#### Solution
Using Java's `BigDecimal` for all financial calculations:

```java
// ✅ GOOD: BigDecimal arithmetic
BigDecimal grams = new BigDecimal("0.0799");
BigDecimal price = new BigDecimal("16994.55");
BigDecimal value = grams.multiply(price); // Exact precision!
```

**Database Schema:**
```sql
grams DECIMAL(10,5)           -- 5 decimal places
price_per_gram DECIMAL(10,2)  -- 2 decimal places
amount DECIMAL(10,2)          -- 2 decimal places
```

#### Calculation Flow
```java
// Step 1: Calculate current value for each investment
BigDecimal goldCurrentValue = goldInvestments.stream()
    .map(inv -> inv.getGrams().multiply(inv.getTodayPricePerGram()))
    .reduce(BigDecimal.ZERO, BigDecimal::add);

// Step 2: Calculate profit/loss
BigDecimal goldProfitLoss = goldCurrentValue.subtract(goldInvested);

// Step 3: Format for display only
String displayValue = String.format("%.2f", goldProfitLoss);
```

**Key Principle:** Never round during calculations, only at display time.

---

### Feature 2: Stored Purchase Prices

#### Why This Matters

**Traditional Approach (❌ Problematic):**
```
Investment 1: Bought at ₹16,000/g on Jan 1
Investment 2: Bought at ₹17,000/g on Feb 1
Current Market Price: ₹16,500/g

Calculation using current price:
- Total Grams: 2g
- Current Value: 2g × ₹16,500 = ₹33,000
- Profit/Loss: ₹33,000 - ₹33,000 = ₹0

Result: Shows ₹0 profit, which is INCORRECT!
```

**Our Approach (✅ Accurate):**
```
Investment 1: 1g at ₹16,000/g
Investment 2: 1g at ₹17,000/g

Calculation using stored prices:
- Investment 1 Value: 1g × ₹16,000 = ₹16,000
- Investment 2 Value: 1g × ₹17,000 = ₹17,000
- Total Current Value: ₹33,000
- Total Invested: ₹33,000
- Profit/Loss: ₹0

Result: Correctly shows ₹0 (you'd get what you paid)
```

#### Implementation

**Entity Design:**
```java
@Entity
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private MetalType metalType;
    private LocalDate purchaseDate;
    private BigDecimal amount;
    private BigDecimal grams;
    
    // ⭐ KEY FIELD: Price at time of purchase
    private BigDecimal todayPricePerGram;
}
```

**Service Logic:**
```java
public DashboardStats getDashboardStats() {
    // Each investment knows its own purchase price
    BigDecimal goldCurrentValue = goldInvestments.stream()
        .map(inv -> inv.getGrams().multiply(inv.getTodayPricePerGram()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    // Profit/loss reflects actual gains/losses
    BigDecimal goldProfitLoss = goldCurrentValue.subtract(goldInvested);
}
```

---

### Feature 3: Dual Metal Support

#### Architecture

**Enum-Based Metal Type:**
```java
public enum MetalType {
    GOLD("Gold"),
    SILVER("Silver");
    
    private final String displayName;
}
```

**Shared Entity Model:**
```java
@Entity
public class Investment {
    @Enumerated(EnumType.STRING)
    private MetalType metalType; // Single table for both metals
}
```

**Benefits:**
- Single codebase for multiple metals
- Easy to add new metals (PLATINUM, PALLADIUM, etc.)
- Consistent behavior across all metal types
- Shared validation and business logic

#### Separate Pages with Shared Logic

**URL Routing:**
```
/gold   → Shows only GOLD investments
/silver → Shows only SILVER investments
/       → Shows combined dashboard
```

**Controller Implementation:**
```java
@GetMapping("/gold")
public String goldInvestments(Model model) {
    return showMetalInvestments(MetalType.GOLD, model);
}

@GetMapping("/silver")
public String silverInvestments(Model model) {
    return showMetalInvestments(MetalType.SILVER, model);
}

private String showMetalInvestments(MetalType metalType, Model model) {
    // Shared logic, different data
    List<Investment> investments = 
        investmentService.getInvestmentsByMetalType(metalType);
    model.addAttribute("investments", investments);
    model.addAttribute("metalType", metalType);
    return "metal-investments";
}
```

---

### Feature 4: Dashboard Analytics

#### Comprehensive Statistics

**Data Aggregation:**
```java
public class DashboardStats {
    // Per-metal stats
    private BigDecimal goldInvested;
    private BigDecimal goldCurrentValue;
    private BigDecimal goldProfitLoss;
    private BigDecimal silverInvested;
    private BigDecimal silverCurrentValue;
    private BigDecimal silverProfitLoss;
    
    // Overall stats
    private BigDecimal totalInvested;
    private BigDecimal totalCurrentValue;
    private BigDecimal profitLoss;
    private BigDecimal profitLossPercentage;
}
```

**Calculation Logic:**
```java
// Individual metal calculations
BigDecimal goldProfitLoss = goldCurrentValue.subtract(goldInvested);
BigDecimal silverProfitLoss = silverCurrentValue.subtract(silverInvested);

// Combined totals
BigDecimal totalProfitLoss = goldProfitLoss.add(silverProfitLoss);

// Percentage calculation
if (totalInvested.compareTo(BigDecimal.ZERO) > 0) {
    BigDecimal percentage = totalProfitLoss
        .divide(totalInvested, 4, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(100));
    stats.setProfitLossPercentage(percentage);
}
```

#### Visual Indicators

**Color Coding:**
```html
<!-- Profit: Green with ▲ -->
<div th:if="${stats.goldProfitLoss >= 0}" class="positive">
    <span>▲</span>
    <span>₹</span><span th:text="${stats.goldProfitLoss}">0.00</span>
</div>

<!-- Loss: Red with ▼ -->
<div th:if="${stats.goldProfitLoss < 0}" class="negative">
    <span>▼</span>
    <span>₹</span><span th:text="${stats.goldProfitLoss}">0.00</span>
</div>
```

---

### Feature 5: Price Management System

#### Two-Tier Price System

**1. Market Prices (Reference Only)**
```java
@Entity
public class MetalPrice {
    private MetalType metalType;
    private BigDecimal pricePerGram;
    private LocalDate updatedOn;
}
```

**Purpose:**
- Provide auto-fill values when adding investments
- Keep users informed of current market rates
- NOT used for profit/loss calculations

**2. Investment Prices (Used for Calculations)**
```java
@Entity
public class Investment {
    private BigDecimal todayPricePerGram; // Price at purchase
}
```

**Purpose:**
- Store historical purchase price
- Enable accurate profit/loss calculations
- Maintain investment integrity over time

#### Daily Price History (Optional)

```java
@Entity
public class DailyPrice {
    private MetalType metalType;
    private LocalDate priceDate;
    private BigDecimal pricePerGram;
}
```

**Purpose:**
- Track price trends over time
- Analyze market movements
- Historical reference for investors

---

## User Experience Design

### Navigation Flow

```
┌─────────────────────────────────────────┐
│           🏠 Dashboard                   │
│  (Portfolio Overview)                   │
│                                         │
│  ┌───────┐  ┌───────┐  ┌───────┐      │
│  │ Gold  │  │Silver │  │ Total │      │
│  │Stats  │  │ Stats │  │ Stats │      │
│  └───────┘  └───────┘  └───────┘      │
└─────────────────────────────────────────┘
         │              │
    ┌────┴────┐    ┌───┴────┐
    ▼         ▼    ▼        ▼
┌─────────┐ ┌─────────┐  ┌──────────┐
│🟡 Gold  │ │⚪Silver │  │💲 Prices │
│  Page   │ │  Page   │  │  Update  │
└─────────┘ └─────────┘  └──────────┘
    │           │
    ▼           ▼
┌──────────────────┐
│ ➕ Add Investment│
│                  │
│ ✏️ Edit Investment│
│                  │
│ 🗑️ Delete       │
└──────────────────┘
```

### Responsive Design

**Breakpoints:**
```css
/* Mobile: < 768px */
.stats-grid {
    grid-template-columns: 1fr;
}

/* Tablet: 768px - 1024px */
.stats-grid {
    grid-template-columns: repeat(2, 1fr);
}

/* Desktop: > 1024px */
.stats-grid {
    grid-template-columns: repeat(3, 1fr);
}
```

### Visual Feedback

**Loading States:**
- Form submission shows loading indicator
- Database operations provide visual feedback
- Success/error messages with auto-dismiss

**Validation:**
- Real-time form validation
- Clear error messages
- Prevents invalid data entry

**Animations:**
```css
.fade-in {
    animation: fadeIn 0.3s ease-in-out;
}

.stat-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 30px rgba(0,0,0,0.3);
}
```

---

## Security Considerations

### Current Implementation (Development-Focused)

1. **No Authentication**: Single-user application
2. **In-Memory Database**: Data not persisted between restarts
3. **No HTTPS**: HTTP only for local development

### Production Recommendations

1. **Authentication & Authorization**
   ```java
   @EnableWebSecurity
   public class SecurityConfig {
       @Bean
       public SecurityFilterChain filterChain(HttpSecurity http) {
           http
               .authorizeHttpRequests(auth -> auth
                   .requestMatchers("/", "/login").permitAll()
                   .anyRequest().authenticated()
               )
               .formLogin();
           return http.build();
       }
   }
   ```

2. **HTTPS/TLS**
   ```properties
   server.port=8443
   server.ssl.enabled=true
   server.ssl.key-store=classpath:keystore.p12
   server.ssl.key-store-password=changeit
   ```

3. **CSRF Protection**
   ```html
   <form th:action="@{/investments/add}" method="post">
       <input type="hidden" th:name="${_csrf.parameterName}" 
              th:value="${_csrf.token}"/>
   </form>
   ```

4. **Input Validation**
   ```java
   @NotNull(message = "Amount is required")
   @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
   private BigDecimal amount;
   ```

---

## Performance Optimization

### Database Optimization

**Indexes:**
```sql
CREATE INDEX idx_metal_type ON investments(metal_type);
CREATE INDEX idx_purchase_date ON investments(purchase_date);
CREATE INDEX idx_metal_price_type ON metal_prices(metal_type);
```

**Query Optimization:**
```java
// Use projection to fetch only needed fields
@Query("SELECT SUM(i.amount) FROM Investment i WHERE i.metalType = :type")
BigDecimal getTotalInvestedByMetalType(@Param("type") MetalType type);
```

### Caching Strategy

```java
@Cacheable("dashboardStats")
public DashboardStats getDashboardStats() {
    // Expensive calculation cached
}

@CacheEvict(value = "dashboardStats", allEntries = true)
public void saveInvestment(Investment investment) {
    // Clear cache when data changes
}
```

### Frontend Optimization

```css
/* Use CSS variables for theming */
:root {
    --primary-color: #6366f1;
    --text-primary: #e5e7eb;
}

/* Minimize repaints with transform */
.stat-card:hover {
    transform: translateY(-5px); /* GPU-accelerated */
    /* Avoid: top: -5px; (causes reflow) */
}
```

---

## Testing Strategy

### Unit Testing

```java
@SpringBootTest
class InvestmentServiceTest {
    
    @Test
    void testProfitLossCalculation() {
        // Given
        Investment inv = new Investment();
        inv.setGrams(new BigDecimal("0.0799"));
        inv.setTodayPricePerGram(new BigDecimal("16994.55"));
        inv.setAmount(new BigDecimal("1358.00"));
        
        // When
        BigDecimal currentValue = inv.getGrams()
            .multiply(inv.getTodayPricePerGram());
        BigDecimal profitLoss = currentValue.subtract(inv.getAmount());
        
        // Then
        assertEquals(new BigDecimal("1357.86"), 
            currentValue.setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("-0.14"), 
            profitLoss.setScale(2, RoundingMode.HALF_UP));
    }
}
```

### Integration Testing

```java
@SpringBootTest
@AutoConfigureMockMvc
class InvestmentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testAddInvestment() throws Exception {
        mockMvc.perform(post("/investments/add")
                .param("metalType", "GOLD")
                .param("amount", "1000")
                .param("grams", "0.05")
                .param("todayPricePerGram", "20000"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/gold"));
    }
}
```

---

## Deployment Options

### Option 1: Traditional JAR Deployment

```bash
# Build executable JAR
./mvnw clean package

# Run application
java -jar target/gold-silver-tracker-1.0.0.jar
```

### Option 2: Docker Containerization

```dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# Build image
docker build -t gold-silver-tracker .

# Run container
docker run -p 8080:8080 gold-silver-tracker
```

### Option 3: Cloud Platform (Heroku)

```yaml
# system.properties
java.runtime.version=17

# Procfile
web: java -jar target/*.jar --server.port=$PORT
```

---

## Future Enhancements

### Phase 1: Multi-User Support
- User registration and login
- Separate portfolios per user
- Role-based access control

### Phase 2: Advanced Analytics
- Interactive charts (Chart.js/D3.js)
- Price trend analysis
- Investment performance comparisons
- Export to Excel/PDF

### Phase 3: Automation
- Automatic price updates via API
- Email/SMS notifications
- Scheduled reports
- Price alerts

### Phase 4: Mobile Application
- React Native/Flutter app
- Offline support
- Push notifications
- Biometric authentication

### Phase 5: API & Integrations
- RESTful API
- Third-party integrations
- Webhook support
- OAuth2 authentication

---

## Conclusion

The Gold & Silver Investment Tracker represents a well-architected, production-quality application that solves real problems for precious metal investors. By combining modern Java technologies with thoughtful design decisions, it provides:

✅ **Accuracy**: High-precision BigDecimal calculations  
✅ **Reliability**: Robust error handling and validation  
✅ **Usability**: Intuitive interface with visual feedback  
✅ **Maintainability**: Clean code following SOLID principles  
✅ **Scalability**: Easy to extend with new features  
✅ **Flexibility**: Database-agnostic design  

This project serves as both a practical tool for investors and a showcase of Spring Boot best practices, making it suitable for both personal use and as a portfolio demonstration for developers.

---

**Built with ❤️ using Spring Boot and modern Java technologies.**
