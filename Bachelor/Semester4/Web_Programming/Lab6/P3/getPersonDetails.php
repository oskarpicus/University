<?php
    $connection = new mysqli("localhost", "root", null, "lab6pw");
    if ($connection->connect_error) {
        die("Connection refused" . $connection->connect_error);
    }

    $id = $_GET["id"];

    $query = "SELECT firstName, lastName, phoneNumber, email FROM Persons WHERE id = ?;";
    $statement = $connection->prepare($query);
    $statement->bind_param("i", $id);
    $statement->execute();

    $output = $statement->get_result()->fetch_all(MYSQLI_ASSOC)[0];

    echo json_encode($output);