package ba.unsa.etf.rpr.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AppDAO {
    private static Connection conn;
    private PreparedStatement getAllUsersStmt, setNewIdStmt, addNewUserStmt, getUserStmt, deleteAllUsersStmt,
            getAllQuotesStmt, setNewIdQuoteStmt, addNewQuoteStmt, getQuoteStmt, deleteAllQuotesStmt,
            getAllTasksStmt, setNewIdTaskStmt, addNewTaskStmt, getTaskStmt, deleteAllTasksFromListStmt, deleteAllTasksStmt,
            getAllListsStmt, getAllListsForUserStmt, addNewListForUserStmt, getListStmt, deleteAllListsStmt, deleteListForUserStmt;


    private static AppDAO instance=null;
    private final LocalDate currentDate=LocalDate.now();

    private AppDAO()  {
        //baza ce se napraviti u home folderu korisnika
        String url = "jdbc:sqlite:" + System.getProperty("user.home") + "\\.todoApp\\todoDatabase.db";

        //String url="jdbc:sqlite:todoDatabase.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            getAllUsersStmt = conn.prepareStatement("SELECT *FROM users");


        } catch (SQLException e) {
            generateDatabase();
            try {
                getAllUsersStmt = conn.prepareStatement("SELECT *FROM users");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            setNewIdStmt=conn.prepareStatement("SELECT MAX(id)+1 FROM users");
            addNewUserStmt=conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?)");
            getUserStmt=conn.prepareStatement("SELECT *FROM users WHERE username=?");
            deleteAllUsersStmt =conn.prepareStatement("DELETE FROM users");

            //quotes
            getAllQuotesStmt=conn.prepareStatement("SELECT *FROM quotes");
            setNewIdQuoteStmt=conn.prepareStatement("SELECT MAX(id)+1 FROM quotes");
            addNewQuoteStmt =conn.prepareStatement("INSERT INTO quotes VALUES(?,?,?)");
            getQuoteStmt =conn.prepareStatement("SELECT *FROM quotes WHERE id=?");
            deleteAllQuotesStmt =conn.prepareStatement("DELETE FROM quotes");

            //tasks
            getAllTasksStmt=conn.prepareStatement("SELECT *FROM tasks");
            setNewIdTaskStmt=conn.prepareStatement("SELECT MAX(id)+1 FROM tasks");
            addNewTaskStmt =conn.prepareStatement("INSERT INTO tasks VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            deleteAllTasksFromListStmt =conn.prepareStatement("DELETE FROM tasks WHERE username=? AND list_name=?");
            deleteAllTasksStmt =conn.prepareStatement("DELETE FROM tasks");

            //lists
            getAllListsStmt=conn.prepareStatement("SELECT *FROM lists");
            getAllListsForUserStmt=conn.prepareStatement("SELECT *FROM lists WHERE username=?");
            addNewListForUserStmt=conn.prepareStatement("INSERT INTO lists VALUES (?,?)");
            deleteAllListsStmt=conn.prepareStatement("DELETE from lists");
            deleteListForUserStmt=conn.prepareStatement("DELETE from lists WHERE username=? AND list_name=?");


        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }

    public static Connection getConn() {
        return conn;
    }

    private static void generateDatabase() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("todoDatabase.db.sql"));
            String sqlStatement = "";
            while (input.hasNext()) {
                sqlStatement += input.nextLine();
                if ( sqlStatement.charAt( sqlStatement.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlStatement);
                        sqlStatement = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static AppDAO getInstance()  {
        if (instance == null) instance = new AppDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
        return u;
    }

    public ArrayList<User> users(){
        ArrayList<User> result = new ArrayList();
        try {
            ResultSet rs = getAllUsersStmt.executeQuery();
            while (rs.next()) {
                User user = getUserFromResultSet(rs);
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addUser(User user){

        try {
            ResultSet rs  = setNewIdStmt.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addNewUserStmt.setInt(1,id);
            addNewUserStmt.setString(2,user.getFirstName());
            addNewUserStmt.setString(3,user.getLastName());
            addNewUserStmt.setString(4,user.getUsername());
            addNewUserStmt.setString(5,user.getPassword());
            addNewUserStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }

    public User getUser(String username){
        try {
            getUserStmt.setString(1, username);
            ResultSet rs = getUserStmt.executeQuery();
            if (!rs.next()) return null;
            return getUserFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAllUsers(){
        try {
            deleteAllUsersStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //quotes
    private Quote getQuoteFromResultSet(ResultSet rs) throws SQLException {
        Quote q = new Quote(rs.getInt(1), rs.getString(2), rs.getString(3));
        return q;
    }

    public ArrayList<Quote> quotes(){
        ArrayList<Quote> result = new ArrayList();
        try {
            ResultSet rs = getAllQuotesStmt.executeQuery();
            while (rs.next()) {
                Quote quote = getQuoteFromResultSet(rs);
                result.add(quote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addQuote(Quote quote){
        ResultSet rs = null;
        try {
            rs = setNewIdQuoteStmt.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addNewQuoteStmt.setInt(1,id);
            addNewQuoteStmt.setString(2,quote.getContent());
            addNewQuoteStmt.setString(3,quote.getAuthor());
            addNewQuoteStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }

    public Quote getQuote(Integer id){
        try {
            getQuoteStmt.setInt(1,id);
            ResultSet rs = getQuoteStmt.executeQuery();
            if (!rs.next()) return null;
            return getQuoteFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAllQuotes(){
        try {
            deleteAllQuotesStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //tasks
    private Task getTaskFromResultSet(ResultSet rs) throws SQLException {
        Task t = new Task();
        t.setId(rs.getInt(1));
        t.setUsername(rs.getString(2));
        t.setTaskName(rs.getString(3));
        t.setStartYear(rs.getInt(4));
        t.setStartMonth(rs.getInt(5));
        t.setStartDay(rs.getInt(6));
        t.setStartHour(rs.getInt(7));
        t.setStartMin(rs.getInt(8));
        t.setEndYear(rs.getInt(9));
        t.setEndMonth(rs.getInt(10));
        t.setEndDay(rs.getInt(11));
        t.setEndHour(rs.getInt(12));
        t.setEndMin(rs.getInt(13));
        t.setNote(rs.getString(14));
        t.setReminder(rs.getBoolean(15));
        t.setReminderDigit(rs.getInt(16));
        t.setReminderPeriod(rs.getString(17));
        t.setAlertNotification(rs.getBoolean(18));
        t.setAlertEmail(rs.getBoolean(19));
        t.setListName(rs.getString(20));
        return t;
    }

    public ArrayList<Task> tasks(){
        ArrayList<Task> result = new ArrayList();
        try {
            ResultSet rs = getAllTasksStmt.executeQuery();
            while (rs.next()) {
                Task task = getTaskFromResultSet(rs);
                result.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Task> getTasksForToday(String username){
        ArrayList<Task> result=new ArrayList<>();
        for(Task t: this.tasks()){
            if(t.getUsername().equals(username) && t.getStartYear()==currentDate.getYear() &&
            t.getStartMonth()==currentDate.getMonthValue() && t.getStartDay()==currentDate.getDayOfMonth()){
                result.add(t);
            }
        }
        return result;
    }

    public void addTask(Task task){

        try {
            ResultSet rs  = setNewIdTaskStmt.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addNewTaskStmt.setInt(1,id);
            addNewTaskStmt.setString(2,task.getUsername());
            addNewTaskStmt.setString(3,task.getTaskName());
            addNewTaskStmt.setInt(4,task.getStartYear());
            addNewTaskStmt.setInt(5,task.getStartMonth());
            addNewTaskStmt.setInt(6,task.getStartDay());
            addNewTaskStmt.setInt(7,task.getStartHour());
            addNewTaskStmt.setInt(8,task.getStartMin());
            addNewTaskStmt.setInt(9,task.getEndYear());
            addNewTaskStmt.setInt(10,task.getEndMonth());
            addNewTaskStmt.setInt(11,task.getEndDay());
            addNewTaskStmt.setInt(12,task.getEndHour());
            addNewTaskStmt.setInt(13,task.getEndMin());
            addNewTaskStmt.setString(14,task.getNote());
            addNewTaskStmt.setBoolean(15,task.isReminder());
            addNewTaskStmt.setInt(16, task.getReminderDigit());
            addNewTaskStmt.setString(17,task.getReminderPeriod());
            addNewTaskStmt.setBoolean(18, task.isAlertNotification());
            addNewTaskStmt.setBoolean(19,task.isAlertEmail());
            addNewTaskStmt.setString(20,task.getListName());
            addNewTaskStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }

    public ArrayList<Task> getAllTasksByListName(String username, String listName){
        ArrayList<Task>result=new ArrayList<>();
        for(Task t: this.tasks()){
            if(t.getUsername().equals(username) && t.getListName().equals(listName)){
                result.add(t);
            }
        }
        return result;


    }

    public void deleteTasksFromList(String username,String listName){
        try {
            deleteAllTasksFromListStmt.setString(1,username);
            deleteAllTasksFromListStmt.setString(2,listName);
            deleteAllTasksFromListStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteAllTasks(){
        try {
            deleteAllTasksStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //lists
    private List getListFromResultSet(ResultSet rs) throws SQLException {
        List l = new List( rs.getString(1), rs.getString(2));
        return l;
    }

    public ArrayList<List> lists(){
        ArrayList<List> result = new ArrayList();
        try {
            ResultSet rs = getAllListsStmt.executeQuery();
            while (rs.next()) {
                List list = getListFromResultSet(rs);
                result.add(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<List> lists(User u){
        ArrayList<List> result = new ArrayList();
        try {
            getAllListsForUserStmt.setString(1,u.getUsername());
            ResultSet rs = getAllListsForUserStmt.executeQuery();
            while (rs.next()) {
                List list = getListFromResultSet(rs);
                result.add(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addList(String username, String listName){
        try {
            addNewListForUserStmt.setString(1,username);
            addNewListForUserStmt.setString(2,listName);
            addNewListForUserStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }



    public void deleteAllLists(){
        try {
            deleteAllListsStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteList(String username, String listName){
        try {
            deleteListForUserStmt.setString(1,username);
            deleteListForUserStmt.setString(2,listName);
            deleteListForUserStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
