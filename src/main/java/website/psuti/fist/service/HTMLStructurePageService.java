package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.psuti.fist.dao.HTMLStructurePage.DAOHtmlStructurePage;
import website.psuti.fist.model.HTMLStructurePage;
import website.psuti.fist.model.TypeHtmlCode;

import java.util.List;

@Service
public class HTMLStructurePageService {

    @Autowired
    private DAOHtmlStructurePage daoHtmlStructurePage;

    public List<HTMLStructurePage> getAll() {
        return daoHtmlStructurePage.getAll();
    }

    public List<HTMLStructurePage> findHTMLCodeByType(TypeHtmlCode typeHtmlCode) {
        return daoHtmlStructurePage.findHTMLCodeByType(typeHtmlCode);
    }

    public long insert(HTMLStructurePage htmlStructurePage) {
        return daoHtmlStructurePage.insert(htmlStructurePage);
    }

    public void update(HTMLStructurePage htmlStructurePage) {
        daoHtmlStructurePage.update(htmlStructurePage);
    }

    public HTMLStructurePage findHTMLStructurePageById(Long id) {
        return daoHtmlStructurePage.findHTMLStructurePageById(id);
    }

    public int delete(Long id) {
        return daoHtmlStructurePage.delete(id);
    }
}
