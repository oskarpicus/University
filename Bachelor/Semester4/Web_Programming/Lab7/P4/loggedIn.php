<?php
    session_start();
    if (!isset($_SESSION["email"])) {
        header("Location: login.html");
        return;
    }
    ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>P4 - Login</title>
</head>
<body>
    <p>
        You are logged in with the email:
        <?php echo $_SESSION["email"] ?>
    </p>
    <form method="POST" action="logout.php">
        <input type="submit" value="Log out">
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
</body>
