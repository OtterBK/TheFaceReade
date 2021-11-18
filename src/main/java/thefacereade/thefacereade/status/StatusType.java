package thefacereade.thefacereade.status;

public enum StatusType {


    SLAVE(0, "노비"),
    CIVIL(1, "평민"),
    TRADER(2,"상인"),
    YANGBAN(3, "양반"),
    KING(4, "왕"),
    KING_SLAVE(99, "왕의 노비");

    public final int index;
    public final String name;

    StatusType(int index, String name){
        this.index = index;
        this.name = name;
    }

}
