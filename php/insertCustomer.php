<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    require_once("dbConnection.php");

    $id = $_POST['id']; //id=NULL
    $name = $_POST['name'];
    $lastname = $_POST['lastname'];
    $birthday = $_POST['birthday'];
    $nin = $_POST['nin'];
    $gender_id = $_POST['gender_id'];
    $address = $_POST['address'];
    $city = $_POST['city'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];

    $query = "INSERT INTO `Customers` (`id`, `name`, `lastname`, `birthday`, `nin`, `gender_id`, `address`, `city`, `email`, `phone`) 
    VALUES(NULL,'$name','$lastname','$birthday','$nin','$gender_id','$address','$city','$email','$phone')";

    $result = $connection->query($query);
    if ($result === null) {
        echo "\nError trying to insert a new customer...";
    } else {
        echo "\nCustomer was registered!";
    }
    $connection->close();
}
