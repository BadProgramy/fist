package website.psuti.fist.dao.checkingChangeTable;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.CheckingChangeTable;

import java.util.List;

public class DAOCheckingChangeTableImpl implements DAOCheckingChangeTable {

    @Autowired
    private Factory factory;

    @Override
    public List<CheckingChangeTable> getAll() {
        List<CheckingChangeTable> checkingChangeTables;
        SqlSession session = factory.getFactory().openSession();
        try {
            checkingChangeTables = session.selectList("CheckingChangeTable.selectAll");
        } finally {
            session.close();
        }
        return checkingChangeTables;
    }

    @Override
    public int insert(CheckingChangeTable checkingChangeTable) {
        return 0;
    }

    @Override
    public void update(CheckingChangeTable checkingChangeTable) {

    }

    @Override
    public void delete(int id) {

    }
}
