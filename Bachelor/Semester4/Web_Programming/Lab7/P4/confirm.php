<?php
    session_start();
    if (!isset($_SESSION["email"])) {
        header("Location: login.html");
        return;
    }

    require_once "../connecting.php";
    global $connection;

    $statement = $connection->prepare("INSERT INTO users(email, password) VALUES (:email, :password);");
    $statement->bindParam(":email", $_SESSION["email"]);
    $statement->bindParam(":password", $_SESSION["password"]);
    $statement->execute();

    echo "Successfully created account ! You may log in <br>";
    echo "<a href='login.html'>Log in</a>";
    session_destroy();