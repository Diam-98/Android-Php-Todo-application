<?php
    session_start();
    $_SESSION = array();
    $account_id;

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

    if(isset($_GET['apicall'])){
        switch ($_GET['apicall']) {
            case 'register':
                if(isset($_POST['email']) AND isset($_POST['password'])){
                    if(filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)){
                        $d = array(
                            'email'=>$_POST['email'],
                            'password'=>$_POST['password']
                        );
                            
                        $enc = json_encode($d);
                        $dec = json_decode($enc);
                        if($post->register($dec)){
                            $accId = $pdo->prepare("SELECT account_id FROM accounts_table WHERE email = :email");
                            $accId->bindParam(":email",$_POST['email']);
                            if($accId->execute()){
                                $accId = $accId->fetchAll();
                                $account_id = $accId[0]['account_id'];
                                $_SESSION['account_id'] = $accId[0]['account_id'];

                                $response['error'] = false;
                                $response['msg'] = "Inscription effectuee avec succees";
                            }else{
                                $response['error'] = true;
                                $response['msg'] = "problem d'Id";
                            }
                            
                        }else{
                            $response['error'] = true;
                            $response['msg'] = "Cet utilisateur existe deja";
                        }
                    }else{
                        $response['error'] = true;
                        $response['msg'] = "Email invalid";
                    }
                }else{
                    $response['error'] = true;
                    $response['msg'] = "remplisser tous les champs";
                }
            break;

            case 'login':
                if(isset($_POST['email']) AND isset($_POST['password'])){
                    $d = array(
                        'email'=>$_POST['email'],
                        'password'=>$_POST['password']
                    );  
                    $enc = json_encode($d);
                    $dec = json_decode($enc);
                    if($post->login($dec)){
                        $accId = $pdo->prepare("SELECT account_id FROM accounts_table WHERE email = :email");
                        $accId->bindParam(":email",$_POST['email']);
                        if($accId->execute()){
                            $accId = $accId->fetchAll();
                            $account_id = $accId[0]['account_id'];
                            $_SESSION['account_id'] = $accId[0]['account_id'];
                            $response['error'] = false;
                            $response['mail'] = $_POST['email'];
                            $response['msg'] = "connextion effectuee avec succees";
                        }else{
                            $response['error'] = true;
                            $response['msg'] = "problem d'Id";
                        }

                    }else{
                        $response['error'] = true;
                        $response['msg'] = "Login ou mot de passe incorrect";
                    }
                }else{
                    $response['error'] = true;
                    $response['msg'] = "remplisser tous les champs";
                }
            break;

            case 'insertTodo':
                if(isset($_POST['date']) AND isset($_POST['todo']) AND isset($_POST['done'])){
                    $acId = $_SESSION['account_id'];

                    // var_dump($acId);
                    $d = array(
                        "account_id"=>$account_id,
                        // "account_id"=>$acId,
                        // "account_id"=>1,
                        "date"=>$_POST['date'],
                        "todo"=>$_POST['todo'],
                        "done"=>$_POST['done']
                    );
                    $enc = json_encode($d);
                    $dec = json_decode($enc);
                    if ($post->insertTodo($dec)){

                        $response['error'] = false;
                        $response['msg'] = "Tache inseree avec succee";

                    }else{
                        
                        $response['error'] = true;
                        $response['msg'] = "Echec dinsertion de la tache";
                    }
                }else{
                    $response['error'] = true;
                    $response['msg'] = "Veuillez remplire tous les champs";
                }
                break;

                case 'updateTodo':
                    if(isset($_POST['date']) AND isset($_POST['todo']) AND isset($_POST['done'])){
                        $acId = $_SESSION['account_id'];
    
                        // var_dump($acId);
                        $d = array(
                            "account_id"=>$account_id,
                            // "account_id"=>$acId,
                            // "account_id"=>1,
                            "date"=>$_POST['date'],
                            "todo"=>$_POST['todo'],
                            "done"=>$_POST['done']
                        );
                        $enc = json_encode($d);
                        $dec = json_decode($enc);
                        if ($put->updateTodo($dec)){
    
                            $response['error'] = false;
                            $response['msg'] = "Tache inseree avec succee";
    
                        }else{
                            
                            $response['error'] = true;
                            $response['msg'] = "Echec dinsertion de la tache";
                        }
                    }else{
                        $response['error'] = true;
                        $response['msg'] = "Veuillez remplire tous les champs";
                    }
                    break;

                case 'getAllTodos':
                    if(isset($_SESSION['account_id'])){
                        $d = array(
                            "account_id"=>$_SESSION['account_id'],
                        );
                        $enc = json_encode($d);
                        $dec = json_decode($enc);
                        if ($get->getAllTodos($dec)){
    
                            $response['error'] = false;
                            $response['msg'] = "Voici l'ensemble de vos taches ";
    
                        }else{
                            
                            $response['error'] = true;
                            $response['msg'] = "Echec d'afficahe de vos taches";
                        }
                    }else{
                        $response['error'] = true;
                        $response['msg'] = "Impossible";
                    }
                    break;
            
            default:
                if(isset($_SESSION['account_id'])){
                    $d = array(
                        "account_id"=>$_SESSION['account_id'],
                    );
                    $enc = json_encode($d);
                    $dec = json_decode($enc);
                    if ($get->getAllTodos($dec)){

                        $response['error'] = false;
                        $response['msg'] = "Voici l'ensemble de vos taches ";

                    }else{
                        
                        $response['error'] = true;
                        $response['msg'] = "Echec d'afficahe de vos taches";
                    }
                }else{
                    $response['error'] = true;
                    $response['msg'] = "Impossible";
                }
            break;
        }   
    }else{
        $response['error'] = true;
        $response['msg'] = "Probleme de connexion";
    }

    echo json_encode($response);

?>