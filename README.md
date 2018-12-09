This is template that contains :
- android project 
- backend restful api written in 3 languages : php , python , javascript using nodejs 

For Android :

1-code written by mvp architecture pattern (Model View Presenter) 

2- there is datamanager class that is responsible for any operation on data 

-it access this data by the following :

--by db package by DbHelper class if it is local data in sqlite database 
this is by using DBFlow library
https://github.com/Raizlabs/DBFlow
you will find preexisiting sqlite database in assets folder named "appdb.db" 

--by  backend package by ApiHelper class if it is remote data in mysql database or any type of remote database of course
this backend package has also feature of caching the response in CacheApi table
this is by using retrofit library
https://github.com/square/retrofit

3 - there are two methods for displaying RecyclerView

- using one adapter either GenericRecyclerViewAdapter class 

or CustomRecyclerViewAdapter class :

in this case you must create ViewHolder class for the cell 

- you will find example for GenericRecyclerViewAdapter in

RestApiListFragment here

https://github.com/omar-adel/template-android-with-backend/blob/master/template_android/app/src/main/java/com/example/template/ui/TestRestApi/fragments/RestApiListFragment.java 

and examples for CustomRecyclerViewAdapter in 

SqliteListFragment here 

https://github.com/omar-adel/template-android-with-backend/blob/master/template_android/app/src/main/java/com/example/template/ui/TestSqliteDbflow/CategoriesAndItems/Simple/fragments/SqliteListFragment.java

or in NotesActivity.java here 

https://github.com/omar-adel/template-android-with-backend/blob/master/template_android/app/src/main/java/com/example/template/ui/TestSqliteDbflow/notes/NotesActivity.java

- using many adapters meaning that each RecyclerView has its own adapter 

and all adapters extends from CustomRecyclerViewAdapterExtending class

example for CustomRecyclerViewAdapterExtending in NotesActivity.java here 

https://github.com/omar-adel/template-android-with-backend/blob/master/template_android/app/src/main/java/com/example/template/ui/TestSqliteDbflow/notes/NotesActivity.java

but it is commented

4 - there is CustomSpinner that simplify usage of spinner

5 - firebase cloud messaging 

6 - realtime updates from remote mysql database using socket.io 
https://github.com/socketio/socket.io 
in case of backend written by nodejs

5 , 6 details below

7 - you will find getCategoriesRX in getCategories function in ApiHelper class here

https://github.com/omar-adel/template-android-with-backend/edit/master/template_android/app/src/main/java/com/example/template/model/backend/ApiHelper.java

but it is commented

, it shows how to call restful api by using retrofit with rxjava 

8 - In NotesActivity , RestApiSingleFragment , SqliteSingleFragment 

 you will find validation using the following two methods

- using 

https://github.com/ragunathjawahar/android-saripaar

- using awesomeValidation module , it is modified version of 

https://github.com/thyrlian/AwesomeValidation

these methods are  described here

https://github.com/omar-adel/FormValidation

I showed usage of the two methods in SqliteSingleFragment.java

https://github.com/omar-adel/template-android-with-backend/blob/master/template_android/app/src/main/java/com/example/template/ui/TestSqliteDbflow/CategoriesAndItems/Simple/fragments/SqliteSingleFragment.java

but saripaar method is commented

and also I used the second method in NotesActivity 

https://github.com/omar-adel/template-android-with-backend/blob/master/template_android/app/src/main/java/com/example/template/ui/TestSqliteDbflow/notes/NotesActivity.java
,

RestApiSingleFragment

https://github.com/omar-adel/template-android-with-backend/blob/master/template_android/app/src/main/java/com/example/template/ui/TestRestApi/fragments/RestApiSingleFragment.java



9- This android project has the following modules separated and you can run any of them by changing the opened activity in splash activity :

1- RestApiListAct Activity : it has two fragments " categories and items " opened from navigation drawer
- it connects with the php restful api for  mysql database "CategoryItems" 
it is simple database that has 3 tables : categories , items and  users
each item has its parent category 
-- users table for sending firebase cloud messaging to all registered devices 
while adding new category for example "that is what I implemented in this code"

2 - SqliteListAct Activity - is is very similar to RestApiListAct Activity but it connects with the local sqlite database "appdb.db"
for categories and items tables

3 - ComplexOperations Activity : this is for showing how to make complex operations in sqlite by DBFlow
such as : 
- where in  
- get custom list of certain query like if you want to make join operation and return certain columns not all columns

4 - SimpleItemActivity Activity :

inserting new simple item and show count of all simple items in the sqlite database

5 - NotesActivity : display all notes in the sqlite database  in RecyclerView , inserting new notes  and ability to delete any note from the list

I used in in it the two methods for displaying RecyclerView that  is descriped in 3 main point 
but I commented the metod of using CustomRecyclerViewAdapterExtending class

6 - MapControlActivity : 

- display map in fragment 
- get current location and updates of it
you can click on any point in map to get addresses of this point
- you will find boolean flag "gotLocation" commented , you can remove this comment to move your finger 
as you want in map without continuous updating map to current location

7 - MapDisplayActivity :

- display certain location in map in fragment 
- also you can click on any point in map to get addresses of this point

there are base fragments for mapdisplay and mapcontrol that I extend to make the code simple and object oriented .

8 - SocketMainActivity :

connecting to socket.io nodejs server and when click on the button displayed , you send certain message to the server
 , if the message is "getAllCategories"  the server replies to you only 
 by returning all of categories in the remote mysql database to your device
 also the server sends your message to all devices that have sockets connected to it including your device
 and we send firebase cloud messaging notification "contains your message " to all registered devices 



For Backend :

- each backend of the 3 is restful api for "create , read , update , delete " categories and items 
you will see also how to return all categories with and without nested items or even all categories and all items in same response

- in php I made all rest api methods of type "post" but in python and nodejs
I used post , get , put and delete

- the RestApiListAct activity in the android app connects to php backend , you can modify the code to make android connects to either nodejs or python backend
but of course if you run nodejs server or python server , the android app will be able to receive 
the firebase cloud messages and the socket.io nodejs server messages

- in any server of the 3 servers , when inserting new category , the server sends firebase cloud message "new category added" to all registered devices
and in case of nodejs server , it also sends all of categories in the remote mysql database to all devices that have sockets connected to it 


- in nodejs backend
I had to use this library

https://github.com/kyleladd/node-mysql-nesting

for returning items related to its parent category in categories response by LEFT JOIN Query

- and had to use  this library
promise-mysql
https://www.npmjs.com/package/promise-mysql
to overcome the headache of " nodejs asynchronous callbacks "

  