var mysql = require('mysql');
  var serverFile = require('./Server.js');

module.exports.getAllCategoriesQuery = function () { 
var query = "SELECT * FROM ??";
        var table = ["categories"];
        query = mysql.format(query,table);
   return query; 
 };

module.exports.getAllDevicesQuery = function () { 
var query = "SELECT * FROM ??";
        var table = ["users"];
        query = mysql.format(query,table);
   return query; 

 };
 
 
module.exports.getAllDevices = function (callback) { 

       var devices="";
        var query = "SELECT * FROM ??";
        var table = ["users"];
  query = mysql.format(query,table);
 serverFile.db
 .query(query,function(err,rows){
             if(err) {
               // res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
               // res.json({"Error" : false, "Message" : "Success", "devices" : rows});
               devices="";
               for (var i = 0; i < rows.length; i++) {
             var row = rows[i];
             if(i===0)
                {
             devices=devices.concat(row.registeration_id);
            }
                else
                {
                             devices=devices.concat(',').concat(row.registeration_id);
                     }
    }

               callback(devices);

                        }});
 };
 
