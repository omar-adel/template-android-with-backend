var request = require('request');

module.exports.sendMessageToUser = function () { 
var query = "SELECT * FROM ??";
        var table = ["categories"];
        query = mysql.format(query,table);
   return query; 
 };


 module.exports.sendMessageToUsers = function(devicesIds, message) {

   console.info(devicesIds);

  request({
    url: 'https://fcm.googleapis.com/fcm/send',
    method: 'POST',
    headers: {
      'Content-Type' :' application/json',
      'Authorization': 'key=AAAAuA4ev-k:APA91bFWCYFQ2X9FvuJpKM4Rw3ro3ZyM7hp1aBXmcWj4MfzmeXpy3AsCbEYeidgyE-RCI5CNH6OysqXms2yPa_Kn_S6-qjOaTMUHJPOK5WOkxKcCzy18LV62hBh1TStsMsinIj-Hez7E'
    },
    body: JSON.stringify(
      { "data": {
        "message": message
      },
        "registration_ids" : devicesIds
      }
    )
  }, function(error, response, body) {
    if (error) { 
      console.error(error, response, body); 
    }
    else if (response.statusCode >= 400) { 
      console.error('HTTP Error: '+response.statusCode+' - '+response.statusMessage+'\n'+body); 
    }
    else {
      console.log('Done!')
    }
  })};

// sendMessageToUser(
//   "d7x...KJQ",
//   { message: 'Hello puf'}
// );