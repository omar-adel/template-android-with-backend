from sqlalchemy import create_engine
from pyfcm import FCMNotification
# db_connect = create_engine('mysql://root@localhost/CategoryItems')

class GeneralClassUtil:

   push_service = FCMNotification(api_key="AAAAuA4ev-k:APA91bFWCYFQ2X9FvuJpKM4Rw3ro3ZyM7hp1aBXmcWj4MfzmeXpy3AsCbEYeidgyE-RCI5CNH6OysqXms2yPa_Kn_S6-qjOaTMUHJPOK5WOkxKcCzy18LV62hBh1TStsMsinIj-Hez7E")
 # def __init__ (self):
 
   def getAllCategoriesQuery(self):
         from server import db_connect
         conn = db_connect.connect() # connect to database
         query = conn.execute("select * from categories") # This line performs query and returns json result
         return query      
      

   def getAllItemsQuery(self):
         from server import db_connect
         conn = db_connect.connect() # connect to database
         query = conn.execute("select * from items  ")
         return query 

   def getAllItemsByCategoryQuery(self,category_id):
         from server import db_connect
         conn = db_connect.connect() # connect to database
         query = conn.execute("select * from items where category_id =%d "  %int(category_id))
         # result = {'items': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
         return query
  
   def getAllDevicesQuery(self):
         from server import db_connect
         conn = db_connect.connect() # connect to database
         query = conn.execute("select * from users") # This line performs query and returns json result
         return query 

   def getAllDevices( self ):

         from server import db_connect
         conn = db_connect.connect() # connect to database
         query = conn.execute("select registeration_id from users") # This line performs query and returns json result
         devices = []
         for row in query:
           data = row['registeration_id']
           devices.append(data)
         # devices = devices[:-1]
         print(devices)
         return devices 
 
            