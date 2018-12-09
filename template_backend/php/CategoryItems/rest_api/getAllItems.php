<?php

// array for JSON response
$response = array();

  include(__DIR__."/GeneralFunctions.php");

  if (isset($_POST['securityKey'])) {

$dbOperationsObject = new DBOperations();
$generalFunctionsObject = new GeneralFunctionsClass();
$items = array();
$resultItems = $dbOperationsObject->getAllItems();
$items = $generalFunctionsObject->getItems($resultItems);

$response["success"] = 1;
$response["items"] = $items;
echo json_encode($response);

  }
?>

