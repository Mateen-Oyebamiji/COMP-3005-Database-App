//import java.beans.Statement;
import java.sql.*;
public class app3 {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/Student Data" ;
        String username = "postgres";
        String pwd = "Morth2003%";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, pwd);
            System.out.println("connected to PostgreSQL");
            //updateStudentEmail(connection, 3, "jimmmy.beam@example.com");
            //addStudent(connection,"Abdul","Mateen" , "TZ@email.v", Date.valueOf("2023-12-03"));
            //deleteStudent(connection, 6);
            getAllStudents(connection);
            //System.out.println(isEmail(connection, "john.doe@example.com"));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Errrorrrrr");
            e.printStackTrace();
        }
   

    }

    //A helper function to check if emails are present in the database
    //This function uses Statements and checks through the email of every student, returns true if the email is foun d and false if not
    private static boolean isEmail(Connection connection, String email){
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM students";
            ResultSet rs = statement.executeQuery(SQL);
            while(rs.next()){
                String studentEmail = rs.getString("email");
                if(email.equals(studentEmail)){
                    System.out.println("This Student already in Database");
                    return true;
                }
            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("Failed Check if email is in Database");
            e.printStackTrace();
        }
        return false;

    }

    //Function to retieve all students from the database
    //Function uses Statement and a while loop to extract the information of every student and prints it out in a specified format
    private static void getAllStudents(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM students";
            ResultSet rs = statement.executeQuery(SQL);
            while(rs.next()){
                int id = rs.getInt("student_id");
                String name = rs.getString("first_name");
                String l_name = rs.getString("last_name");
                String email = rs.getString("email");
                Date e_date = rs.getDate("enrollment_date");
                System.out.println( "Student Id: "+id+", First Name: "+name + ", Last Name: "+l_name+", Email: "+email+", Enrollment date: "+e_date);
            }
        } catch(SQLException e) {
            // TODO: handle exception
            System.out.println("Failed to get all students");
            e.printStackTrace();

        }
        
    }
    //Function to add a student to the database
    //Function uses Prepared Statement to send an insert Query to the SQL Database, adding new a new student
    private static void addStudent(Connection connection, String firstName, String lastName, String email, Date enrollmentDate) {
        String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSQL)){
            if(!isEmail(connection, email)){
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setDate(4, enrollmentDate);
            ps.executeUpdate();
            System.out.println("Data Insert Successful");
            } 
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("Failed to insert student");
            e.printStackTrace();
        }
    }

    //Function to update a Students Email in the Database
    //Function uses PreparedStatement to send an Update Query to the SQL database
    private static void updateStudentEmail(Connection connection,int studentId, String newEmail) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE students SET email = ? WHERE student_id = ?")){
            ps.setString(1, newEmail);
            ps.setInt(2, studentId);
            ps.executeUpdate();
            System.out.println("Student Email updated successfully");


        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("Failed to update student email");
            e.printStackTrace();
        }
    }

    //Function to Delete a student from the Database
    //Function uses PreparedStatement to send a delete query to the Database
    private static void deleteStudent(Connection connection,int studentId) {
    try (PreparedStatement ps = connection.prepareStatement("DELETE FROM students WHERE student_id = ?")){
        ps.setInt(1, studentId);
        ps.executeUpdate();
        System.out.println("Student was Deleted Sucessfully");
    } catch (SQLException e) {
        // TODO: handle exception
        System.out.println("Failed to delete student");
        e.printStackTrace();
    }
}
}
     
 
