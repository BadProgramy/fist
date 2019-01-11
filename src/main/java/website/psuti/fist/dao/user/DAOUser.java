package website.psuti.fist.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import website.psuti.fist.model.User;

@Mapper
@Repository
public interface DAOUser extends CrudRepository<User, Long> {
}
