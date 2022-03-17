<?php
if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    require_once('dbConnection.php');
    $id = $_GET['id'];
    $name = $_GET['name'];
    $lastname = $_GET['lastname'];
    $nin = $_GET['nin'];
    $query = "SELECT * FROM Customers WHERE nin='$nin' OR id='$id' OR name='$name' OR lastname='$lastname'";

    $result = $connection->query($query);
    if ($connection->affected_rows > 0) {

        while ($row = $result->fetch_assoc()) {
            $array = $row;
            echo "\n", json_encode($array);
        }
    } else {
        echo "\n", 'There is not data to show...';
    }
    $result->close();
    $connection->close();
}
?>