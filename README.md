# Project: Blood Moon
Project: Blood Moon is a fantasy adventure console typing game that takes place in WWII.
You cannot officially download anything yet, as the game is still in beta.
## For Windows users
Download and extract everything in the .zip file. Make sure to keep all of the files in the same folder. Simply double click the file named start_on_windows.bat. If it says windows proptected your PC, just click "more info" and then "Run Anyway".
## For MacOS users
Download the .zip file. Make sure to keep all of the files in the same folder. Double click the file named start_on_macos.command. Many errors may occur. 

If it says "“start_on_macos.command” cannot be opened because it is from an unidentified developer.", you have to do the following:
1. Open System Preferences
2. Go to Security and Privacy
3. Click the lock and unlock it
4. Click "Open Anyway"
5. If it asks for confirmation, click Open
6. Click the lock again to save changes

If it says "The file “start_on_macos.command” could not be executed because you do not have appropriate access privileges.", do the following:
1. Right click on the file
2. Hold down option and click "Copy "start_on_macos.command" as pathname"
3. Open terminal
4. Type "chmod u+x " and paste in the pathname you copied.
5. Press enter

If it says "bad interpreter: /bin/bash^M: no such file or directory" when you try to run the file, then do the following:
1. Right click on the file
2. Hold down option and click "Copy "start_on_macos.command" as pathname"
3. Open terminal
4. Type "sed -i -e 's/\r$//' " and paste in the pathname you copied.
5. Press enter

Then the file should run without problems.
## For Linux users
If you're using Linux, then you probably know how to run a .jar file, or just run start_on_linux.sh.
