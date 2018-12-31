package website.psuti.fist.dao.bestStudent;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.BestStudent;

import java.util.List;

@Mapper
public interface DAOBestStudent {
    List<BestStudent> getAll();
    int insert (BestStudent bestStudent);
    void update(BestStudent bestStudent);
    void delete(int id);

    List<BestStudent> filledBestStudent();//заполненные студенты со всеми ссылками на картинки и тд. тп.
}
