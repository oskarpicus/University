<?php
    session_start();
    $availableExtensions = array("jpg", "png", "gif", "jpeg");
    if ($_SERVER["REQUEST_METHOD"] !== "POST" || $_POST["token"] !== $_SESSION["token"] || !isset($_POST["submit"])) {
        header("Location: error.html");
        return;
    }
    $targetPath = "images/" . basename($_FILES["image"]["name"]);
    $extension = strtolower(pathinfo($targetPath, PATHINFO_EXTENSION));

    if (getimagesize($_FILES["image"]["tmp_name"]) === false) {
        echo "File is not an image";
        return;
    }

    if ($_FILES["image"]["size"] > 100000) {
        echo "Sorry, file is too large";
        return;
    }

    if (!in_array($extension, $availableExtensions)) {
        echo "This extension does not belong to a image";
        return;
    }

    if (file_exists($targetPath)) {
        echo "File already exists";
        return;
    }

    require_once "../connecting.php";
    global $connection;

    if (savePicture($targetPath, $connection)) {
            move_uploaded_file($_FILES["image"]["tmp_name"], $targetPath);
            echo "Uploaded picture successfully <br>";
            echo "<a href='profile.php?user=" . $_SESSION["id"] . "'>Return to your profile</a>";
    }
    else {
        echo "Failed to upload picture";
    }


function savePicture(string $targetPath, $connection) : bool {
    try {
        $statement = $connection->prepare("INSERT INTO images(path) VALUES (:path);");
        $statement->bindParam(":path", $targetPath);
        $statement->execute();

        $statement = $connection->prepare("SELECT MAX(id) AS Last FROM images;");
        $statement->execute();
        $lastId = $statement->fetchAll()[0]["Last"];

        $statement = $connection->prepare("INSERT INTO usersimages(idUser, idImage) VALUES (:idUser, :idImage)");
        $statement->bindParam(":idUser", $_SESSION["id"]);
        $statement->bindParam(":idImage", $lastId);

        $statement->execute();

        return true;
    } catch (Exception $exception) {
        return false;
    }
}