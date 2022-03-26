package diam.diallo.todoapp;


// La classe qui contient tous les liens qui menent vers les differents partie de l'API
public class ApisUrls {
    //l'adresse ip locale sur lequel le serveur et le telephone sont connecte
    public static final String MAIN_URL = "http://192.168.1.100/todo/index.php?apicall=";
    public static final String REGISTER_URL = MAIN_URL + "register";
    public static final String LOGIN_URL = MAIN_URL + "login";
    public static final String INSERTTODO_URL = MAIN_URL + "insertTodo";
    public static final String UPDATETODO_URL = MAIN_URL + "updateTodo";
    public static final String DELETTODO_URL = MAIN_URL + "deletTodo";
}
