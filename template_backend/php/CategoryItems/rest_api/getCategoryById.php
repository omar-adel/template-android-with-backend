<?php
  $response = array();
  
  include(__DIR__."/GeneralFunctions.php");
   
 if (isset($_POST['category_id'])) {
 $category_id = $_POST['category_id'];
$dbOperationsObject = new DBOperations();
$generalFunctionsObject = new GeneralFunctionsClass();
 $resultCategory = $dbOperationsObject->getCategoryById($category_id);
 $category = $generalFunctionsObject->getCategoryById($resultCategory);
$response["success"] = 1;
$response["category"] = $category;
echo json_encode($response);
        
 }
 else  
 {
     $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
 }
?>