package lk.estate.storage;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lk.estate.domain.Identified;

/** Small, single-process text store; each nonblank line is one JSON record. */
public abstract class FileRepository<T extends Identified> implements Repository<T> {
    private final Path file;
    private final ObjectMapper mapper;
    private final Class<T> type;

    protected FileRepository(Path file, ObjectMapper mapper, Class<T> type) {
        this.file = file.toAbsolutePath().normalize();
        this.mapper = mapper.copy().registerModule(new JavaTimeModule())
                .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.INDENT_OUTPUT);
        this.type = type;
        try {
            Files.createDirectories(this.file.getParent());
            if (Files.notExists(this.file)) {
                Files.createFile(this.file);
            }
        } catch (IOException exception) {
            throw new UncheckedIOException("Cannot initialize text storage.", exception);
        }
    }

    @Override
    public synchronized List<T> findAll() {
        try {
            List<T> items = new ArrayList<>();
            Set<String> ids = new HashSet<>();
            if (Files.notExists(file)) {
                return items;
            }
            int lineNumber = 0;
            for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                T item = mapper.readValue(line, type);
                if (item == null || item.getId() == null || !ids.add(item.getId())) {
                    throw new IOException("Missing or duplicate record ID on line " + lineNumber);
                }
                items.add(item);
            }
            return items;
        } catch (IOException exception) {
            throw new UncheckedIOException("Cannot read " + file.getFileName()
                    + "; inspect and repair the original data before retrying.", exception);
        }
    }

    @Override
    public synchronized Optional<T> findById(String id) {
        return findAll().stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public synchronized void save(T item) {
        List<T> items = findAll();
        items.removeIf(existing -> existing.getId().equals(item.getId()));
        items.add(item);
        replace(items);
    }

    @Override
    public synchronized void deleteById(String id) {
        List<T> items = findAll();
        items.removeIf(item -> item.getId().equals(id));
        replace(items);
    }

    private void replace(List<T> items) {
        Path temporary = null;
        try {
            List<String> lines = new ArrayList<>();
            for (T item : items) {
                // Declared base type preserves the JSON subtype discriminator.
                lines.add(mapper.writerFor(type).writeValueAsString(item));
            }
            temporary = Files.createTempFile(file.getParent(), file.getFileName().toString(), ".tmp");
            Files.write(temporary, lines, StandardCharsets.UTF_8);
            try {
                Files.move(temporary, file, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            throw new UncheckedIOException("Cannot write " + file.getFileName() + ".", exception);
        } finally {
            if (temporary != null) {
                try {
                    Files.deleteIfExists(temporary);
                } catch (IOException ignored) {
                    // A failed cleanup must not hide the original write failure.
                }
            }
        }
    }
}