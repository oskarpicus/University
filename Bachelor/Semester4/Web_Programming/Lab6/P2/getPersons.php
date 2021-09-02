<?php
    $connection = new mysqli("localhost", "root", null, "lab6pw");
    if ($connection->connect_error) {
        die("Connection refused" . $connection->connect_error);
    }
    $pageNumber = $_GET["page-number"];
    $pageSize = 3;

    $query = "SELECT firstName, lastName, phoneNumber, email FROM Persons ORDER BY id LIMIT ?, ?";
    $statement = $connection->prepare($query);
    $currentRecords = $pageNumber * $pageSize;
    $statement->bind_param("ss", $currentRecords, $pageSize);
    $statement->execute();

    $output = $statement->get_result()->fetch_all(MYSQLI_ASSOC);

    echo json_encode($output);

    $query = "SELECT COUNT(*) as number FROM Persons";
    $statement = $connection->prepare($query);
    $statement->execute();

    $nrEntries = $statement->get_result()->fetch_all(MYSQLI_ASSOC);
    $nrPages = intval($nrEntries[0]["number"] / $pageSize);

    if ($nrEntries[0]["number"] % $pageSize !== 0)
        $nrPages += 1;

    echo json_encode(array($nrPages));

    $connection->close();

