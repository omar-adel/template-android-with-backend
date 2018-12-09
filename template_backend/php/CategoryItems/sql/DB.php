<?php

  include  __DIR__."/config.php";  

class DbConnect{	
	
	private $connect;	
	
	public function __construct(){
		
		$this->connect = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
           mysqli_query($this->connect,"SET NAMES 'utf8'");
 
		if (mysqli_connect_errno($this->connect))
		{
			echo "Failed to connect to MySQL: " . mysqli_connect_error();  
		}
	}
	
	public function getDb(){
		
		return $this->connect;
	}	
} 
 