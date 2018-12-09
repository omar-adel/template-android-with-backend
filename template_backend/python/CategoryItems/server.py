#!/usr/bin/python3
from flask import Flask, request, jsonify
from flask_restful import Resource, Api
from sqlalchemy import create_engine
from json import dumps
# import json
from GeneralClass import GeneralClassUtil
generalClass = GeneralClassUtil()

  # default
db_connect = create_engine('mysql://root@localhost/CategoryItems')

# # mysql-python
# db_connect = create_engine('mysql+mysqldb://scott:tiger@localhost/foo')

# # MySQL-connector-python
# db_connect = create_engine('mysql+mysqlconnector://scott:tiger@localhost/foo')

# # OurSQL
# db_connect = create_engine('mysql+oursql://scott:tiger@localhost/foo')

app = Flask(__name__)
api = Api(app)

class AllUsers(Resource):
    def get(self):
         query = generalClass.getAllDevicesQuery() 
         result = {'users': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
         for row in query:
             print("user_id:", row['user_id'])
             print("registeration_id:", row['registeration_id'])
         return jsonify(result)
 

class AllCategories(Resource):
    def get(self):
        # conn = db_connect.connect() # connect to database
        # query = conn.execute("select * from categories") # This line performs query and returns json result
        query = generalClass.getAllCategoriesQuery() 
        result = {'categories': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
        return jsonify(result)
        # return {'categories': [i[0] for i in query.cursor.fetchall()]} # Fetches first column that is category ID

class AllCategoriesWithItems(Resource):
    def get(self):
        query = generalClass.getAllCategoriesQuery() 
        # result = {'categories': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
        categoriesWithItems = []
        # categoriesWithItems = ['categories']
        for row in query:
          # category = {}
          # category_row_as_dict = dict(row)
          category = dict(row)
          # for key, value in category_row_as_dict.items():
          #  category[key] = value
          #  print("category", category)
          queryItems = generalClass.getAllItemsByCategoryQuery(category['id']) 
          items = []
          for rowItem in queryItems:
            items.append(dict(rowItem))
          category['items']=items
          categoriesWithItems.append(category)
        print("categoriesWithItems", categoriesWithItems)
        return categoriesWithItems

class AllCategoriesAndItems(Resource):
    def get(self):
        categoriesAndItems = {}
        categories = []
        items = []
        queryCategories = generalClass.getAllCategoriesQuery() 
        for rowCategory in queryCategories:
          categories.append(dict(rowCategory))
        queryItems = generalClass.getAllItemsQuery() 
        for rowItem in queryItems:
            items.append(dict(rowItem))
        categoriesAndItems['categories']=categories
        categoriesAndItems['items']=items
        print("categoriesAndItems", categoriesAndItems)
        return categoriesAndItems


class add_category(Resource):
   def post(self):
        conn = db_connect.connect()
        name = request.form['name']
        query =  conn.execute("INSERT INTO categories(name) VALUES (%s)",name)
        queryCategories = generalClass.getAllCategoriesQuery() 
        resultCategories = {'categories': [dict(zip(tuple (queryCategories.keys()) ,i)) for i in queryCategories.cursor]}
        data_message = {
        "type" : 'normal',
        # "message" : resultCategories  
        "message" : "new category added"
         }

        data = {}
        data['firebase_json_message'] = data_message
        fcm_data={}
        fcm_data['message']=data
        result = generalClass.push_service.multiple_devices_data_message(
                   registration_ids=generalClass.getAllDevices(), data_message=fcm_data)
        print (result)
        return {'status':'success'}

class CategoryById(Resource):
    def get(self, category_id):
        conn = db_connect.connect()
        query = conn.execute("select * from categories where id =%d "  %int(category_id))
        result = {'category': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
        return jsonify(result)

class UpdateCategoryById(Resource):
    def put(self, category_id):
        conn = db_connect.connect()
        query = conn.execute ("""UPDATE categories SET name=%s WHERE id=%s""", (request.form['name'] ,  category_id))
        return {'status':'success'}

class DelCategoryById(Resource):
    def delete(self, category_id):
        conn = db_connect.connect()
        query = conn.execute("delete   from categories where id =%d "  %int(category_id))
        return {'status':'success'}

class AllItems(Resource):
    def get(self):
        conn = db_connect.connect() # connect to database
        query = conn.execute("select * from items") # This line performs query and returns json result
        result = {'items': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
        return jsonify(result)

class AllItemsByCat(Resource):
    def get(self, category_id):
        conn = db_connect.connect() # connect to database
        query = conn.execute("select * from items where category_id =%d "  %int(category_id))
        result = {'items': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
        return jsonify(result)

class AllItemsOrderedByCat(Resource):
    def get(self):
        conn = db_connect.connect() # connect to database
        query = conn.execute("select * from items where id order by category_id desc " )
        result = {'items': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
        return jsonify(result)

     
class add_item(Resource):
   def post(self):
        conn = db_connect.connect()
        name = request.form['name']
        description = request.form['description']
        category_id = request.form['category_id']
        query =  conn.execute("INSERT INTO items (name,description,category_id) VALUES (%s, %s, %s)",name, description , category_id)
        return {'status':'success'}

 
class ItemById(Resource):
    def get(self, item_id):
        conn = db_connect.connect()
        query = conn.execute("select * from items where id =%d "  %int(item_id))
        result = {'item': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
        return jsonify(result)

class UpdateItemById(Resource):
    def put(self, item_id):
        conn = db_connect.connect()
        query = conn.execute ("""UPDATE items SET name=%s, description=%s , category_id = %s WHERE id=%s""", (request.form['name'], request.form['description'] , 
         request.form['category_id'] , item_id))
        return {'status':'success'}


class DelItemById(Resource):
    def delete(self, item_id):
        conn = db_connect.connect()
        query = conn.execute("delete   from items where id =%d "  %int(item_id))
        return {'status':'success'}

class DelItemsByCatId(Resource):
    def delete(self, category_id):
        conn = db_connect.connect()
        query = conn.execute("delete   from items where category_id =%d "  %int(category_id))
        return {'status':'success'}

class SendToken(Resource):
   def post(self):
        conn = db_connect.connect()
        name = request.form['registeration_id']
        query =  conn.execute("INSERT INTO users(token) VALUES (%s)",token)
        return {'status':'success'}

api.add_resource(AllUsers, '/users')  
api.add_resource(AllCategories, '/categories') 
api.add_resource(AllCategoriesWithItems, '/categoriesWithItems') 
api.add_resource(AllCategoriesAndItems, '/categoriesAndItems') 
api.add_resource(CategoryById, '/categories/<category_id>')
api.add_resource(AllItems, '/items') 
api.add_resource(AllItemsOrderedByCat, '/itemsOrderedByCat') 
api.add_resource(AllItemsByCat, '/itemsByCat/<category_id>') 
api.add_resource(ItemById, '/items/<item_id>')
api.add_resource(add_category, '/categories') 
api.add_resource(add_item, '/items') 
api.add_resource(UpdateCategoryById, '/categories/<category_id>') 
api.add_resource(UpdateItemById, '/items/<item_id>') 
api.add_resource(DelCategoryById, '/categories/<category_id>') 
api.add_resource(DelItemById, '/items/<item_id>')
api.add_resource(DelItemsByCatId, '/DelItemsByCatId/<category_id>')

api.add_resource(SendToken, '/sendToken') 


if __name__ == '__main__':
     app.run()
