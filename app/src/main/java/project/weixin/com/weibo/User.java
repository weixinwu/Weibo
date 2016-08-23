package project.weixin.com.weibo;

/**
 * Created by wuwei on 8/21/2016.
 */
public class User {
    private int id ;
    private String screen_name;
    private String name;
    private int province;
    private int city;
    private String location;
    private String description;
    private String profile_pic_url;
    private String profile_url;
    private String gender;
    private int follower_count;
    private int friends_count;
    private int favorite_count;
    private boolean geo_enabled;
    private boolean verified;
    private String avatar_mid;
    private String avatar_hd;
    private boolean follow_me;
    private int online_status;
    private int bi_followers;

    public User(int id, String screen_name, String name, int province, int city, String location, String description, String profile_pic_url, String profile_url, String gender, int follower_count, int friends_count, int favorite_count, boolean geo_enabled, boolean verified, String avatar_mid, String avatar_hd, boolean follow_me, int online_status, int bi_followers) {
        this.id = id;
        this.screen_name = screen_name;
        this.name = name;
        this.province = province;
        this.city = city;
        this.location = location;
        this.description = description;
        this.profile_pic_url = profile_pic_url;
        this.profile_url = profile_url;
        this.gender = gender;
        this.follower_count = follower_count;
        this.friends_count = friends_count;
        this.favorite_count = favorite_count;
        this.geo_enabled = geo_enabled;
        this.verified = verified;
        this.avatar_mid = avatar_mid;
        this.avatar_hd = avatar_hd;
        this.follow_me = follow_me;
        this.online_status = online_status;
        this.bi_followers = bi_followers;
    }

    public int getId() {
        return id;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getName() {
        return name;
    }

    public int getProvince() {
        return province;
    }

    public int getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public String getGender() {
        return gender;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getAvatar_mid() {
        return avatar_mid;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public boolean isFollow_me() {
        return follow_me;
    }

    public int getOnline_status() {
        return online_status;
    }

    public int getBi_followers() {
        return bi_followers;
    }
}
