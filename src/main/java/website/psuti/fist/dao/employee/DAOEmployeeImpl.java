package website.psuti.fist.dao.employee;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.Employee;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAOEmployeeImpl implements DAOEmployee {
    public final Logger logger = LoggerFactory.getLogger(DAOEmployeeImpl.class);

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
    public long insert(Employee employee) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        employee.setCharacteristic(employee.getCharacteristic().replace("\r\n","<br>").replace("\n","<br>"));
        employee.setCurator(employee.getCurator().replace("\r\n","<br>").replace("\n","<br>"));
        employee.setQualificationBriefly(employee.getQualificationBriefly().replace("\r\n","<br>").replace("\n","<br>"));
        employee.setQualificationDetailed(employee.getQualificationDetailed().replace("\r\n","<br>").replace("\n","<br>"));
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("Employee.add", employee);
            if (id == 1) {
                id = session.selectOne("Employee.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.EMPLOYEE);
                logger.info("Добавлен сотрудник - " + employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(Employee employee) {
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        employee.setCharacteristic(employee.getCharacteristic().replace("\r\n","<br>").replace("\n","<br>"));
        employee.setCurator(employee.getCurator().replace("\r\n","<br>").replace("\n","<br>"));
        employee.setQualificationBriefly(employee.getQualificationBriefly().replace("\r\n","<br>").replace("\n","<br>"));
        employee.setQualificationDetailed(employee.getQualificationDetailed().replace("\r\n","<br>").replace("\n","<br>"));
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("Employee.update", employee);
            if (id == 1) {
                MainPageObjectConstant.addCheck(NameTableBD.EMPLOYEE);
                logger.info("Обновлен сотрудник - " + employee);
            }
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
            check = session.delete("Employee.deleteById", id);
            if (check == 1) {
                MainPageObjectConstant.addCheck(NameTableBD.EMPLOYEE);
                logger.info("Удален сотрудник - " + id);
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<Employee> findEmployeesByNameDepartment(String nameDepartment) {
        List<Employee> employees;
        SqlSession session = factory.getFactory().openSession();
        try {
            employees = session.selectList("Employee.findEmployeesByNameDepartment", nameDepartment);
        } finally {
            session.close();
        }
        return employees;
    }
}
