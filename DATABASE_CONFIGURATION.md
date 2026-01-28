# Database Configuration Guide

## ✅ Current Configuration

Your application is now configured to **permanently save all user data** to a file-based H2 database.

### Database Credentials
- **Username:** `root`
- **Password:** `prajwal`
- **Database File:** `./data/goldsilverdb.mv.db`

### Key Changes Made

1. **Database Type:** Changed from in-memory (`jdbc:h2:mem:`) to file-based (`jdbc:h2:file:`)
2. **Data Persistence:** All data is now saved to disk and will persist across application restarts
3. **Location:** Database files are stored in the `data/` directory in your project root

---

## 🗄️ Database Details

### Tables

#### 1. **investments**
Stores all investment records entered by users:
- `id` - Unique identifier (auto-incremented)
- `metal_type` - Type of metal (GOLD or SILVER)
- `purchase_date` - Date of purchase
- `amount` - Total investment amount
- `price_per_gram` - Price per gram at purchase time
- `grams` - Weight in grams

#### 2. **metal_prices**
Stores current metal prices:
- `id` - Unique identifier
- `metal_type` - Type of metal (GOLD or SILVER)
- `price_per_gram` - Current price per gram
- `updated_on` - Last update date

---

## 🔍 Accessing the Database

### H2 Console (Database UI)
When the application is running, you can access the H2 console:

1. **URL:** http://localhost:8080/h2-console
2. **JDBC URL:** `jdbc:h2:file:./data/goldsilverdb`
3. **Username:** `root`
4. **Password:** `prajwal`

This console allows you to:
- View all tables and data
- Run SQL queries
- Export/import data
- Monitor database operations

---

## 🔄 Switching to MySQL (Production)

If you want to use MySQL instead of H2 for production:

### Step 1: Install MySQL
Download and install MySQL Server from https://dev.mysql.com/downloads/mysql/

### Step 2: Create Database
Run the provided `schema.sql` file:
```bash
mysql -u root -p < schema.sql
```

### Step 3: Update application.properties
Uncomment and update the MySQL configuration:

```properties
# Comment out H2 configuration
#spring.datasource.url=jdbc:h2:file:./data/goldsilverdb;AUTO_SERVER=TRUE
#spring.datasource.driver-class-name=org.h2.Driver
#spring.h2.console.enabled=false

# Enable MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/gold_silver_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=prajwal
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Update JPA Dialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

## 🛡️ Data Safety Features

### Automatic Schema Updates
```properties
spring.jpa.hibernate.ddl-auto=update
```
This setting ensures:
- Tables are created automatically if they don't exist
- Schema changes are applied without losing data
- No manual SQL migrations needed

### SQL Logging
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
```
These settings help you:
- See all SQL queries being executed
- Debug data issues
- Monitor database operations

---

## 📂 Database Files Location

After running the application, you'll find these files in the `data/` directory:
- `goldsilverdb.mv.db` - Main database file
- `goldsilverdb.trace.db` - Trace/log file (if debugging is enabled)

**Important:** Backup these files regularly to prevent data loss!

---

## ✨ Verification Steps

1. **Start the application:**
   ```bash
   mvnw spring-boot:run
   ```

2. **Add some data** through your application UI

3. **Stop the application** (Ctrl+C)

4. **Restart the application**

5. **Verify data persists** - All your previously entered data should still be there!

---

## 🔧 Troubleshooting

### Issue: Database file not found
**Solution:** The `data/` directory and database file will be created automatically on first run. Ensure your application has write permissions.

### Issue: Data not persisting
**Solution:** Check that you're using `jdbc:h2:file:./data/goldsilverdb` (not `jdbc:h2:mem:`)

### Issue: Cannot connect to H2 console
**Solution:** 
- Ensure application is running
- Check the JDBC URL matches: `jdbc:h2:file:./data/goldsilverdb`
- Verify credentials: root/prajwal

---

## 📊 Database Backup

To backup your data:

1. **Simple backup:** Copy the entire `data/` folder
2. **Using H2 Console:** 
   - Connect to database
   - Run: `SCRIPT TO 'backup.sql'`
   - This creates a SQL dump file

To restore:
- Copy the `data/` folder back, or
- Run the SQL script: `RUNSCRIPT FROM 'backup.sql'`

---

## 🎯 Summary

✅ Database configured with credentials: **root / prajwal**  
✅ All user data is **automatically saved** to disk  
✅ Data **persists** across application restarts  
✅ H2 console available for database management  
✅ Ready to switch to MySQL when needed  

Your investment tracker is now ready to reliably save all user data!
