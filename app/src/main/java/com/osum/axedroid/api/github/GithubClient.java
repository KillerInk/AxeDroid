package com.osum.axedroid.api.github;

import java.io.IOException;

public interface GithubClient {
    String getLatestRelease() throws IOException;
}
