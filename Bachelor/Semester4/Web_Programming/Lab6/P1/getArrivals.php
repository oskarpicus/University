<?php
    $connection = new mysqli("localhost", "root", null, "lab6pw");
    if ($connection->connect_error) {
        die("Connection refused" . $connection->connect_error);
    }
    $departureCity = $_GET["departure-city"];

    $query = "SELECT arrival FROM Routes WHERE departure = ?";
    $statement = $connection->prepare($query);
    $statement->bind_param("s", $departureCity);
    $statement->execute();

    $output = $statement->get_result()->fetch_all(MYSQLI_ASSOC);

    $connection->close();

    echo json_encode($output);