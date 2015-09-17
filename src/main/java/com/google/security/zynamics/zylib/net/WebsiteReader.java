/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.google.security.zynamics.zylib.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class WebsiteReader {
  public static String read(final String url) throws IOException {
    // Create a URL for the desired page
    final URL url_ = new URL(url);

    final BufferedReader in = new BufferedReader(new InputStreamReader(url_.openStream()));

    final StringBuilder sb = new StringBuilder();

    String str;

    while ((str = in.readLine()) != null) {
      sb.append(str);
    }

    in.close();

    return sb.toString();
  }

  public static String sendPost(final String urlString, final String encodedData)
      throws IOException {
    final URL url = new URL(urlString);

    final URLConnection conn = url.openConnection();

    conn.setDoOutput(true);

    try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())) {
      wr.write(encodedData);
      wr.flush();
    }

    // Get the response
    final StringBuilder ret = new StringBuilder();
    String line;

    try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
      while ((line = rd.readLine()) != null) {
        ret.append(line);
      }
    }

    return ret.toString();
  }
}
