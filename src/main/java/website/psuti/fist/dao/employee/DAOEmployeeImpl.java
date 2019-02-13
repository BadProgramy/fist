package website.psuti.fist.dao.employee;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.Employee;

import javax.sql.DataSource;
import java.util.List;

@Primary
@Repository
public class DAOEmployeeImpl implements DAOEmployee {

    @Autowired
    private Factory factory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Employee> getAll() {
        List<Employee> employees;
        SqlSession session = factory.getFactory().openSession();
        try {
            employees = session.selectList("Employee.selectAll");
        } finally {
            session.close();
        }
        return employees;
    }

    @Override
    public int insert(Employee employee) {
        return 0;
    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Employee> findEmployeesByNameDepartment(String nameDepartment) {
        List<Employee> employees;
        SqlSession session = factory.getFactory().openSession();
        try {
            employees = session.selectList("Employee.findEmployeesByNameDepartment", nameDepartment);
            MainPageObjectConstant.checkModelAndView.add(NameTableBD.EMPLOYEE);
        } finally {
            session.close();
        }
        return employees;
    }
}
