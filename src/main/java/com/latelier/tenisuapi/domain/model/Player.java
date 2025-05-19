package com.latelier.tenisuapi.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Player(
        int id,
        String firstname,
        String lastname,
        String shortname,
        Gender sex,
        Country country,
        String picture,
        PlayerData data
) {

    @JsonCreator
    public Player(
            @JsonProperty("id") int id,
            @JsonProperty("firstname") String firstname,
            @JsonProperty("lastname") String lastname,
            @JsonProperty("shortname") String shortname,
            @JsonProperty("sex") Gender sex,
            @JsonProperty("country") Country country,
            @JsonProperty("picture") String picture,
            @JsonProperty("data") PlayerData data
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.shortname = shortname;
        this.sex = sex;
        this.picture = picture;
        this.data = data;
        this.country = (country == null || country.code() == null)
                ? new Country(CountryCode.UNKNOWN.getLabel(), null)
                : country;
    }
    public record Country(String picture, CountryCode code) {
    }

    public record PlayerData(
            Integer rank,
            Integer points,
            Double weight,
            Double height,
            Integer age,
            List<Integer> last
    ) { }
}
