# Data Directory

This directory contains the H2 database file that stores all your investment data.

## Files
- `goldsilverdb.mv.db` - Main database file (created automatically on first run)
- `goldsilverdb.trace.db` - Database trace/log file (if debugging enabled)

## Important Notes
- **Do NOT delete** this folder if you want to keep your data
- **Backup regularly** by copying this entire folder
- The database file is created automatically when you first run the application

## Database Access
- **JDBC URL:** `jdbc:h2:file:./data/goldsilverdb`
- **Username:** root
- **Password:** prajwal
- **Console:** http://localhost:8080/h2-console (when app is running)
