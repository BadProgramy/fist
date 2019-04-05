package website.psuti.fist.dao.department;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.Department;

import java.util.List;

@Mapper
public interface DAODepartment {
    List<Department> getAll();
    long insert (Department department);
    void update(Department department);
    void delete(long id);
}
