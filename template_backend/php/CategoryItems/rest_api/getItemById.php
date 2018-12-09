<?php
  $response = array();
  
  include(__DIR__."/GeneralFunctions.php");
   
 if (isset($_POST['item_id'])) {
 $item_id = $_POST['item_id'];
$dbOperationsObject = new DBOperations();
$generalFunctionsObject = new GeneralFunctionsClass();
 $resultItem = $dbOperationsObject->getItemById($item_id);
 $item = $generalFunctionsObject->getItemById($resultItem);
$response["success"] = 1;
$response["item"] = $item;
echo json_encode($response);
        
 }
 else  
 {
     $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
 }
?>