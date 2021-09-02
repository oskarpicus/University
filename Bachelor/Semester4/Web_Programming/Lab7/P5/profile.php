<?php
    session_start();
    if (!isset($_SESSION["email"])) {
        header("Location: login.html");
        return;
    }
    require_once "../connecting.php";
    global $connection;
    ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>P5 - Login</title>
</head>
<body>
    <p>You are logged in as <?php echo $_SESSION["email"] ?></p>
    <p>Click <a href="upload.php">here</a> to upload a new picture</p>
    <form method="POST" action="logout.php">
        <input type="submit" name="submit" value="Logout">
        <input type="hidden" name="token" value=<?php
            try {
                $token = bin2hex(random_bytes(24));
                $_SESSION["token"] = $token;
                echo $token;
            } catch (Exception $e) {
                echo $e;
            }
        ?>>
    </form>
    <hr>
    <?php
        if ($_SESSION["id"] === $_GET["user"]) {
            echo "<p>My Pictures:</p>";
        }
        else {
            echo "<p>" . getEmailById($connection, $_GET["user"]) . "'s Pictures:</p>";
        }
        echo loadPictures($connection);
    ?>
    <p>Other people</p>
    <?php echo loadUsers($connection); ?>
</body>
<?php
    function loadPictures(PDO $connection) : string {
        $statement = $connection->prepare("SELECT path 
            FROM users INNER JOIN usersimages u on users.id = u.idUser 
                INNER JOIN images i on u.idImage = i.id WHERE users.id=:id;");
        $statement->bindParam("id", $_GET["user"]);
        $statement->execute();
        $paths = $statement->fetchAll();

        $result = "<table>";

        foreach ($paths as $path) {
            $result .= "<tr><td><img src='" . $path["path"] . "' alt='image'></td></tr>";
        }

        $result .= "</table>";
        return $result;
    }

    function loadUsers(PDO $connection) : string {
        $statement = $connection->prepare("SELECT * FROM users;");
        $statement->execute();
        $users = $statement->fetchAll();

        $result = "";

        foreach ($users as $user) {
            $result .= "<a href='profile.php?user=" . $user["id"] . "'>" . $user["email"] . "</a><br>";
        }

        return $result;
    }

    function getEmailById(PDO $connection, $id) : string {
        $statement = $connection->prepare("SELECT * FROM users WHERE id=:id;");
        $statement->bindParam(":id", $id);
        $statement->execute();
        $users = $statement->fetchAll();
        if (count($users) !== 0)
            return $users[0]["email"];
        return "";
    }