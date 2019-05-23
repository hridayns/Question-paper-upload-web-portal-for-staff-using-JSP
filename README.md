# Project Details

This repository hosts code for a Web Portal that allows faculty members to perform different levels of functions like upload, view, download and approve question papers based on their ranks/roles like teachers, HoDs and Dean.

# Usage

1. Make sure you install the latest version of XAMPP (include Apache Tomcat).
2. Go to `C:\xampp\tomcat\webapps` (or wherever you installed XAMPP) and create a new folder for your web app. Call it **test**.
3. Clone the repository using `git clone` within this directory.
4. Copy the contents of **db_bkp** folder and paste it in a new folder called **test** at `C:\xampp\mysql\data`.
5. Compile every Java src file (.java) in the `WEB-INF/classes` folder of your web app directory using `javac` command.
6. Open XAMPP contol panel. Start Apache, MySQL and Tomcat.
7. Go to **http://localhost/phpmyadmin** in your browser to make any changes to your database.
8. Go to **http://localhost:8080/test** and log in with the credentials seen in database above.