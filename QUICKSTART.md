# 🚀 Quick Start Guide - Gold & Silver Investment Tracker

Get up and running in **5 minutes**!

## ⚡ Super Quick Start (TL;DR)

```bash
# 1. Clone & Navigate
git clone <repository-url>
cd gold-silver-tracker

# 2. Run Application
./mvnw spring-boot:run         # Linux/Mac
mvnw.cmd spring-boot:run        # Windows

# 3. Open Browser
# Visit: http://localhost:8080
```

**That's it!** 🎉 You're ready to start tracking investments.

---

## 📋 Step-by-Step Guide

### Step 1: Prerequisites Check ✓

Before starting, ensure you have:

- **Java 17+** installed
  ```bash
  java -version
  # Should show: java version "17" or higher
  ```

- **Git** installed (optional, for cloning)
  ```bash
  git --version
  ```

💡 **Don't have Java?** Download from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)

---

### Step 2: Get the Code 📥

**Option A: Clone with Git**
```bash
git clone https://github.com/yourusername/gold-silver-tracker.git
cd gold-silver-tracker
```

**Option B: Download ZIP**
1. Download ZIP from GitHub
2. Extract to a folder
3. Open terminal/command prompt in that folder

---

### Step 3: Run the Application 🏃

**On Linux/Mac:**
```bash
./mvnw spring-boot:run
```

**On Windows:**
```bash
mvnw.cmd spring-boot:run
```

**What happens next:**
- Maven downloads dependencies (first time only, ~2-3 minutes)
- Application compiles
- Server starts on port 8080
- You'll see: `Started GoldSilverTrackerApplication in X seconds`

💡 **Port 8080 busy?** Stop other applications or change the port in `application.properties`

---

### Step 4: Open Your Browser 🌐

Visit: **http://localhost:8080**

You should see the **Dashboard** page!

---

## 🎯 First Steps in the Application

### 1️⃣ Set Market Prices (Recommended)

**Why?** These prices will auto-fill when you add investments.

1. Click **"Update Prices"** in the navigation
2. Enter current market prices:
   - Gold: ₹7,000 per gram (example)
   - Silver: ₹90 per gram (example)
3. Click **"💲 Update Prices"**

✅ **Success!** You'll see a confirmation message.

---

### 2️⃣ Add Your First Investment

**Example: Record a gold purchase**

1. Click **"🟡 Gold"** in navigation
2. Click **"➕ Add New Investment"**
3. Fill in the form:
   ```
   Date: 2026-01-15
   Amount Invested: ₹10,000
   Grams Purchased: 1.42857
   Today's Price: ₹7,000 (auto-filled)
   ```
4. Click **"💰 Add Investment"**

✅ **Done!** You'll see your investment in the list.

---

### 3️⃣ View Your Dashboard

1. Click **"Dashboard"** in navigation
2. See your portfolio summary:
   - Total grams owned
   - Total invested
   - Current value
   - Profit/Loss

---

## 🧪 Try the Demo Data

Want to see the app in action with sample data?

### Quick Demo Setup

1. **Add Market Prices:**
   - Gold: ₹7,200/g
   - Silver: ₹92/g

2. **Add Sample Investments:**

   **Gold Investment 1:**
   ```
   Date: 2026-01-10
   Amount: ₹10,000
   Grams: 1.40845
   Price: ₹7,100/g
   ```

   **Gold Investment 2:**
   ```
   Date: 2026-01-20
   Amount: ₹15,000
   Grams: 2.08333
   Price: ₹7,200/g
   ```

   **Silver Investment 1:**
   ```
   Date: 2026-01-15
   Amount: ₹5,000
   Grams: 54.945
   Price: ₹91/g
   ```

3. **Check Dashboard** - You should see:
   - Total Invested: ₹30,000
   - Profit/Loss calculations
   - Separate stats for Gold & Silver

---

## 🔧 Troubleshooting

### Issue: `Command not found: mvnw`

**Solution:**
```bash
# Make the script executable (Linux/Mac)
chmod +x mvnw

# Or use Maven directly if installed
mvn spring-boot:run
```

---

### Issue: `Port 8080 already in use`

**Solution 1:** Stop the application using port 8080

**Solution 2:** Change the port
1. Edit `src/main/resources/application.properties`
2. Add: `server.port=8081`
3. Restart the application
4. Visit: http://localhost:8081

---

### Issue: `Application starts but page won't load`

**Check:**
1. Is the application still running? (Check terminal)
2. Is the URL correct? (http://localhost:8080)
3. Try a different browser
4. Check firewall settings

---

### Issue: `Java version error`

**Error Message:**
```
Unsupported class version...
```

**Solution:**
- You need Java 17 or higher
- Update Java: [Download Here](https://adoptium.net/)
- Verify: `java -version`

---

## 🎓 Learning the Interface

### Navigation Bar

```
┌──────────────────────────────────────┐
│ 💎 Gold & Silver Tracker             │
│                                      │
│ [Dashboard] [🟡 Gold] [⚪ Silver]    │
│             [Update Prices]          │
└──────────────────────────────────────┘
```

- **Dashboard**: Overview of all investments
- **🟡 Gold**: Gold-specific investments
- **⚪ Silver**: Silver-specific investments
- **Update Prices**: Set current market prices

---

### Understanding the Dashboard

**Gold Summary Card:**
```
┌─────────────────────────────┐
│ 🟡 Gold Summary             │
├─────────────────────────────┤
│ Total Gold: 1.500 grams     │
│ Amount Invested: ₹10,500    │
│ Current Value: ₹10,800      │
│ Gold P/L: 🟢 ▲₹300 (2.86%) │
└─────────────────────────────┘
```

**What this means:**
- You own **1.500 grams** of gold
- You invested **₹10,500** in total
- Current value is **₹10,800**
- You're in **profit of ₹300** (2.86%)

---

### Adding Investments - Field Guide

| Field | Description | Example |
|-------|-------------|---------|
| **Date** | Purchase date | 2026-01-15 |
| **Amount Invested** | Money you paid | ₹10,000 |
| **Grams Purchased** | Exact grams received | 1.42857 |
| **Today's Price** | Price per gram at purchase | ₹7,000 |

**💡 Tips:**
- ✅ Check your receipt for **exact grams**
- ✅ Enter the **actual price you paid** per gram
- ✅ Don't round grams - be precise!

---

## 📱 Using on Mobile

The application is **fully responsive**!

1. Make sure your phone is on the **same network** as your computer
2. Find your computer's IP address:
   ```bash
   # Windows
   ipconfig
   
   # Mac/Linux
   ifconfig
   ```
3. On your phone, visit: `http://YOUR_IP:8080`
   - Example: `http://192.168.1.100:8080`

---

## 🛑 Stopping the Application

**In Terminal:**
- Press **Ctrl + C** (Windows/Linux)
- Press **Cmd + C** (Mac)

**Note:** Since we're using H2 in-memory database, **all data will be lost** when you stop the application.

**Want to keep data?** Switch to MySQL/PostgreSQL (see README.md)

---

## ⏭️ Next Steps

Now that you're up and running:

1. 📖 Read the full [README.md](README.md) for detailed features
2. 🏗️ Check [PROJECT_DESCRIPTION.md](PROJECT_DESCRIPTION.md) for architecture details
3. 🤝 Want to contribute? See [CONTRIBUTING.md](CONTRIBUTING.md)
4. 🗄️ Need persistent storage? Configure MySQL/PostgreSQL
5. 🚀 Deploy to production? Check deployment guides

---

## 💬 Need Help?

- 📧 Email: support@example.com
- 🐛 Found a bug? [Open an Issue](https://github.com/yourusername/gold-silver-tracker/issues)
- 💡 Have a suggestion? We'd love to hear it!

---

## 🎉 Congratulations!

You're now ready to track your precious metal investments like a pro!

**Happy Investing!** 💎✨

---

<div align="center">

**Built with ❤️ for precious metal investors**

[⭐ Star on GitHub](https://github.com/yourusername/gold-silver-tracker) | [📖 Documentation](README.md) | [🐛 Report Bug](https://github.com/yourusername/gold-silver-tracker/issues)

</div>
