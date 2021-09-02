<?php
    $connection = new mysqli("localhost", "root", null, "lab6pw");
    if ($connection->connect_error) {
        die("Connection refused" . $connection->connect_error);
    }

    $query = "SELECT id FROM Persons";
    $statement = $connection->prepare($query);
    $statement->execute();

    $result = $statement->get_result()->fetch_all(MYSQLI_ASSOC);

    echo json_encode($result);