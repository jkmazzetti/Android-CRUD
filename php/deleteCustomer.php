<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    require_once("dbConnection.php");

    $id = $_POST['id'];

    $query = "DELETE FROM `Customers` WHERE `id`='$id'";
    $result = $connection->query($query);
    if ($result === null) {
        echo "\nError trying to delete customer...";
    } else {        
        echo "\nCustomer was deleted!";
    }
    $connection->close();
}