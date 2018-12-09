<?php
define( 'API_ACCESS_KEY', 'AAAAuA4ev-k:APA91bFWCYFQ2X9FvuJpKM4Rw3ro3ZyM7hp1aBXmcWj4MfzmeXpy3AsCbEYeidgyE-RCI5CNH6OysqXms2yPa_Kn_S6-qjOaTMUHJPOK5WOkxKcCzy18LV62hBh1TStsMsinIj-Hez7E' );
define( 'FIREBASE_SEND_URL', 'https://fcm.googleapis.com/fcm/send' );

class Firebase {
 
    function __construct() {
         
    }
 
    /**
     * Sending Push Notification
     */
    public function send_notification($registatoin_ids, $message) {
        
        $msg = array
		(
			'title'		=> 'Firebase Notification',
			'message'	=> $message,
			'type'		=> 'message'
		);
		$fields = array
		(
				// 'registration_ids' 	=> array($registratoin_ids) ,
             'registration_ids' 	=> $registatoin_ids ,
			'data'			=> $msg
		);
		      		  

		$headers = array
		(
			'Authorization: key=' . API_ACCESS_KEY,
			'Content-Type: application/json'
		);
		 
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, FIREBASE_SEND_URL );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		// echo $result ;
		curl_close( $ch );
		
     }
}
 
?>