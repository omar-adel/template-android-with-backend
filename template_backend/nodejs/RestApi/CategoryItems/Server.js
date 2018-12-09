 var mysql = require('mysql');
var http = require('http');
var bodyParser  = require("body-parser");
 var rest = require("./REST.js");
var db = require('./db.js');
var express = require('express'),
  app = module.exports.app = express();
var server = http.createServer(app)
 var mysqljson = require('./mysqljson.js');
 var generalClass = require('./GeneralClass.js');
 var firebase = require('./firebase.js');
 

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
 



