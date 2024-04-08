package crud;

import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_crud";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Create
            Employee employee1 = new Employee("Abhishek", 23, 50000);
            insertEmployee(connection, employee1);

            // Read
            Employee employee = findEmployeeById(connection, 6);
            System.out.println("Retrieved employee: " + employee);

            // Update
            employee.setSalary(60000);
            updateEmployee(connection, employee);

            // Delete
            deleteEmployee(connection, employee.getId());

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertEmployee(Connection connection, Employee employee) throws SQLException {
        String query = "INSERT INTO employees (name, age, salary) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setInt(2, employee.getAge());
            preparedStatement.setDouble(3, employee.getSalary());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        }
    }

    private static Employee findEmployeeById(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEmployee(resultSet);
                }
            }
        }
        return null;
    }

    private static void updateEmployee(Connection connection, Employee employee) throws SQLException {
        String query = "UPDATE employees SET name = ?, age = ?, salary = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setInt(2, employee.getAge());
            preparedStatement.setDouble(3, employee.getSalary());
            preparedStatement.setInt(4, employee.getId());
            preparedStatement.executeUpdate();
        }
    }

    private static void deleteEmployee(Connection connection, int id) throws SQLException {
        String query = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private static Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setName(resultSet.getString("name"));
        employee.setAge(resultSet.getInt("age"));
        employee.setSalary(resultSet.getDouble("salary"));
        return employee;
    }
}
