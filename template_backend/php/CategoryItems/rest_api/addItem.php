<?php
 $response = array();
 
include(__DIR__."/../sql/DBOperations.php");
   
  

  if (isset($_POST['name']) && isset($_POST['description'])
     && isset($_POST['category_id']) 
            ) 
{
      
   
      $dbOperationsObject = new DBOperations();
 
      $name = $_POST['name'];
         $description = $_POST['description'];
        $category_id = $_POST['category_id'];
 
        
      $result= $dbOperationsObject->addItem($name, $description, $category_id);
  
      if (mysqli_affected_rows($result)>0) {
         $response['success'] = 1;
         echo json_encode($response);
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

