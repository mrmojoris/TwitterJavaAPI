package twitterjavagt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class Autorizacion {
    Autorizacion() throws IOException, TwitterException {
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
            configBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey(new Tokens().OAuthConsumerKey)
                    .setOAuthConsumerSecret(new Tokens().OAuthConsumerSecret);
        Twitter OAuthTwitter = new TwitterFactory(configBuilder.build()).getInstance();
        RequestToken requestToken = null;
        AccessToken accessToken = null;
        String url = null;
        do {
            try {
                requestToken = OAuthTwitter.getOAuthRequestToken();
                System.out.println("Request Tokens obtenidos con éxito.");
                url = requestToken.getAuthorizationURL();
            } catch (TwitterException ex) {
                Logger.getLogger(TwitterJavaGT.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader lectorTeclado = new BufferedReader(new InputStreamReader(System.in));
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("firefox " + url);
            } catch (Exception e) {
            }
            System.out.print("Introduce el PIN del navegador y pulsa intro.\n\n PIN: ");
            String pin = lectorTeclado.readLine();
            if (pin.length() > 0) {
                accessToken = OAuthTwitter.getOAuthAccessToken(requestToken, pin);
            } else {
                accessToken = OAuthTwitter.getOAuthAccessToken(requestToken);
            }
        } while (accessToken == null);
        System.out.println("\n\nAccess Tokens obtenidos con éxito.");
        FileOutputStream fileOS = null;
        File file;
        String content = accessToken.getToken() + "\n" + accessToken.getTokenSecret();
        try {
            file = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"auth_file.txt");
            fileOS = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentInBytes = content.getBytes();
            fileOS.write(contentInBytes);
            fileOS.flush();
            fileOS.close();
            System.out.println("Escritura realizada con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOS != null) {
                    fileOS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            TwitterJavaGT cliente=new TwitterJavaGT();
        }
    }
}