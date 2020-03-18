package be.pxl.student.entity;

import java.sql.*;
import java.util.List;

public class AccountDAOImpl implements DAO<Account, AccountException> {
    public static final String CREATE = "";
    public static final String GET_ALL = "SELECT * FROM Account";
    public static final String SELECT_BY_ID = "SELECT * FROM Account WHERE id = ?";
    public static final String UPDATE = "";
    public static final String DLETE = "";
    private String url;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public AccountDAOImpl(String url) {
        this.url = url;
    }

    @Override
    public Account create(Account account) throws AccountException {
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
            // stack trace blijft dan volledig
            throw new AccountException(String.format("Exception while retrieveing account with id [%d]", id), e);
        }
    }

    @Override
    public Account update(Account account) throws AccountException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE)) {
            preparedStatement.setString(1, account.getIBAN());
        } catch (SQLException e) {

        }
        throw new AccountException("Not yet implemented");
    }

    @Override
    public Account delete(Account account) throws AccountException {
        throw new AccountException("Not yet implemented");
    }

}
