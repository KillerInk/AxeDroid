package com.osum.axedroid.api.axeos.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubReleaseObj {
    public String tag_name;
}
