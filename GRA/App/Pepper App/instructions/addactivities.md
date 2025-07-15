Find TutorialId script under model/data folder, and add a new ID after the last one.

Go to TutorialRepository script, add the new tutorialId under the category you want. 
For example, if you want to add a new activity under the presentation category, locate 
the "getPresentationTutorials" function and use "tutorials.add" for the new activity.
The first parameter for tutorial is the tutorialId that you just defined in TutorialId script,
the second one is a string value, which is the title of the activity, it will show on the 
card recyclerview. The third one is for voice control name, which you could talk to pepper
using this word to direct you to the activity. The last one is level, just leave it to basic.

Create a new activity under activities folder. You should create a new folder, name it as
you want. Then create the kotlin file contain the code, and name format for the file should
be "XXX"Session. You can use the MindfulnessSession as an example.

Find CategoriesRouter script, add your new activity class under the "getDestinationActivity" function.

Go to AndroidManifest.xml file and add the new activity.