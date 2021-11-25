package com.example.feathers.database.cloudinary.impl;

import com.cloudinary.Cloudinary;
import com.example.feathers.database.cloudinary.CloudinaryImage;
import com.example.feathers.database.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    CloudinaryImage cloudinaryImage = null;

    @Override
    public CloudinaryImage upload (MultipartFile file) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, file.getOriginalFilename());
        file.transferTo(tempFile);
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> upload = cloudinary.uploader()
                    .upload(tempFile, Map.of());

            String url = upload.getOrDefault(URL, "https://i0.wp.com/www.balloonintuscany.com/wp-content/uploads/2016/05/placeholder-3.png?fit=450%2C450&ssl=1");
            String publicId = upload.getOrDefault(PUBLIC_ID, "");

            cloudinaryImage = new CloudinaryImage().setPublicId(publicId).setUrl(url);
        } finally {
            tempFile.delete();
        }
        return cloudinaryImage;
    }

    @Override
    public boolean delete(String publicId) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
