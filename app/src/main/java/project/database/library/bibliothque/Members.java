package project.database.library.bibliothque;

/**
 * Created by Maria on 25-May-17.
 */

public class Members {
    private String member_name ;
    private byte[] member_image ;
    private String id ;

    public Members(String member_name, byte[] member_image , String id) {
        this.member_name = member_name;
        this.member_image = member_image;
        this.id = id ;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public byte[] getMember_image() {
        return member_image;
    }

    public void setMember_image(byte[] member_image) {
        this.member_image = member_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
