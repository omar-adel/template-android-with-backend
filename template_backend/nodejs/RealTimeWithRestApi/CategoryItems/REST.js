  var serverFile = require('./Server.js');
 var mysql = require('mysql');
 var mysqljson = require('./mysqljson.js');
  var generalClass = require('./GeneralClass.js');
   var firebase = require('./firebase.js');
  var mysqlPromise = require('promise-mysql');

function REST_ROUTER(router) {
    var self = this;
    self.handleRoutes(router);
}

////


    ///////
REST_ROUTER.prototype.handleRoutes = function(router) {
    var self = this;
    router.get("/",function(req,res){
        res.json({"Message" : "Hello World !"});
    });

router.get("/devices",function(req,res){
           serverFile.db.query(generalClass.getAllDevicesQuery(),function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "devices" : rows});
             
             }
        });
    });


router.post("/categories",function(req,res){
         var query =   "INSERT INTO categories (name) VALUES ('" + req.body.name + "')";
        serverFile.db.query(query,function(err,rows){
            if(err) {
             res.json({"Error" : true, "query":query });
             res.json({"Error" : true, "Message":err });
            } else {
                res.json({"Error" : false, "Message" : "category Added !"});
               serverFile.db.query(generalClass.getAllCategoriesQuery(),function(err,rows){
            if(err) {
            } else {
               var newMessageFromWeb="";
                newMessageFromWeb = JSON.stringify(rows);

///fcm///

var myObjMessage = new Object()
var dataMessage = {
    type: 'normal',
    // message: rows
    message: "new category added"
};
 myObjMessage.firebase_json_message = dataMessage;
generalClass.getAllDevices(function(returnValue){
devicesArray=returnValue.split(',');
console.info(devicesArray);
  firebase.sendMessageToUsers(devicesArray,myObjMessage);
          //serverFile.socketIo.emit('newMessageFromWeb', 'category Added');//send to all sockets
          serverFile.socketIo.emit('newMessageFromWeb', newMessageFromWeb);//send to all sockets
            }
        );
///fcm///
             }
        });
    }}
    );
    });

 
      router.get("/categoriesWithItems",function(req,res){
       var query = 'SELECT * FROM categories LEFT JOIN items ON items.category_id = categories.id';
         var nestingOptions = [
        { tableName : 'categories', pkey: 'id'},
        { tableName : 'items', pkey: 'id', fkeys:[{table:'categories',col:'category_id'}
         ]},
    ];
 
 serverFile.db.query({sql: query, nestTables: true}
  //serverFile.db.query(query
 ,function(err,rows)
     {
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                 //res.json({"Error" : false, "Message" : "Success", "categories" : rows});

              var nestedRows = mysqljson.convertToNested(rows, nestingOptions);
              // res.send(JSON.stringify(nestedRows));
            res.send(nestedRows);
}
        });
    });



 
router.get("/categoriesAndItems",function(req,res){
      categories='';
      items='';
     console.info(req.body );
          serverFile.db.query(generalClass.getAllCategoriesQuery(),function(err,rows){

            if(err) {
              res.json({"Error" : true, "Message":err });
            } else {
                     categories = rows ;
              serverFile.db.query('SELECT * FROM items ',function(err,rows){
            if(err) {
              res.json({"Error" : true, "Message":err });
            } else {
            items = rows ;
                       res.json({
    Categories: categories,
    Items: items
       });
 }
        });
        };
             }
        );
    });




router.get("/categoriesAndItems2",function(req,res){
    categories='';
    items='';

      mysqlPromise.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'CategoryItems'
}).then(function(conn) {
        connection = conn;
       return  connection.query(generalClass.getAllCategoriesQuery()) ;
   })
       .then(function(rows){
        categories=rows;
    //        var result = connection.query('SELECT * FROM items WHERE category_id =?');
    // // var result = connection.query('select * from items where `owner`="' + rows[0].id + '" and `name`="ring"');

    var result = connection.query('SELECT * FROM items ');
   connection.end();
   items=result;
    return result;
})
       .then(function(rows) {
           items=rows;
  res.json({
    Categories: categories,
    Items: items
});

   })
       .catch(function(error){
    if (connection && connection.end) connection.end();
    //logs out the error
    console.log(error);
});

    });



router.get("/categories",function(req,res){
           serverFile.db.query(generalClass.getAllCategoriesQuery(),function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "categories" : rows});
               // console.info(rows);
               var newMessageFromWeb="";
             //    for (var i = 0; i < rows.length; i++) {
             // var row = rows[i];
             // newMessageFromWeb=newMessageFromWeb+":"+row.id+":"+row.name;
             //  console.info(row.id);
             //  console.info(row.name);
             //   }  
                 newMessageFromWeb = JSON.stringify(rows);  
              console.info(newMessageFromWeb);
                serverFile.socketIo.emit('newMessageFromWeb', newMessageFromWeb);//send to all sockets
            }
        });
    });


    router.get("/categories/:id",function(req,res){
        var query = "SELECT * FROM ?? WHERE ??=?";
        var table = ["categories","id",req.params.id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "categories" : rows});
            }
        });
    });
 
  
    router.put("/categories/:id",function(req,res){
        var query = "UPDATE ?? SET ?? = ? WHERE ?? = ?";
        var table = ["categories","name",req.body.name,"id",req.params.id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "category updated "});
            }
        });
    });

    router.delete("/categories/:id",function(req,res){
        var query = "DELETE from ?? WHERE ??=?";
        var table = ["categories","id",req.params.id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Deleted the category with id "+req.params.id});
            }
        });
    });

 

    router.get("/items",function(req,res){
        var query = "SELECT * FROM ??";
        var table = ["items"];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "items" : rows});
            }
        });
    });

    router.get("/items/:id",function(req,res){
        var query = "SELECT * FROM ?? WHERE ??=?";
        var table = ["items","id",req.params.id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "items" : rows});
            }
        });
    });

      router.get("/itemsByCategory/:category_id",function(req,res){
        var query = "SELECT * FROM ?? WHERE ??=?";
        var table = ["items","category_id",req.params.category_id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "items" : rows});
            }
        });
    });


      router.get("/itemsOrderedByCategory/",function(req,res){
        var query = "SELECT * FROM ??  order by category_id desc";
        var table = ["items"];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "items" : rows});
            }
        });
    });

    router.post("/items",function(req,res){
        var query = "INSERT INTO ??(??,??,??) VALUES (?,?,?)";
        var table = ["items","name","description","category_id",req.body.name,req.body.description,req.body.category_id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "item Added !"});
            }
        });
    });


    router.put("/items/:id",function(req,res){
           var query = "UPDATE ?? SET ?? = ? , ?? = ? , ?? = ? WHERE ?? = ?";
        var table = ["items","name",req.body.name,"description",req.body.description,"category_id",req.body.category_id,"id",req.params.id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "item updated "});
            }
        });
    });

    router.delete("/items/:id",function(req,res){
        var query = "DELETE from ?? WHERE ??=?";
        var table = ["items","id",req.params.id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Deleted the item with id "+req.params.id});
            }
        });
    });

     router.delete("/delItemsByCategory/:category_id",function(req,res){
        var query = "DELETE from ?? WHERE ??=?";
        var table = ["items","category_id",req.params.category_id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Deleted the item with category_id "+req.params.category_id});
            }
        });
    });


 

   router.post("/sendToken",function(req,res){
        var query = "INSERT INTO users(registeration_id) VALUES (?)";
        var table = [req.body.registeration_id];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "user Added !"});
            }
        });
    });


   router.delete("/categories",function(req,res){
        var query = "DELETE from ?? ";
        var table = ["categories"];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Deleted all the categories"});
            }
        });
    });



     router.delete("/items",function(req,res){
        var query = "DELETE from ?? ";
        var table = ["items"];
        query =  mysql.format(query,table);
        serverFile.db.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Deleted all the items"});
            }
        });
    });
     


}

module.exports = REST_ROUTER;
