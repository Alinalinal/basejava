package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private SerializeStrategy serializeStrategy;

    protected PathStorage(String dir, SerializeStrategy serializeStrategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serializeStrategy = serializeStrategy;
    }

    public void setSerializeStrategy(SerializeStrategy serializeStrategy) {
        this.serializeStrategy = serializeStrategy;
    }

    @Override
    protected void doSave(Path file, Resume resume) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.toAbsolutePath(),
                    String.valueOf(file.getFileName()), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(String.valueOf(file))));
        } catch (IOException e) {
            throw new StorageException("File read error", String.valueOf(file.getFileName()), e);
        }
    }

    @Override
    protected void doUpdate(Path file, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(String.valueOf(file))));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("File delete error", String.valueOf(file.getFileName()));
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(String.valueOf(directory), uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected List<Resume> doCopyAll() {
        try {
            return Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        serializeStrategy.doWrite(resume, os);
    }

    protected Resume doRead(InputStream is) throws IOException {
        return serializeStrategy.doRead(is);
    }
}
