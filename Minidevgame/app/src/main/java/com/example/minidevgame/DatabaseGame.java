package com.example.minidevgame;


import android.os.StrictMode;
import android.util.Log;


import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseGame  {
    Connection con;
    String uname, pass, ip, port, database;

    public Connection connectionclass()
    {
        ip = "172.1.1.0";
        database = "BDD_GAMEDEV";
        uname ="agent_BDD";
        pass = "^WfXrpd%GJFt*g9Q*99Jam";
        port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection= null;
        String ConnectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://"+ ip +":"+ port +";"+"databasename="+database+"user="+uname+";password="+pass+";";
            connection= DriverManager.getConnection(ConnectionURL);
        }
        catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return connection;
    }
}


