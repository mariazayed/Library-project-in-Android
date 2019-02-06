package project.database.library.bibliothque;

/**
 * Created by Maria on 5/29/2017.
 */

public class Borrow_Object {
    private String bname ;
    private String bid ;

    public Borrow_Object(String bname,String bid) {
        this.bname = bname;
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
