package com.example.feathers.web;

import com.example.feathers.database.service.LogService;
import com.example.feathers.util.ObjectConverter;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GPXController {

    private final LogService logService;
    private final ObjectConverter objectConverter;

    public GPXController(LogService logService, ObjectConverter objectConverter) {
        this.logService = logService;
        this.objectConverter = objectConverter;
    }

    @GetMapping("/api/param")
    public ResponseEntity<byte[]> getGpxLogFile(@RequestParam(value = "gpx") Long id) {

        byte[] bytes = objectConverter.toPrimitives(logService.findSpecificGPXLog(id));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .contentLength(bytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("gpxFile.gpx")
                                .build().toString())
                .body(bytes);
    }



}
