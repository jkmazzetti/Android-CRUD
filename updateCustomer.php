<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    require_once("dbConnection.php");

    $id = $_POST['id'];
    $name = $_POST['name'];
    $lastname = $_POST['lastname'];
    $birthday = $_POST['birthday'];
    $nin = $_POST['nin'];
    $gender_id = $_POST['gender_id'];
    $address = $_POST['address'];
    $city = $_POST['city'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];

    $query = "UPDATE `Customers` SET `id`='$id',`name`='$name',`lastname`='$lastname',`birthday`='$birthday',`nin`='$nin',`gender_id`='$gender_id',`address`='$address',`city`='$city',`email`='$email',`phone`='$phone' WHERE `nin`='$nin'";
    $result = $connection->query($query);
    if ($result === null) {
        echo "\nError trying to insert a new customer.";
    } else {        
        echo "\nCustomer was updated!";
    }
    $connection->close();
}
