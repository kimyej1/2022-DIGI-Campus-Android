<?php
    function connectDB() {
        $dbHost = "localhost";
        $dbName = "android";
        $dbUser = "root";
        $dbPass = "";
        $dbPort = "3306";

        $conn = new mysqli($dbHost, $dbUser, $dbPass, $dbName, $dbPort) or die("Fail : %s\n" . $conn->error);
        return $conn;
    }

    function closeDB($conn) {
        $conn->close();
    }

?>