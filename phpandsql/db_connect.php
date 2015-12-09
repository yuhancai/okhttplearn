<?php

/**
 * A class file to connect to database
 */
class DB_CONNECT {

    // constructor
    function __construct() {
        // connecting to database
        $this->connect();
    }

    // destructor
    function __destruct() {
        // closing db connection
        $this->close();
    }

    /**
     * Function to connect with database
     */
    function connect() {
        // import database connection variables
        require_once __DIR__ . '/db_config.php';
		$con=mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE);
		if (mysqli_connect_errno($con))
		{
		   echo "Failed to connect to MySQL: " . mysqli_connect_error();
		}
        // returing connection cursor
        return $con;
    }

    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
       mysqli_close($this->connect());
    }

}

?>