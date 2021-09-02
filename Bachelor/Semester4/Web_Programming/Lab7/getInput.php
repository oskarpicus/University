<?php
    function getInput(string $method, string $name) : string {
        $variable = $method === "GET" ? $_GET : $_POST;
        return addslashes(htmlentities($variable[$name], ENT_COMPAT, "UTF-8"));
    }
