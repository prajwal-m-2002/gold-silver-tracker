@echo off
echo ========================================
echo  Gold & Silver Tracker - GitHub Push
echo ========================================
echo.

REM Check if in correct directory
if not exist "pom.xml" (
    echo ERROR: Not in project directory!
    echo Please run this script from e:\Gold
    pause
    exit /b 1
)

echo Step 1: Configuring Git user...
git config user.name "prajwal-m-2002"
set /p email="Enter your email address: "
git config user.email "%email%"
echo.

echo Step 2: Committing files...
git commit -m "Initial commit: Gold & Silver Investment Tracker"
if errorlevel 1 (
    echo ERROR: Commit failed!
    pause
    exit /b 1
)
echo.

echo Step 3: Renaming branch to main...
git branch -M main
echo.

echo Step 4: Adding GitHub remote...
git remote add origin https://github.com/prajwal-m-2002/gold-silver-tracker.git
echo.

echo Step 5: Pushing to GitHub...
echo.
echo NOTE: You may need to enter your credentials:
echo - Username: prajwal-m-2002
echo - Password: Use Personal Access Token (not regular password)
echo.
pause

git push -u origin main
if errorlevel 1 (
    echo.
    echo ERROR: Push failed!
    echo.
    echo Common solutions:
    echo 1. Use Personal Access Token as password
    echo 2. Check internet connection
    echo 3. Verify repository exists
    echo.
    pause
    exit /b 1
)

echo.
echo ========================================
echo  SUCCESS! Code pushed to GitHub
echo ========================================
echo.
echo Your repository is now live at:
echo https://github.com/prajwal-m-2002/gold-silver-tracker
echo.
pause
