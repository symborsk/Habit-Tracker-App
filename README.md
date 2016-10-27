-------------------------------
-----Habit Tracker README------
-------------------------------
------by: John Symborski ------
------CCID: symborsk ----------
------SID:   138781 -----------
-------------------------------
-----------------------------------------------------------------------------------------------------------------
VIDEO DEMO: 
watch online: http://sendvid.com/cei2tsl7
Download: https://archive.org/details/AppDemo_201610

Description:
-------------------------------------------------------------------------------------------------------------------
This app enables a user to organize and manage his/her individual daily habits in which they want to complete. 
-------------------------------------------------------------------------------------------------------------------

Features to note:
----------------------------------------------------------------------------------------------------------------------
- When making a habit you need to select atleast one weekday to plan on completing it.

- Toggling between days with the drop down box will allow the user to see what habits they need to complete each weekday.

-Hitting Complete will add a completion for today. You can see this is the habit history selection once you hit it.

-This app allows the user to create a habit that they want to start in the future. Simply change the start date after creation. When they set the date to the future they will not be able to complete the habit until that day.

-If a user sets a start date to after a prior completion the app will automatically delete any completions that were done before the start date(follows the logic that a habit needs to start to have a completion.)

-The complete font will turn blue if the user has completed that habit today. It is independent of the day of the week. Only based on whether  you have completed that habit today. 
----------------------------------------------------------------------------------------------------------------------------------------------

List of all attributions:
----------------------------------------------------------------------------------------------------------------------------------------------
* http://stackoverflow.com/questions/17545060/custom-view-with-button-in-arrayadapter
* http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
* http://stackoverflow.com/questions/21253623/border-of-items-of-listview-in-android
* http://stackoverflow.com/questions/16189651/android-listview-selected-item-stay-highlighted
* http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
* http://stackoverflow.com/questions/5308200/clear-text-in-edittext-when-entered
* https://github.com/joshua2ua/lonelyTwitter
* http://stackoverflow.com/questions/5469629/put-enum-values-to-android-spinner
----------------------------------------------------------------------------------------------------------------------------------------------

Building on lab machines:

-When i build on the lab machines I can a tiny error as follows: Error:(1, 1) A problem occurred evaluating project ':app'.
> Failed to apply plugin [id 'com.android.application']
 
 Gradle version 2.10 is required. Current version is 2.8. If using the gradle wrapper, ......./gradle/wrapper/gradle-wrapper.properties to gradle-2.10-all.zip

simply change the gradle-wrapper gradle version '2.8.0-all.zip' to  'gradle-2.10-all.zip' 

For some reason the gradle-wrapper generated when trying to open my project trys to set it as an earlier version which is too low for my project.

----------------------------------------------------------------------------------------------------------------------------------------------