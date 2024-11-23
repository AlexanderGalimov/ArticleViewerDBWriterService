package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.component.pdf;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class PdfSaver {

    public InputStream openPdfStream(String pdfLink) throws IOException {
        URL url = new URL(pdfLink);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("User-Agent", "my-agent");
        httpURLConnection.connect();
        return httpURLConnection.getInputStream();
    }

}