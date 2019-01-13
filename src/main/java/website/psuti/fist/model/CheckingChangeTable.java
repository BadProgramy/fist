package website.psuti.fist.model;

import website.psuti.fist.constant.NameTableBD;

import javax.persistence.*;

@Entity
public class CheckingChangeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private NameTableBD nameTableBD;
}
