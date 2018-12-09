<?php

// array for JSON response
$response = array();

  //include(__DIR__."/../server.php");
  include(__DIR__."/GeneralFunctions.php");
  if (isset($_POST['securityKey'])) {

$dbOperationsObject = new DBOperations();
$generalFunctionsObject = new GeneralFunctionsClass();

$categories = array();
$items = array();
$resultCategories = $dbOperationsObject->getAllCategories();
//$categories = $generalFunctionsObject->getAllCategories($resultCategories,2);
$categories = $generalFunctionsObject->getAllCategories($resultCategories,1);

$resultItems = $dbOperationsObject->getAllItemsOrderedByCategory();
$items = $generalFunctionsObject->getItems($resultItems);

$response["success"] = 1;
$response["categories"] = $categories;
$response["items"] = $items;
echo json_encode($response);
  }
?>

