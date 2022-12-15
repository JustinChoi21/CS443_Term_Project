## CS443 Term Project
### How to Build
1. Open Android Studio
2. Click "Get from VCS(Version Control System)"
3. Enter the URL of this repository: https://github.com/JustinChoi21/CS443_Term_Project.git
4. Click "Clone"
5. Wait for the project to be built
6. Click "Run" to run the app on an emulator

### Android API level
- target API level: 32
- min API level: 26

### Test Emulator
- Pixel 5 API 32
- Pixel 5 API 26


## Features
### 1. Login / Register / Logout
- You may register or log in with your email and password. (ex. test@gmail.com / test123)
- The login/Register process is connected with Firebase Authentication. 
- If Firebase Authentication failed, you can click the "forgot password" link on the Login page or the "Can't register" link on the Register page to see the Main page.

### Logout
- You can find the logout button by pressing the button on the upper left of the app.

### Select Your Car
- There are 50 cars you can choose from. You may change your car by clicking the "Change Car" button.

### History Menu
- You can enter your new car maintenance history (ex., refuel, change engine oil, change a tire, get regular service) by clicking the floating action button(+) on the lower left. 
- you can edit or delete the car maintenance history item.

### Stat Menu
- You can see the current year's monthly fuel usage.

### Reminder Menu
- The app calculates the recommended next date for the car maintenance action (ex., refuel, Change Engine Oil, Change Tire, Do regular service) and sets the reminder for the target date and time.
- You may turn on/off the reminder.
- You may alter the reminder's alarm time.
