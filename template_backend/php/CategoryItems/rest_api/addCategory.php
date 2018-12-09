<?php
// array for JSON response
$response = array();
  
   include(__DIR__."/GeneralFunctions.php");
 include("firebase.php");

if (isset($_POST['name'])) 
     
           
{
 	$name = $_POST['name'];

$dbOperationsObject = new DBOperations();
$generalFunctionsObject = new GeneralFunctionsClass();
   $result= $dbOperationsObject->addCategory($name);
     if (mysqli_affected_rows($result)>0) {
         $response['success'] = 1;
         echo json_encode($response);

$categories = array();
$resultCategories = $dbOperationsObject->getAllCategories();
$categories = $generalFunctionsObject->getAllCategories($resultCategories,1);

  $devices= $dbOperationsObject->getAllRegisteredTokensDevices(); 
          if (!empty($devices)) {
     $firebase = new Firebase();
  $obj = new stdClass();
 $obj->firebase_json_message = array(
     "type" => 'normal',
      // "message" => $categories
      "message" => 'new category added'
  );
 
$messageFirbase =  json_encode($obj);
 $firebase->send_notification($devices, $messageFirbase);

}

       }
        else
        {
         $response['success'] = 0;
 echo json_encode($response);
        }
} else 
{
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}
?>

