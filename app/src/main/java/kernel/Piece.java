package kernel;

import java.util.ArrayList;
import java.util.Date;

public class Piece {
    private Integer id;
    private String name;
    private Date date;
    private String img_path;
    private String size;
    private ArrayList<Restoration> restorations;

    public Piece(Integer id, String name, Date date, String img_path, String size, ArrayList<Restoration> restorations) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.img_path = img_path;
        this.size = size;
        this.restorations = restorations;
    }

    public Piece() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getImg_path() {
        return img_path;
    }

    public String getSize() {
        return size;
    }

    public ArrayList<Restoration> getRestorations() {
        return restorations;
    }
}
