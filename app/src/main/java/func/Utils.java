package func;

/**
 * Created by kamlesh kumar garg on 04-06-2015.
 */

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Utils {

   public static boolean isNetworkAvailable(Activity a) {
       ConnectivityManager connectivityManager
               = (ConnectivityManager) a.getSystemService(a.CONNECTIVITY_SERVICE);
       NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
       return activeNetworkInfo != null && activeNetworkInfo.isConnected();
   }


   public static String encrypt(String Data) throws UnsupportedEncodingException {
       byte[] data = Data.getBytes("UTF-8");
       String base64 = Base64.encodeToString(data, Base64.DEFAULT);
       return base64;
   }
   public static String decryt(String d) throws UnsupportedEncodingException {
       byte[] data = Base64.decode(d, Base64.DEFAULT);
       String text = new String(data, "UTF-8");
       return text;
   }

}