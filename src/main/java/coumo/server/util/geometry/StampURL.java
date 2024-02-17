package coumo.server.util.geometry;

public class StampURL {
    public static String getURL(String type){
        return "https://coumo-s3.s3.ap-northeast-2.amazonaws.com/" + type + ".png";
    }
}
