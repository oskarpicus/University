<?php
    session_start();
    if (isset($_GET["line"]) && isset($_GET["column"])) {
        $_SESSION["table" . strval($_GET["line"]) . strval($_GET["column"])] = "user";

        $check = checkEndGame();
        if ($check === "") { // no winner
            try {
                $counter = 0;
                do {
                    $line = random_int(0, 2);
                    $column = random_int(0, 2);
                    $counter++;
                } while ($_SESSION["table" . strval($line) . strval($column)] && $counter < 9);
                echo ($counter === 9) ? "!!!" : $line . $column;
                if ($counter !== 9)
                    $_SESSION["table" . strval($line) . strval($column)] = "computer";
                echo checkEndGame();
            } catch (Exception $e) {
                echo $e;
            }
        }
        else {
            echo $check;
        }
    }
    else {  // computer moves first
        $_SESSION["table00"] = "computer";
        echo "00";
    }

function checkEndGame(): string
{
        for ($line = 0; $line < 3 ; $line++) {
            $first = $_SESSION["table" . strval($line) . "0"];
            $second = $_SESSION["table" . strval($line) . "1"];
            $third = $_SESSION["table" . strval($line) . "2"];
            if ($first === $second && $second === $third && $first === $third && $first)
                return $first . " won";
        }
        for ($column = 0; $column < 3; $column++) {
            $first = $_SESSION["table" . "0" . strval($column)];
            $second = $_SESSION["table" . "1" . strval($column)];
            $third = $_SESSION["table" . "2" . strval($column)];
            if ($first === $second && $second === $third && $first === $third && $first)
                return $first . " won";
        }

        $first = $_SESSION["table00"];
        $second = $_SESSION["table11"];
        $third = $_SESSION["table22"];
        if ($first === $second && $second === $third && $first === $third && $first)
            return $first . " won";

        $first = $_SESSION["table02"];
        $third = $_SESSION["table20"];
        if ($first === $second && $second === $third && $first === $third && $first)
            return $first . " won";

        $end = true;
        for ($line = 0; $line < 3 && $end; $line++)
            for ($column = 0; $column < 3 && $end; $column++)
                if (!$_SESSION["table" . $line . $column])
                    $end = false;
        if ($end)
            return "Nobody wins";

        return "";
}