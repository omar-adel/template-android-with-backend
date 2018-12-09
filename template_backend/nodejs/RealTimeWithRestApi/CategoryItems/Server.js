 var mysql = require('mysql');
var http = require('http');
var bodyParser  = require("body-parser");
 var rest = require("./REST.js");
var db = require('./db.js');
var express = require('express'),
  app = module.exports.app = express();
var server = http.createServer(app)
 var socketIo = require('socket.io')(server);   
 var mysqljson = require('./mysqljson.js');
 var generalClass = require('./GeneralClass.js');
 var firebase = require('./firebase.js');

socketIo.on('connection', function(socket){
  socket.on('newMessageFromAndroid', function(msg){
    console.info("newMessageFromAndroid");
    socketIo.emit('newMessageFromWeb', msg);//send to all sockets
   

///fcm///

var myObjMessage = new Object()
var dataMessage = {
    type: 'normal',
    message: msg
};
 myObjMessage.firebase_json_message = dataMessage;
generalClass.getAllDevices(function(returnValue){
devicesArray=returnValue.split(',');
  firebase.sendMessageToUsers(devicesArray,myObjMessage);
            }
        );
///fcm///



  


    if(msg==='getAllCategories')
    {
  var query = "SELECT * FROM ??";
        var table = ["categories"];
        query = mysql.format(query,table);
        db.query(query,function(err,rows){
            if(err) {
            	    socket.emit('newMessageFromWeb', 'Message  false Error executing MySQL query');//send to the sender socket

             } else {
                var newMessageFromWeb="";
                for (var i = 0; i < rows.length; i++) {
             var row = rows[i];
             newMessageFromWeb=newMessageFromWeb+":"+row.id+":"+row.name;
              console.info(row.id);
              console.info(row.name);
               }    
               
                //socket.emit('newMessageFromWeb', 'Message  Success categories ' + newMessageFromWeb);//send to the sender socket
                 newMessageFromWeb = JSON.stringify(rows);
                 socket.emit('newMessageFromWeb', 'Message  Success categories ' + newMessageFromWeb);//send to the sender socket
              }
        });
    }
  });
});
 

REST.prototype.configureExpress = function() {
      var self = this;
 app.use(bodyParser.json());         
app.use(bodyParser.urlencoded({ extended: true })); 
      var router = express.Router();
      app.use('/api', router);
      var rest_router = new rest(router);
      self.startServer();
}

REST.prototype.startServer = function() {
      server.listen(3000,function(){
          console.log("All right ! I am alive at Port 3000.");
      });
}

REST.prototype.stop = function(err) {
    console.log("ISSUE WITH MYSQL \n" + err);
    process.exit(1);
}

function REST(){
    var self = this;
    self.configureExpress();
};

new REST();
 
exports.db = db;
exports.socketIo = socketIo;
 



