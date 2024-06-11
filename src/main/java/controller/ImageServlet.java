package controller;

import java.io.IOException;
import java.io.OutputStream;

import org.bson.types.ObjectId;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DBConnector;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageId = request.getParameter("id");

        if (imageId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing image ID");
            return;
        }

        try {
            ObjectId objectId = new ObjectId(imageId);
            GridFSBucket gridFSBucket = DBConnector.getGridFSBucket();
            GridFSFile gridFSFile = gridFSBucket.find(Filters.eq("_id", objectId)).first();

            if (gridFSFile == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                return;
            }

            response.setContentType("image/jpeg");
            response.setContentLength((int) gridFSFile.getLength());

            try (GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = downloadStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image ID format");
        }
    }
}
