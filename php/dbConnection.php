<?php

$connection = new mysqli(
    "localhost", //server ip
    "root", //user
    "", //password
    "CustomerService" //data base
    
);
if($connection->connect_error) {

    echo "Error trying to connect to database...";

}else{

    //echo "Connection was established!";

}
?>