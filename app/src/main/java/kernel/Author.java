package kernel;

import java.util.ArrayList;

public class Author {
    private Integer id;
    private String name;
    private String imgPath;
    private String biography;
    private ArrayList<Piece> pieces;
    private ArrayList<Museum> museums;

    // Constructor
    public Author(Integer id, String name, String imgPath, String biography, ArrayList<Piece> pieces, ArrayList<Museum> museums) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
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

    public String getImgPath() {
        return imgPath;
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
