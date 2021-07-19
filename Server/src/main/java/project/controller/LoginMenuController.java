package project.controller;


import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import project.ServerMainController;
import project.model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class LoginMenuController {

    public String createUser(String username, String nickname, String password) {
        if (isUsernameUsed(username)) return "used_username";
        if (isNicknameUsed(nickname)) return "used_nickname";
        User user = new User(username, password, nickname);
        new ScoreboardData(user.getNickname(), user.getScore(), false);
        sendScoreboardData();
        //saveToDataBase(username, nickname, password);
        //saveToMongo(username, nickname, password);
        return "success";
    }

    public void saveToDataBase(String username1, String nickname1, String password1) {
        String url = "jdbc:mysql://localhost:3306/user";
        String usernameLH = "root";
        String passwordLH = "12345678";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, usernameLH, passwordLH);
            System.out.println("Connection was successful : " + url);
            String query = "Insert into information(username,nickname,password) values(" + "'" + username1 + "'" + "," + "'" + nickname1 + "'" + "," + "'" + password1 + "'" + ")";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveToMongo(String username1, String nickname1, String password1) {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "savingUsers", "password".toCharArray());
        System.out.println("Connected to the database successfully");
        MongoDatabase database = mongo.getDatabase("savingUsers");
        MongoCollection<Document> collection = database.getCollection("usersCollection");
        System.out.println("Collection usersCollection selected successfully");
        Document document1 = new Document("title", "SavingUsersInMongoDB")
                .append("username", username1)
                .append("nickname", nickname1)
                .append("password", password1);
        List<Document> list = new ArrayList<Document>();
        list.add(document1);
        collection.insertMany(list);
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;
        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            i++;
        }
    }

    public void sendScoreboardData() {
        ArrayList<ScoreboardData> scoreboardData = ScoreboardData.getDataArrayList();
        synchronized (ServerMainController.getScoreboardDataTransfer()) {
            for (String s : ServerMainController.getScoreboardDataTransfer().keySet()) {
                try {
                    ServerMainController.getScoreboardDataTransfer().get(s).writeUTF(new Gson().toJson(scoreboardData));
                    ServerMainController.getScoreboardDataTransfer().get(s).flush();
                    System.out.println("sent");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isUsernameUsed(String username) {
        return User.getUserByUsername(username) != null;
    }

    public boolean isNicknameUsed(String nickname) {
        ArrayList<User> allUsers = User.getAllUsers();
        synchronized (allUsers) {
            for (User user : allUsers)
                if (user.getNickname().equals(nickname)) return true;
            return false;
        }
    }

    public boolean doesUsernameAndPasswordMatch(String username, String password) {
        System.out.println(username + "  " + password);
        return User.getUserByUsername(username).getPassword().equals(password);
    }

    public String loginUser(String username, String password) {
        if (!isUsernameUsed(username)) return "username_password_dont_match";
        if (!doesUsernameAndPasswordMatch(username, password)) return "username_password_dont_match";
        for (String s : ServerMainController.getLoggedInUsers().keySet()) {
            if (ServerMainController.getLoggedInUsers().get(s).getUsername().equals(username))
                return "logged_in_before";
        }
        String token = UUID.randomUUID().toString();
        User user = User.getUserByUsername(username);
        System.out.println(user + " logged in ");
        ServerMainController.getLoggedInUsers().put(token, user);
        ScoreboardData.setOnline(user.getNickname());
        synchronized (ServerMainController.getOnlineCounter()) {
            int count = ServerMainController.getLoggedInUsers().keySet().size();
            for (String s : ServerMainController.getOnlineCounter().keySet()) {
                try {
                    ServerMainController.getOnlineCounter().get(s).writeUTF(String.valueOf(count));
                    ServerMainController.getOnlineCounter().get(s).flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        sendScoreboardData();
        return "success " + token;
    }
}