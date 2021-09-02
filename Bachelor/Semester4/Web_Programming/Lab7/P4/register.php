<?php
    require_once "../getInput.php";

    $email = getInput("POST", "email");
    $password = getInput("POST", "password");
    $passwordAgain = getInput("POST", "passwordAgain");

    if ($email === "" || $password === "" || $passwordAgain === "") {
        header("Location: register.html");
        return;
    }
    if ($password !== $passwordAgain) {
        header("Location: error.html");
        return;
    }

    require_once "../connecting.php";
    global $connection;

    $statement = $connection->prepare("SELECT * FROM users WHERE email=:email");
    $statement->bindParam(":email", $email);
    $statement->execute();
    if (count($statement->fetchAll()) !== 0) {
        header("Location: error.html");
        return;
    }

    if(!mail($email, "Confirm registration", "
        Visit http://localhost/Lab7PW/P4/confirm.php to complete the registration. Beware ! 
        The link is available for only 20 minutes
    ", "From: oskarpicus@gmail.com")) {
        echo "Invalid email";
        return;
    }
    session_start();
    $_SESSION["email"] = $email;
    $_SESSION["password"] = password_hash($password, PASSWORD_DEFAULT);
    echo "Check your email account for a link to complete the registration ";
    echo "Be fast ! It is only available for 20 minutes";