package website.psuti.fist.dao.department;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.Department;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAODepartmentImpl implements DAODepartment {

    @Autowired
    private Factory factory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Department> getAll() {
        List<Department> departments;
        SqlSession session = factory.getFactory().openSession();
        try {
            departments = session.selectList("Department.selectAll");
        } finally {
            session.close();
        }
        return departments;
    }

    @Override
    public long insert(Department department) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("Department.add", department);
            if (id == 1) {
                id = session.selectOne("Department.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.DEPARTMENT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(Department department) {
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("Department.update", department);
            if (id == 1) MainPageObjectConstant.addCheck(NameTableBD.DEPARTMENT);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(long id) {
        int check = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            check = session.delete("Department.deleteById", id);
            if (check == 1) MainPageObjectConstant.addCheck(NameTableBD.DEPARTMENT);
        } finally {
            session.close();
        }
        //return check;
    }
}
