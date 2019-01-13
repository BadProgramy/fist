package website.psuti.fist.dao.checkingChangeTable;

import website.psuti.fist.model.CheckingChangeTable;

import java.util.List;

public interface DAOCheckingChangeTable {
    List<CheckingChangeTable> getAll();
    int insert (CheckingChangeTable checkingChangeTable);
    void update(CheckingChangeTable checkingChangeTable);
    void delete(int id);
}
