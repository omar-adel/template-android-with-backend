<?php
// array for JSON response
$response = array();
  include(__DIR__."/GeneralFunctions.php");
 
 if (isset($_POST['category_id'])) {

 $category_id = $_POST['category_id'];

$dbOperationsObject = new DBOperations();
$generalFunctionsObject = new GeneralFunctionsClass();
$items = array();
$resultItens = $dbOperationsObject->getAllItemsByCategory($category_id);
$items = $generalFunctionsObject->getItems($resultItens);
$response["success"] = 1;
$response["items"] = $items;
echo json_encode($response);
 }
 else  
     
 {
      $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
 }
?>

