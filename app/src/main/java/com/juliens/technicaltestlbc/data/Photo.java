package com.juliens.technicaltestlbc.data;

/**
 * Created by juliens on 12/10/2017.
 *
 * Model class for a photo
 */
public final class Photo {
    private int id;
    private int albumId;
    private String title;
    private String url;
    private String thumbnailUrl;

    public Photo(int id, int albumId, String title, String url, String thumbnailUrl) {
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public int getAlbumId() {
        return albumId;
    }
}
