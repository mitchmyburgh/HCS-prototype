For Instructions on how to build the application in the command line see README.md

To run it in android studio open the project and click the run button with android sdk version 22 installed

the included files are:

Report.pdf
User\ Manual.pdf
HCS-prototype/ - The project Files
app.apk
Documentation/ - The javadoc documentation
CaseStudies/ - contains teh exampel case studies to test loading from disk
README.txt - this file
README.md - a markdown file containing instructions on how to build


To run the tests:
Open the "Build Variants" tab in Android Studio and change test Artifact to Android Instrumentation Tests
Right click the package app/java/com.hcs.protoype.hcs_prototype(androidTest) and choose run

To load a Case Study from the disk:
Add the case study .hson file and img/ folder to the same location on external storage and open the .hson file in the ass Case study dialog

This app uses specially formatted JSON data in teh form of .HSON files (for Health Script Object Notation)
These are structured as follows:

The file contains a root JSON object which contains a nested JSON object with key "CaseStudyID"
This contains 3 nested JSON Objects:
"metadata" which contains strings: "name" and "Description"
"tips" which contains a JSON Array of Strings which are tips for when the user completes the case study
"casestudy" which contains a JSON Object
inside this object is contained
"start" which is the introductory string to the case study 
"daignosis" Which is a json object containing "correct" the correct diagnosis as a string and "possible" the alternative diagnosis as a string array.
"questions" is a Json array which can contain 2 tyopes of objects
a text question structured as:
"type": "text"
"question" string containing the question
"answer" the string containing teh data gleaned from investigating the question
and Image questions which include a small quiz about what is wrong with teh question structured as follows:
"type": "img"
"question" the issue investigated
"answer" a json object containg "desc" a description of the image and "img" an array of strings which contains paths to teh images relative to teh .hson file's parent directory
"quiz" a question to be asked about the image
"quiz_answer" the string containing the correct answer to the quiz
"quiz_possible" an array of alternative (incorrect) answer strings

an example follows:

{
	"CS1":{
		"metadata":{
			"name": "CS1", "description": "Help this man"
		},
		"casestudy":{
			"start": "an old man enters, what do you do?",
			"questions":[
				{
					"type": "text",
					"question": "How old are you?",
					"answer": "What a rude question! Back in my day people just disnt ask such things"
				},
				{
					"type": "img",
					"question": "can I X-ray you?",
					"answer": {
						"desc": "Hi I am Nicole from Radiology, here is the old man's X-rays"
						"img": ["img/xray1.png", "img/xray2.png"]
					},
					"quiz": "What is wrong with the old man's xrays?",
					"quiz_answer": "He is dying from acute case of oldness",
					"quiz_possible": ["he is fresh as the dickens", "he is iron man"]
				}
			],
			"diagnosis": {
				"correct": "He is an old man and will die soon",
				"possible": ["aids", "cancer", "bunyons"]
			}
		},
		"tips": ["this is a tip"]
	}
}
 
