package website.psuti.fist.dao.HTMLStructurePage;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.HTMLStructurePage;

import java.util.List;

@Mapper
public interface DAOHtmlStructurePage {
    List<HTMLStructurePage> getAll();
    long insert (HTMLStructurePage htmlStructurePage);
    void update(HTMLStructurePage htmlStructurePage);
    int delete(long id);

    HTMLStructurePage findHTMLStructurePageById(Long id);
}
