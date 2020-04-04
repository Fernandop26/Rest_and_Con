package kernel;

import java.util.ArrayList;

public class Author {
    private Integer id;
    private String name;
    private String img_path;
    private String biography;
    private ArrayList<Piece> pieces;
    private ArrayList<Museum> museums;

    // Constructor
    public Author(Integer id, String name, String img_path, String biography, ArrayList<Piece> pieces, ArrayList<Museum> museums) {
        this.id = id;
        this.name = name;
        this.img_path = img_path;
        this.biography = biography;
        this.pieces = pieces;
        this.museums = museums;
    }

    //Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg_path() {
        return img_path;
    }

    public String getBiography() {
        return biography;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public ArrayList<Museum> getMuseums() {
        return museums;
    }
}
