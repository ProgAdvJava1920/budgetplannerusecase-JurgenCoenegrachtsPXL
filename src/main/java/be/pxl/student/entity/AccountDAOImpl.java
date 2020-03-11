package be.pxl.student.entity;

import java.sql.*;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    public static final String SELECT_BY_ID = "SELECT * FROM Account WHERE id = ?";
    private String url;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public AccountDAOImpl(String url) {
        this.url = url;
    }

    @Override
    public Account Create(Account account) throws AccountException {
        throw new AccountException("Not yet implemented");
    }

    @Override
    public List<Account> getAll() throws AccountException {
        throw new AccountException("Not yet implemented");
    }

    @Override
    public Account getById(int id) throws AccountException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id); // indexen beginnen vanaf 1
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) { // is er één resultaat
                return new Account(
                        resultSet.getInt("id"),         // lees een int van een kolom met naam "id"
                        resultSet.getString("IBAN"),
                        resultSet.getString("name")
                );
            } else {
                throw new AccountNotFoundException(String.format("Account with id [%d]", id));
            }
        } catch (SQLException e) {
            // exception gooien met informatie en originele exception meegeven voor debuggen
            throw new AccountException(String.format("Exception while retrieveing account with id [%d]", id), e);
        }
    }

    @Override
    public Account Update(Account account) throws AccountException {
        throw new AccountException("Not yet implemented");
    }

    @Override
    public Account Delete(int id) throws AccountException {
        throw new AccountException("Not yet implemented");
    }
}
