<?php

   $DBOperations_path =   __DIR__."/../sql/DBOperations.php";
 
include($DBOperations_path);

class GeneralFunctionsClass {

    public function __constructor() 
            
     {
        
    }

      
    public function getAllCategories($resultCategories,$type) {
        $dbOperationsObject = new DBOperations();
        $categories = array();

        while ($rowCategory = mysqli_fetch_array($resultCategories)) {
            $category = array("id" => $rowCategory['id'], "name" => $rowCategory['name']  
            );
              if($type==1)
        {
            $category["items"] = array();
            $resultItems = $dbOperationsObject->getAllItemsByCategory($rowCategory["id"]);
            $items = $this->getItems($resultItems);
            $category["items"] = $items;
            
        }
                   if($type==2)
        {
           
            
        }
            array_push($categories, $category);
        }

        return $categories;
    }

       public function getCategoryByid($resultCategory) {
        
                   $dbOperationsObject = new DBOperations();
           $category =[];

           // $category = (object)array();
                   while ($rowCategory = mysqli_fetch_array($resultCategory)) {
            $category["id"] = $rowCategory["id"];
            $category["name"] = $rowCategory["name"];
              
           $category["items"] = array();
            $resultItems = $dbOperationsObject->getAllItemsByCategory($rowCategory["id"]);
            $items = $this->getItems($resultItems);
            $category["items"] = $items;
}

        return $category;
    }

    
     
   public function getItems($resultItems) {
        $items = array();

        while ($rowItem = mysqli_fetch_array($resultItems)) {

            $item= $this->getItemAtRow($rowItem);

            array_push($items, $item);
        }

        return $items;
    }
   
     

            public function getItemByid($resultItem) {
        
                   $dbOperationsObject = new DBOperations();
           $item =[];

           // $category = (object)array();
                   while ($rowItem = mysqli_fetch_array($resultItem)) {
            $item["id"] = $rowItem["id"];
            $item["name"] = $rowItem["name"];
           $item["description"] = $rowItem["description"];
            $item["category_id"] = $rowItem["category_id"];

 }

        return $item;
    }

    public function getItemAtRow($rowItem) {
        $item = array("id" => $rowItem['id']
            ,"name" => $rowItem['name']
            , "description" => $rowItem['description']
             , "category_id" => $rowItem['category_id']
         );

        return $item;
    }

       
                public function deleteAllCategories()
    {
        
    $dbOperationsObject = new DBOperations();
 $connection= $dbOperationsObject->deleteAllCategories(); 

  
     if (mysqli_affected_rows($connection) > 0) {
         
        return 1 ;
    } else {
        return 0 ;
    }
    }

             public function deleteAllItems()
    {
        
    $dbOperationsObject = new DBOperations();
 $connection= $dbOperationsObject->deleteAllItems(); 


     if (mysqli_affected_rows($connection) > 0) {
         
        return 1 ;
    } else {
        return 0 ;
    }
    }


    
         public function deleteItemsByCategory($category_id)
    {
        
    $dbOperationsObject = new DBOperations();
 $connection= $dbOperationsObject->deleteItemsByCategory($category_id); 

  
     if (mysqli_affected_rows($connection) > 0) {
         
        return 1 ;
    } else {
        return 0 ;
    }
    
    
    }

}
