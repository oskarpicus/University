<?php
    session_start();
    if (!isset($_SESSION["email"])) {
        session_destroy();
        header("Location: login.html");
        return;
    }
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload a new picture</title>
</head>
<body>
    <form method="POST" enctype="multipart/form-data" action="confirmUpload.php">
        <label for="image">Image:</label>
        <input type="file" id="image" name="image">
        <br>
        <input type="hidden" name="token" value=<?php
            try {
                $token = bin2hex(random_bytes(24));
                $_SESSION["token"] = $token;
                echo $token;
            } catch (Exception $e) {
                echo $e;
            }
        ?>>
        <input type="submit" name="submit">
    </form>
</body>
</html>