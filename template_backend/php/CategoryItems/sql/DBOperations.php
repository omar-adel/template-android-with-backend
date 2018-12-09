<?php

 include  __DIR__."/DB.php";  

class DBOperations {

    public function __constructor() {
        
    }


 
  
     
    public function getAllCategories() {

        $com = new DbConnect();
        $sql = "select * from categories order by id desc";
        $result = mysqli_query($com->getDb(), $sql);

        return $result;
    }
    
      public function getCategoryById($id) {

        $com = new DbConnect();
        $sql = "select * from categories where id =  '$id' ";
        $result = mysqli_query($com->getDb(), $sql);

        return $result;
    }

   
    public function addCategory($name) {


           $com = new DbConnect();
    
           $name =  mysqli_real_escape_string($com->getDb(),$name);  
     
      
        $sql = "insert into categories(name
                 ) VALUES  
                 ('$name' )";

        $result = mysqli_query($com->getDb(), $sql);
        return $com->getDb();
    }

    public function editCategory($id
    , $name
    ) {

        $com = new DbConnect();
        
       $id =  mysqli_real_escape_string($com->getDb(),$id);  
       $name =  mysqli_real_escape_string($com->getDb(),$name);  

      
      $sql = "update categories set 
                 name ='$name'
                 where id =  '$id' ";
        $result = mysqli_query($com->getDb(), $sql);

        return $com->getDb();
    }

    

    public function deleteCategory($id) {

        $com = new DbConnect();
        $sql = "delete from categories where id =  '$id' ";
        $result = mysqli_query($com->getDb(), $sql);
 
        return $com->getDb();
    }

    public function getAllItems() {
        $com = new DbConnect();
        $sql = "select * from items order by id desc";
        $result = mysqli_query($com->getDb(), $sql);
        return $result;
    }
      public function getAllItemsOrderedByCategory() {
        $com = new DbConnect();
        $sql = "select * from items order by category_id desc";
        $result = mysqli_query($com->getDb(), $sql);
        return $result;
    }
    

    public function getItemById($id) {

        $com = new DbConnect();
        $sql = "select * from items where id =  '$id' ";
        $result = mysqli_query($com->getDb(), $sql);

        return $result;
    }

    public function getAllItemsByCategory($category_id) {

        $com = new DbConnect();
        $sql = "select * from items where category_id='$category_id'";
        $result = mysqli_query($com->getDb(), $sql);

        return $result;
    }
    
    public function addItem($name, $description, $category_id
    ) {


            $com = new DbConnect();
    $name=  mysqli_real_escape_string($com->getDb(),$name);  
      $description =  mysqli_real_escape_string($com->getDb(),$description);  
          $category_id =  mysqli_real_escape_string($com->getDb(),$category_id);  
 
      
      
        $sql = "insert into items(name,description , category_id 
                 ) VALUES
                ('$name','$description','$category_id'
         )";

        $result = mysqli_query($com->getDb(), $sql);
        return $com->getDb();
    }


      
    

    public function editItem($id, $name, $description, $category_id) {

             $com = new DbConnect();
      $id=  mysqli_real_escape_string($com->getDb(),$id);  
      $category_id =  mysqli_real_escape_string($com->getDb(),$category_id);  
        $name =  mysqli_real_escape_string($com->getDb(),$name);  
      $description =  mysqli_real_escape_string($com->getDb(),$description);  

      
        $sql = "update items set 
               name='$name',
                description='$description',
                 category_id='$category_id'
                where id =  '$id' ";


        $result = mysqli_query($com->getDb(), $sql);

        return $com->getDb();
    }

    

    public function deleteItem($id) {

        $com = new DbConnect();
        $sql = "delete from items where id=  '$id' ";
        $result = mysqli_query($com->getDb(), $sql);
 
        return $com->getDb();
    }

   
 
             public function deleteAllCategories()
    {
        
       $com = new DbConnect();
        $sql = "delete from categories";
        $result = mysqli_query($com->getDb(), $sql);



        return $com->getDb();

       

    }

             public function deleteAllItems()
    {
        
          $com = new DbConnect();
        $sql = "delete from items";
        $result = mysqli_query($com->getDb(), $sql);



        return $com->getDb();

 
    }


    public function deleteItemsByCategory($category_id) {

        $com = new DbConnect();
        $sql = "delete from items where category_id=  '$category_id' ";
        $result = mysqli_query($com->getDb(), $sql);
 
        return $com->getDb();
    }
  

    public function insertUserDeviceToken($token){          
        $com = new DbConnect(); 
        $sql = "insert into users(registeration_id) values ('$token')";         
        $success = mysqli_query($com->getDb(), $sql);
        if($success){
            echo json_encode(array('token' => 1), JSON_PRETTY_PRINT);
        }else{
            echo json_encode(array('token' => 0), JSON_PRETTY_PRINT);
        }   

        return $success  ; 
    }
    
        public function getRegisteredTokenAtUserId(){
        
        $com = new DbConnect();
        $sql = "select * from users where  user_id = $user_id ";
        $result = mysqli_query($com->getDb(), $sql);
        $row = mysqli_fetch_row($result);
        $token = $row[1];
        return $token;
    }


    public function getAllRegisteredTokens(){
        
        $com = new DbConnect();
        $sql = "select registeration_id from users ";
        $result = mysqli_query($com->getDb(), $sql);
        return $result;
    }   


    public function getAllRegisteredTokensJsonArray(){
        
        $com = new DbConnect();
        $sql = "select registeration_id from users ";
        $result = mysqli_query($com->getDb(), $sql);

    $response["users_tokens"] = array();

         // check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // registeration_ids node
 
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $user_token = array();
        $user_token["token"] = $row["registeration_id"];
   
        // push single registeration_id into final response array
        array_push($response["users_tokens"], $user_token);
    }
    // success
  //  $response["success"] = 1;
 
    // echoing JSON response
    return $response["users_tokens"];
}
else
{
        return $response["users_tokens"];

}
 

    }



public function getAllRegisteredTokensDevices(){
        
        $com = new DbConnect();
        $sql = "select registeration_id from users ";
        $result = mysqli_query($com->getDb(), $sql);

 $devices = "" ;
         // check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // registeration_ids node
 
    while ($row = mysqli_fetch_array($result)) {
      
 
        $devices.=$row["registeration_id"].=",";

    }

     $devices= substr( $devices, 0, -1);
 

   $devices = explode(",", $devices ) ;

 
    return   $devices;
} 
             echo $devices;

 return   $devices;

    }

     

    
}
