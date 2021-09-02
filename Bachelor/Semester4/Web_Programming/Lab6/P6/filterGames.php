<?php
    $connection = new mysqli("localhost", "root", null, "lab6pw");
    if ($connection->connect_error) {
        die("Connection refused" . $connection->connect_error);
    }

    $typeParameters = "";
    $actualParameters = array();

    $brand = $_GET["brand"];
    $platform = $_GET["platform"];
    $genre = $_GET["genre"];

    $query = "SELECT name, brand, platform, genre, price FROM Games";
    if ($brand !== "any") {
        $query .= " WHERE brand=?";
        $typeParameters .= "s";
        $actualParameters[] = &$brand;
    }
    if ($platform !== "any") {
        if ($typeParameters === "")
            $query .= " WHERE platform=?";
        else
            $query .= " AND platform=?";
        $typeParameters .= "s";
        $actualParameters[] = &$platform;
    }
    if ($genre !== "any") {
        if ($typeParameters === "")
            $query .= " WHERE genre=?";
        else
            $query .= " AND genre=?";
        $typeParameters .= "s";
        $actualParameters[] = &$genre;
    }

    $statement = $connection->prepare($query);
    if ($typeParameters !== "")  // if there are filtering conditions
        $statement->bind_param($typeParameters, ...$actualParameters);
    $statement->execute();
    echo json_encode($statement->get_result()->fetch_all(MYSQLI_ASSOC));

    $connection->close();