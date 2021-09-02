<?php
    session_start();
    for ($i = 0; $i < 3; $i++)
        for ($j = 0; $j < 3; $j++) {
            $_SESSION["table" . strval($i) . strval($j)] = false;
        }
    ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>P4 - Javascript</title>
    <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="index.js"></script>
    <style>
        #tableGame, td {
            border: 1px solid black;
        }
        #tableGame td {
            height: 100px;
            width: 100px;
            text-align: center;
        }
    </style>
</head>
<body>
    <table id="tableGame">
        <tr>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</body>