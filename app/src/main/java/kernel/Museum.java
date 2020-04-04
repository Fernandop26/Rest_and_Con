package kernel;

import java.util.ArrayList;

public class Museum {
    private Integer id;
    private String name;
    private String location;
    private String img_path;
    private String description;
    private ArrayList<Piece> pieces;

    public Museum(Integer id, String name, String location, String img_path, String description, ArrayList<Piece> pieces) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.img_path = img_path;
        this.description = description;
        this.pieces = pieces;
    }

    public Museum() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getImg_path() {
        return img_path;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
}
