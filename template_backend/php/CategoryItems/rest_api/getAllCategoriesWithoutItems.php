<?php

// array for JSON response
$response = array();

  include(__DIR__."/GeneralFunctions.php");
  if (isset($_POST['securityKey'])) {

$dbOperationsObject = new DBOperations();
$generalFunctionsObject = new GeneralFunctionsClass();

$categories = array();
$resultCategories = $dbOperationsObject->getAllCategories();
$categories = $generalFunctionsObject->getAllCategories($resultCategories,2);

$response["success"] = 1;
$response["categories"] = $categories;
echo json_encode($response);

  }
?>

