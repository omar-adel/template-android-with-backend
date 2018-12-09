<?php

 $response = array();

include(__DIR__."/../sql/DBOperations.php");
   
if (isset($_POST['category_id']) && isset($_POST['name'])) 
{	
	$category_id= $_POST['category_id'];
	$name = $_POST['name'];

 $dbOperationsObject = new DBOperations();
  $result= $dbOperationsObject->editCategory($category_id,
              $name ); 

		 
    if (mysqli_affected_rows($result)>=0) {
         $response["success"] = 1;
 
         echo json_encode($response);
    } 

    else {

      $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";

       echo json_encode($response);

    }


	}



?>
 
