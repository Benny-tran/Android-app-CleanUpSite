Student id: s3681419
Student name: Tran Tri Tai

Functionality:

#SplashScreenActivity
In this activity, I use a Handler().postDelayed to display a simple logo pop up for 4s and then it move to SignUpActivity. 
Also I use getSuppurtActionBar() to display my app name.

#SignUpActivity
In this activity, user who have not create a account must create one. I use SQlite database to Create new table and add user information and retrive their data to login.
In the SignUp form, I have 3 Edittext (Email, Password, rePassword) for user to fill in all their info and I also use StringBuilder for the Gender checkbox. Then, if
the user did not type anything in these space, an error message toast up. Finally, when the user types all the field and click the "Add" button, user will attempt the
next activity. If the user already have an account, you just need to click the Textview ("Already Registered ? Login Here") to go to the LoginActivity.

#LoginActivity
In this activity, user need to fill the exactly the same Email and Password info to login successfully. I use DatabaseHelper to check the user's input with the database.
I use the function call "checkemailpassword" in DatabaseHelper to check the input.

#HomeActivity
In this activity, I create a bottom navigation have 3 fragments (MapFragment,ListFragment,ProfileFragment).

#ProfileFragment
In this fragment, I just input some information about myself and a Logout button to exit the app.

#ListFragment
In this fragment, I display the list of every Events in the database. In this fragment, I use firestore to be the database, I create a Textview to be a place to display the 
data and a CollectionReference call "notebookref" to get the firestore collection path.
I addOnSuccessListener for the notebookref to create a method call onSuccess. In this method, I create a for loop to get all the Events and then print it out on the 
Textview. 

#MapFragment
This is the fragment have the most function in my app. The fundamental feature is the map itself. First, I create a Google map by installing the google map in android studio
and set a zoom control, a toolbar, a marker point at RMIT SGS campus and setOnMapClickListener .
Then, In the onMapClick method, I want to create a marker when I tap at any point on the map. To do that, I have to get the latitude and longitude info and use intent
to put it through another activity call AddCleanUpActivity to input more information about the event at that location. In AddCleanUpActivity, I add data by using 
Map<String, Object> events = new HashMap<>() from the user input by using function getText().toString(). Then I get back to the Mapfragment. From here, whenever I click
the marker icon, there is a small infoWindow showing the Event name and the contact number of that Event by using function setOnMapLoadedCallback. Then, I use the function 
call onInfoWindowClick for user moving to another activity call ShowEventActivity when he/she clicks the infoWindow. In the ShowEventActivity, user will see all the event
detail about that Event and decide want to join or not. If he/she clicks the Join button, a message will toast up. 
