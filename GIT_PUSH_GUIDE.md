# Git Commands to Push to GitHub

## Step-by-Step Guide

### Step 1: Initialize Git Repository (if not already done)
```bash
cd e:\Gold
git init
```

### Step 2: Create .gitignore File
```bash
# Create .gitignore to exclude build files and IDE settings
echo "target/" > .gitignore
echo ".idea/" >> .gitignore
echo "*.iml" >> .gitignore
echo ".vscode/" >> .gitignore
echo ".DS_Store" >> .gitignore
echo "*.log" >> .gitignore
echo ".settings/" >> .gitignore
echo ".project" >> .gitignore
echo ".classpath" >> .gitignore
```

### Step 3: Add All Files
```bash
git add .
```

### Step 4: Create Initial Commit
```bash
git commit -m "Initial commit: Gold & Silver Investment Tracker

- Spring Boot application with Thymeleaf
- Investment tracking for gold and silver
- High-precision profit/loss calculations
- Comprehensive documentation
- MIT License"
```

### Step 5: Add Remote Repository
```bash
git remote add origin https://github.com/prajwal-m-2002/gold-silver-tracker.git
```

### Step 6: Verify Remote
```bash
git remote -v
```

### Step 7: Push to GitHub
```bash
# Push to main branch
git push -u origin main
```

**If you get an error about branch names, try:**
```bash
# Rename branch to main if it's currently master
git branch -M main
git push -u origin main
```

---

## Quick One-Liner (After Initial Setup)

For future updates, just use:
```bash
git add .
git commit -m "Your commit message"
git push origin main
```

---

## Alternative: Complete Script

Run all commands at once:
```bash
cd e:\Gold
git init
git add .
git commit -m "Initial commit: Gold & Silver Investment Tracker"
git branch -M main
git remote add origin https://github.com/prajwal-m-2002/gold-silver-tracker.git
git push -u origin main
```

---

## Troubleshooting

### Issue: "Repository already exists"
```bash
# Remove existing remote and re-add
git remote remove origin
git remote add origin https://github.com/prajwal-m-2002/gold-silver-tracker.git
```

### Issue: "Authentication failed"
You may need to use a Personal Access Token instead of password.

1. Go to: https://github.com/settings/tokens
2. Generate new token (classic)
3. Use token as password when prompted

### Issue: "Branch diverged"
```bash
# Force push (only if needed)
git push -f origin main
```

---

## After Successful Push

Your repository will be live at:
**https://github.com/prajwal-m-2002/gold-silver-tracker**

The README.md will automatically display on the repository homepage! 🎉
