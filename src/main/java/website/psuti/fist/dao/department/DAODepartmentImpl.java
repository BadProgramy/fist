package website.psuti.fist.dao.department;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.Department;

import javax.sql.DataSource;
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
    public int insert(Department department) {
        return 0;
    }

    @Override
    public void update(Department department) {

    }

    @Override
    public void delete(int id) {

    }
}
