<?php

// array for JSON response
$response = array();

  //include(__DIR__."/../server.php");
  include(__DIR__."/GeneralFunctions.php");
  if (isset($_POST['securityKey'])) {

$dbOperationsObject = new DBOperations();
$generalFunctionsObject = new GeneralFunctionsClass();

$categories = array();
$resultCategories = $dbOperationsObject->getAllCategories();
$categories = $generalFunctionsObject->getAllCategories($resultCategories,1);

$response["success"] = 1;
$response["categories"] = $categories;
echo json_encode($response);
   //$io->emit('chat message', $response);
    //$realTime->sendMessageFromWeb('newMessageFromWeb' + 'all getAllCategories '+ $response);
    
   // foreach ($server->getClients() as $client) {
   //      $server->send($client['socket'], 'newMessageFromWeb' + 'all getAllCategories '+ $response);
                
   //  }
  }
?>

