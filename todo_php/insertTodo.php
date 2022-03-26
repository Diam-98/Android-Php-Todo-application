<?php

    session_start();
    
    $account_id = $_SESSION['account_id'];

    require_once("./config/database.php");
    require_once("./methods/post.php");
    require_once("./methods/get.php");
    require_once("./methods/put.php");
    require_once("./methods/delete.php");

    $db = new Connection();
    $pdo = $db->connect();

    $post = new Post($pdo);
    $get = new Get($pdo);
    $put = new Put($pdo);
    $delete = new Delete($pdo);


    $response = array();

    echo $account_id;

?>