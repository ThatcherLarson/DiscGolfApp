# DiscGolfApp

Updated instructions as of 05/01/20.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for
development and testing purposes.

### Prerequisites

Need most updated version of Android Studio downloaded. As of 04/01/20 the latest version is 3.6.

### Installing

Either Clone or Download the repository from github using the provided link. Then find the
downloaded folder on your local device and open in Android Studio.

`git clone <url>`

## Running the Tests

Current unit tests with code coverage can be run by navigating to src>test>java>com.example.discgolfapp in the project window, right-clicking, and selecting run tests (or run tests with coverage).  You may run into an issue where all the tests fail immediately when running them all at once.  In that case you can run them all individually, or you can try editing the 'discgolfapp in app' configuration and add -noverify to the VM options for running the tests. 

To run the full, working application beta, either setup an android mobile device emulator on
Android Studio or connect an Android device.

To run an individual Activity, find the "AndroidManifest.xml" in app > src > res and check if the
specified activity has the following line:

`android:exported="true"/>`

If the line is absent, add the line. Then navigate to the specified activity, right click in the
source code area, and click "run <Activity>"

