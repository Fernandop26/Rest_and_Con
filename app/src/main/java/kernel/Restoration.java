package kernel;

import java.util.ArrayList;
import java.util.Date;

public class Restoration {
    private Integer id;
    private String img_path;
    private Date date;
    private String alt_map;
    private String procedures;
    private String restorer;

    public Restoration(Integer id, String img_path, Date date, String alt_map, String procedures, String restorer) {
        this.id = id;
        this.img_path = img_path;
        this.date = date;
        this.alt_map = alt_map;
        this.procedures = procedures;
        this.restorer = restorer;
    }

    public Integer getId() {
        return id;
    }

    public String getImg_path() {
        return img_path;
    }

    public Date getDate() {
        return date;
    }

    public String getAlt_map() {
        return alt_map;
    }

    public String getProcedures() {
        return procedures;
    }

    public String getRestorer() {
        return restorer;
    }
}
