package kernel;

import java.util.ArrayList;

public class Technique {
    private Integer id;
    private String name;
    private String img_path;
    private String description;
    private ArrayList<Piece> pieces;

    public Technique(Integer id, String name, String img_path, String description, ArrayList<Piece> pieces) {
        this.id = id;
        this.name = name;
        this.img_path = img_path;
        this.description = description;
        this.pieces = pieces;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
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
