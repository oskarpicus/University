<?php
    $connection = new mysqli("localhost", "root", null, "lab6pw");
    if ($connection->connect_error) {
        die("Connection refused" . $connection->connect_error);
    }

    $query = "SELECT DISTINCT departure FROM Routes";
    $statement = $connection->prepare($query);
    $statement->execute();

    $output = $statement->get_result()->fetch_all(MYSQLI_ASSOC);

    $connection->close();

    echo json_encode($output);