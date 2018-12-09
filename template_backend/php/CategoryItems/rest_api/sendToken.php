<?php

  include(__DIR__."/GeneralFunctions.php");

$response = array();


if(isset($_POST['registeration_id'])){	
	$token = $_POST['registeration_id'];

      $dbOperationsObject = new DBOperations();
    $result = $dbOperationsObject->insertUserDeviceToken($token); 


    		// check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "user successfully inserted.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }


   
}
else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}