<?php
    $connection = new mysqli("localhost", "root", null, "lab6pw");
    if ($connection->connect_error) {
        die("Connection refused" . $connection->connect_error);
    }

    $query = "SELECT DISTINCT brand FROM Games;";
    $statement = $connection->prepare($query);
    $statement->execute();

    echo json_encode($statement->get_result()->fetch_all(MYSQLI_ASSOC));

    $connection->close();