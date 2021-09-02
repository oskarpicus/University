<?php
    $url = $_GET["url"];
    if (is_dir($url)) {
        $files = scandir($url);
        echo json_encode($files);
    }
    else {
        echo json_encode(file_get_contents($url));
    }