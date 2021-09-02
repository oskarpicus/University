<?php
    require_once "../getInput.php";

    $email = getInput("POST", "email");
    $passwordForm = getInput("POST", "password");

    if ($email === "" || $passwordForm === "") {
        header("Location: login.html");
        return;
    }

    require_once "../connecting.php";
    global $connection;
    $statement = $connection->prepare("SELECT * FROM users WHERE email=:email;");
    $statement->bindParam(":email", $email);
    $statement->execute();
    $users = $statement->fetchAll();

    if (count($users) === 0) {
        header("Location: invalidLogin.html");
        return;
    }

    $passwordDatabase = substr($users[0]["password"], 0, 60);

    if (!password_verify($passwordForm, $passwordDatabase)) {
        header("Location: invalidLogin.html");
        return;
    }

    session_start();
    $_SESSION["email"] = $email;
    $_SESSION["id"] = $users[0]["id"];
    $newLocation = "profile.php?user=" . $_SESSION["id"];
    header("Location: " . $newLocation);