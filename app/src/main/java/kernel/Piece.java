package kernel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Piece{
    private Integer id;
    private String name;
    private String author;
    private Date date;
    private String dateString;

    private String technique;
    private String imgPath;
    private String size;
    private String museum;
    private ArrayList<Restoration> restorations;
    private SimpleDateFormat df2 = new SimpleDateFormat("yyyy");


    public Piece(Integer id, String name, String author, Date date, String technique, String imgPath, String size, String museum, ArrayList<Restoration> restorations) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.date = date;
        this.technique = technique;
        this.imgPath = imgPath;
        this.size = size;
        this.museum = museum;
        this.restorations = restorations;
    }
    ///////////////////////////// CONSTRUCTOR TEST //////////////////////////
    public Piece (Integer id, String name, String img_path){
        this.id = id;
        this.name = name;
        this.imgPath = img_path;
    }

    public Piece (Integer id, String name, String img_path,String date){
        this.id = id;
        this.name = name;
        this.imgPath = img_path;
        this.dateString=date;
    }

    ////////////////////////////////////////////////////////////////////////
    public Piece() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() { return name; }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }
    public String getDateToString() {
        return dateString;
    }

    public String getTechnique() {
        return technique;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getSize() {
        return size;
    }

    public String getMuseum() {
        return museum;
    }

    public ArrayList<Restoration> getRestorations() {
        return restorations;
    }

 /*    @Override
  public int compareTo(Object o) {
        Piece piece2= (Piece)o;
        ///Ascendente
        return name.compareTo(piece2.getName());

        //Descendente
        //return piece2.getName().compareTo(name);
    }*/

   /* @Override
    public String toString() {
        return name;
    }*/
}
