<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <title>P1</title>
</head>
<body>
<?php
    require_once "../connecting.php";
    require_once "../getInput.php";
    require_once "displayTrains.php";
    global $connection;

    $connectingOptions = array("with", "without");

    $departure = getInput("GET", "departure");
    $arrival = getInput("GET", "arrival");
    $connectingTrain = $_GET["connecting"];

    if ($departure === "" || $arrival === "" || !in_array($connectingTrain, $connectingOptions)) {
        header("Location: index.html");
        return;
    }

    $formatter = null;
    if ($connectingTrain === "with") {
        $statement = $connection->prepare(
            'SELECT t1.id as id1, t1.type as type1, t1.departure as dept1, t1.arrival as arrv1, t1.departureTime as dt1, t1.arrivalTime as at1,
                t2.id as id2, t2.type as type2, t2.departure as dept2, t2.arrival as arrv2, t2.departureTime as dt2, t2.arrivalTime as at2
                FROM trains t1 INNER JOIN trains t2 ON t1.arrival=t2.departure
                WHERE t1.departure=:departure and t2.arrival=:arrival and t1.arrivalTime < t2.departureTime;'
        );
        $formatter = 'withConnecting';
    }
    else {
        $statement = $connection->prepare("SELECT * FROM trains WHERE departure=:departure and arrival=:arrival;");
        $formatter = 'withoutConnecting';
    }
    $statement->bindParam(":departure", $departure);
    $statement->bindParam(":arrival", $arrival);
    $statement->execute();
    $trains = $statement->fetchAll();

    if (count($trains) === 0) {
        echo "No trains to display";
        return;
    }

    echo displayTrains($trains, $formatter);
    ?>
</body>