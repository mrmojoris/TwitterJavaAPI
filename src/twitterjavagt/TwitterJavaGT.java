package twitterjavagt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
public class TwitterJavaGT {
    TwitterJavaGT() throws TwitterException, MalformedURLException {
        Twitter twitter;
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        String Token = new String();
        String TokenSecret = new String();
        File archivo = null;
        FileReader fileR = null;
        BufferedReader lecturaTeclado = null;
        try {
            archivo = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"auth_file.txt");
            fileR = new FileReader(archivo);
            lecturaTeclado = new BufferedReader(fileR);
            String linea = new String();
            int n = 1;
            while ((linea = lecturaTeclado.readLine()) != null) {
                if (n == 1) {
                    Token = linea;
                } else if (n == 2) {
                    TokenSecret = linea;
                }
                n++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fileR) {
                    fileR.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
            configBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey(new Tokens().OAuthConsumerKey)
                    .setOAuthConsumerSecret(new Tokens().OAuthConsumerSecret)
                .setOAuthAccessToken(Token)
                .setOAuthAccessTokenSecret(TokenSecret);
        twitter = new TwitterFactory(configBuilder.build()).getInstance();
        TwitterFrame ventana=new TwitterFrame(twitter);
    }
    public static void main(String ar[]) throws TwitterException, IOException, URISyntaxException {
        try{
            TwitterJavaGT twitter = new TwitterJavaGT();
        } catch(TwitterException ex){
            AutorizacionVentana auth=new AutorizacionVentana();
        }
    }
}