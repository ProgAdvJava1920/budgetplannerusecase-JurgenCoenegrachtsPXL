package be.pxl.student.entity;

import java.util.List;

public interface AccountDAO {
    // CRUD volgen
    // Create
    Account Create(Account account) throws AccountException;

    // Read (select)
    List<Account> getAll() throws AccountException;
    Account getById(int id) throws AccountException;

    // Update
    // beter om object mee te geven dan elk veld apart
    // REST API: geeft aangepaste account terug mee
    Account Update(Account account) throws AccountException;

    // Delete
    Account Delete(int id) throws AccountException;
}
