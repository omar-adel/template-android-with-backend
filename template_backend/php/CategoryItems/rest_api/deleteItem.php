<?php
include(__DIR__."/../sql/DBOperations.php");
 $response = array();
   if (isset($_POST['item_id'])) {

 $item_id = $_POST['item_id'];


 $dbOperationsObject = new DBOperations();
 $connection= $dbOperationsObject->deleteItem($item_id); 

  
     if (mysqli_affected_rows($connection) > 0) {
         $response["success"] = 1;
  
         echo json_encode($response);
    } else {
         $response["success"] = 0;
        $response["message"] = "error in deleting";
 
         echo json_encode($response);
    }

   }
 
?>

