package org.spring.codingStory.movieApi.movie.data;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private String kofic;
    private String tmdb;

    public String getKofic() {
        return kofic;
    }

    public void setKofic(String kofic) {
        this.kofic = kofic;
    }

    public String getTmdb() {
        return tmdb;
    }

    public void setTmdb(String tmdb) {
        this.tmdb = tmdb;
    }
}

