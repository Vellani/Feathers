package com.example.feathers.web.rest;

import com.example.feathers.database.service.LogService;
import com.example.feathers.util.ObjectConverter;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class GPXController {

    private final LogService logService;
    private final ObjectConverter objectConverter;

    public GPXController(LogService logService, ObjectConverter objectConverter) {
        this.logService = logService;
        this.objectConverter = objectConverter;
    }

    @PreAuthorize("@logServiceImpl.isOwnerOfLog(#id, #principal.name)")
    @GetMapping("/api/param")
    public ResponseEntity<byte[]> getGpxLogFile(@RequestParam(value = "gpx") Long id, Principal principal) {

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

    @GetMapping("/profile/gpx/all")
    @ResponseBody
    public List<String> getAllGpx(Principal principal) {
        List<String> list = new ArrayList<>();

        logService.findAllGpxFilesForUsername(principal.getName())
                .stream()
                .filter(e -> e.length > 0)
                .map(objectConverter::toPrimitives)
                .map(e -> new String(e, StandardCharsets.UTF_8))
                .forEach(list::add);

        return list;
    }

}
