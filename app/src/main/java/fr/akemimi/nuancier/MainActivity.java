package fr.akemimi.nuancier;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.ClipData;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.ValueCallback;
import androidx.core.content.FileProvider;
import java.io.File;
public class MainActivity extends Activity {
  private ValueCallback<Uri[]> fileCallback;
  private Uri captureUri;
  private File captureFile;
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    WebView view = new WebView(this);
    view.getSettings().setJavaScriptEnabled(true);
    view.getSettings().setDomStorageEnabled(true);
    view.getSettings().setAllowFileAccess(false);
    view.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
    view.setWebChromeClient(new WebChromeClient() {
      @Override public boolean onShowFileChooser(WebView web, ValueCallback<Uri[]> callback, FileChooserParams params) {
        if (fileCallback != null) fileCallback.onReceiveValue(null);
        fileCallback = callback;
        captureUri = null;
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        gallery.addCategory(Intent.CATEGORY_OPENABLE);
        gallery.setType("image/*");
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
          if (params.isCaptureEnabled() && camera.resolveActivity(getPackageManager()) != null) {
            File directory = new File(getCacheDir(), "photos");
            directory.mkdirs();
            // A single temporary photo is kept; new captures replace it.
            captureFile = new File(directory, "capture.jpg");
            if (captureFile.exists()) captureFile.delete();
            captureUri = FileProvider.getUriForFile(MainActivity.this, getPackageName()+".photos", captureFile);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, captureUri);
            camera.setClipData(ClipData.newRawUri("photo", captureUri));
            camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(camera, 7);
          } else startActivityForResult(gallery, 7);
        } catch (Exception error) {
          fileCallback.onReceiveValue(null); fileCallback = null;
        }
        return true;
      }
    });
    setContentView(view);
    view.loadUrl("file:///android_asset/index.html");
  }
  @Override protected void onActivityResult(int request, int result, Intent data) {
    super.onActivityResult(request, result, data);
    if (request != 7 || fileCallback == null) return;
    Uri[] selected = null;
    if (result == RESULT_OK) {
      if (captureUri != null && captureFile != null && captureFile.length() > 0) selected = new Uri[]{captureUri};
      else if (data != null && data.getData() != null && "content".equals(data.getData().getScheme())) {
        Uri uri = data.getData();
        String type = getContentResolver().getType(uri);
        if (type != null && type.startsWith("image/")) selected = new Uri[]{uri};
      }
    }
    fileCallback.onReceiveValue(selected); fileCallback = null; captureUri = null;
  }
  @Override protected void onDestroy() {
    if (fileCallback != null) { fileCallback.onReceiveValue(null); fileCallback = null; }
    super.onDestroy();
  }
}
