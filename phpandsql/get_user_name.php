<?php

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
$result = mysqli_query($db->connect(),"SELECT *FROM spt_user where userid=\"".$_POST["userId"]."\"");

// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
   
    
    while ($row = mysqli_fetch_array($result)) {
        $response["username"] = $row["username"];
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {

    $response["success"] = 0;
    $response["message"] = "No persons found";


    echo json_encode($response);
}
?>
