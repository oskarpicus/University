<?php
    session_start();
    if(hash_equals($_SESSION["token"], $_POST["token"])) {
        session_start();
        if(isset($_SESSION["email"])) {
            session_destroy();
            header("Location: login.html");
        }
        else {
            header("Location: error.html");
        }
    }
    else {
        header("Location: error.html");
    }