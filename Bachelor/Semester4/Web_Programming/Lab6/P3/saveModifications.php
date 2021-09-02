<?php
    $connection = new mysqli("localhost", "root", null, "lab6pw");
    if ($connection->connect_error) {
        die("Connection refused" . $connection->connect_error);
    }

    $body = file_get_contents("php://input");
    $data = json_decode($body);

    $query = "UPDATE Persons SET firstName = ?, lastName = ?, phoneNumber = ?, email = ? WHERE id = ?;";
    $statement = $connection->prepare($query);
    $statement->bind_param("ssssi", $data->firstName, $data->lastName, $data->phoneNumber,
        $data->email, $data->id);
    $statement->execute();

    $connection->close();