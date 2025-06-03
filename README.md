# TAMI-Apps
Open Source Respository for Pepper Apps Produced by ZhaoRobotLab
Pepper Starting Guide

Installation Requirements

To begin programming on the Pepper robot (specifically the Android Version) you will need a few specific programs installed.

- Python version 3.8 or below
- Android Studio
  - You will need Android Studio Bumblebee 2021.1.1 Patch 3
  - You can find various installations here: [Android Studio download archives  |  Android Developers](https://developer.android.com/studio/archive)

Once you have these installed, you will need to add the Android SDK to your Android Studio

- Open Android Studio
  - You may also need to open a project; this can be a blank new one
- Choose **configure -> SDK Manager** or **Tools -> SDK Manager**
- Check **System Settings**
- Select **Android SDK**
- Select an SDK Platform and hit **Apply**
  - I recommend Android API 30, as that is what has worked for us in the past
- Once installed, go to **SDK Tools**
- Select **Show Package Details** in the bottom right corner
- Under **Android SDK Build Tools** select **the matching version to the SDK** and hit **Apply**
  - For API 30, this would be **Version 30.0.3**

Once the build tools and API are installed, you will need to add the Pepper SDK Plugin

- In Android Studio, go to **Files -> Settings -> Plugins**
- Search for **Pepper SDK** and install it
- Restart Android Studio
- You should now see **Pepper SDK** under **Tools**

Now that everything is installed, you should be ready to start programming the Pepper robot!

Project Structure Configuration

For any app that you wish to build and upload to the Pepper robot, you will need certain project configurations.

After you open a Pepper App project, go to **File** \-> **Project Structure**.

Under **Project**, ensure the following is set:

- Android Studio Plugin Version: 7.1.0
- Gradle Version: 7.4

Next, under **Modules**, ensure the following is set:

- Min Sdk Version: 23
- Target Sdk Version: 30
- Version Code: 1
- Version Name: “1.0”

Once all of these are set, the Pepper app should be able to build and upload.

Uploading to Pepper

While it is possible to test Pepper apps using the emulator in Android Studio, you might want to upload to the actual robot to test. Here is how:

- Turn on the Pepper robot
  - One brief click to the button behind the tablet; do not hold
- Make sure it is connected to WiFi
  - Swipe down from the top and you should see a WiFi icon; if it is empty is it not connected
  - Tap the icon to connect to go to **Settings** on the tablet and connect to your preferred WiFi
- Open the app in Android Studio
- In Android Studio go to **Tools -> Pepper SDK -> Connect**
  - It should ask for an IP address
- On Pepper’s tablet, swipe down from the top
  - You should see 2 IP addresses; you will need the one that says **Run/Debug**
- Enter the IP address on Android Studio
  - **If this is your first time connecting this computer to this robot, you will need to grant it permission**
    - Android Studio will say “Connection Failed”
    - On the Pepper tablet, there should be a pop up window asking if you want to allow access from this robot; hit **Yes**
    - Enter the IP address once again in Android studio, and it should connect
- Once connected, you should see the target device at the top of Android Studio change from **Pepper 1.9** to **ARTNCORE**
- Once the target device is correct, all you have to do is hit **Run**
  - Once the Gradle build finishes, the bar at the bottom right of Android Studio should say **Install**
  - Do not worry if this bar does not move, as it rarely ever does; it will disappear once the app is installed

Once installed, the app should appear under the Apps screen on the Pepper robot!

You can find all of these directions and more at the following website:

[Getting Started — QiSDK (aldebaran.com)](https://android.aldebaran.com/sdk/doc/pepper-sdk/ch1_gettingstarted/getting_started.html)
