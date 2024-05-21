<h1 align="center"> Calendar Scheduler </h1> <br>

<div align="center">
<p >
  This is a window based calendar scheduler application written using Java Swing.
</p>
<img align="center" src="https://github.com/audreensoh/CalendarScheduler/assets/170464907/725af689-df7d-4983-9543-7df3bf641670" width="600">
</div>

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [technology](#technology)
- [Quick Start](#quick-start)

## Introduction
<p>This is a Java Swing project which allows users to manage their daily tasks and keep track of their daily schedules. </p>

## Features
The main features of this calendar scheduler:
- Home page - This page shows an interactive calendar for a month. On the right hand side, it shows the tasks which were created for today. The tasks bars will be in different colors based on their categories. To add tasks for a specific date, click on the 'New' button or double click on the day in the calendar which will bring the user to the day page.
- Day page - This page shows the tasks added for a specific day. A task includes a title, time, category, description and done/not done checkbox. There will be a notes section on the right to record anything which came to the user’s mind for that day, this will be saved to the database. A random motivational quote will also be shown beside the mini calendar at the bottom of the page. 
- Add task - Users can add a task by clicking the “New” button on the home page or the "+" button in the day page. This will bring up the add/edit task form.
- Edit task - Users can edit the tasks by clicking on the tasks in the home page or day page. This will bring up the add/edit task form with pre-populated data from the database and save will update the database with the new details.
- Delete task - Users can delete a task by clicking on an existing task to open the edit task form, the delete button is on the bottom of the form. This will bring up a dialog to ask if the user is sure to delete the task. If yes, It will be removed from the database.
- Add/Edit task form - Users can add tasks using this form, the data will be saved to the database. 
- Calendar - The interactive calendar which is added in the home page. Clicking on a day will show the task of the day and double clicking on a day will open the day page for that specific day.

## Technology
### Java 8 with Java Swing
The technology used for this project is Java 8 and Java Swing.

### H2 Database Engine
H2 database is a relational database which can be embedded in Java application. The initial plan was to use MySql but MySql is a server based database, and it will need to be running either locally or using cloud(AWS or GCP). After some research, it looks like H2 database meets the requirement where it can be embedded and also it provides a solution to persist the data on application shut down. For this project, the database starts along with the application and the application will create a file called CalendarSchedulerDBSnapshot.sql if it doesn’t exist and  write the data out to CalendarSchedulerDBSnapshot.sql before it exits. The database will be shutdown when the application shuts down. When we run the application again, it will load the data from CalendarSchedulerDBSnapshot.sql if this file exists. When we first run this application, the  CalendarSchedulerDBSnapshot.sql file is not created yet and in this case, it will call the function to create the required tables.
The image below shows the database tables used in this project. The tables are all independent, there is no relation between the tables.
<img align="center" src="https://github.com/audreensoh/CalendarScheduler/assets/170464907/dd88de9e-c269-482e-90f1-790b842bb587" width="600">

## Quick Start
Run the command below to start Calendar Scheduler:
- java -jar .\CalendarScheduler.jar

