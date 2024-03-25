package hei.school.soratra.service.soratra;

import hei.school.soratra.endpoint.rest.controller.soratra.dto.Soratra;
import hei.school.soratra.file.BucketComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoratraService {

    private final BucketComponent bucket;

    public Optional<String> saveSoratra(String id, byte[] soratra) throws IOException {
        String original = new String(soratra, StandardCharsets.UTF_8);
        String transformed = original.toUpperCase();

        File originalFile = File.createTempFile("original" + id, ".txt");
        File transformedFile = File.createTempFile("transformed" + id, ".txt");

        Files.writeString(
                Path.of(originalFile.getAbsolutePath()),
                original,
                StandardOpenOption.WRITE
        );

        Files.writeString(
                Path.of(transformedFile.getAbsolutePath()),
                transformed,
                StandardOpenOption.WRITE
        );

        bucket.upload(originalFile, "original" + id);
        bucket.upload(transformedFile, "transformed" + id);

        return Optional.of(id);
    }

    public Optional<Soratra> getSoratra(String id) {
        return Optional.of(new Soratra(
                bucket.presign("original" + id, Duration.ofMinutes(20)),
                bucket.presign("transformed" + id, Duration.ofMinutes(20))
        ));
    }

}