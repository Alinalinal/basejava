package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeCollection(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(resume.getSections().entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                AbstractSection section = entry.getValue();
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(((ListSection) section).getContent(), dos,
                                dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(((OrganizationSection) section).getContent(), dos,
                                organization -> {
                                    Link link = organization.getHomePage();
                                    dos.writeUTF(link.getName());
                                    String url = link.getUrl();
                                    dos.writeUTF(url == null ? "null" : url);
                                    writeCollection(organization.getPositions(), dos, position -> {
                                        DateUtil.writeAsData(dos, position.getStartDate());
                                        DateUtil.writeAsData(dos, position.getEndDate());
                                        dos.writeUTF(position.getTitle());
                                        String description = position.getDescription();
                                        dos.writeUTF(description == null ? "null" : description);
                                    });
                                });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(readCollection(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType, new OrganizationSection(readCollection(dis, () ->
                                new Organization(new Link(dis.readUTF(), dis.readUTF()), readCollection(dis, () ->
                                        new Organization.Position(DateUtil.readDataDate(dis),
                                                DateUtil.readDataDate(dis), dis.readUTF(), dis.readUTF()))))));
                        break;
                }
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface WriteDataConsumer<T> {
        void writeData(T t) throws IOException;
    }

    private static <T> void writeCollection(Collection<T> collection, DataOutputStream dos,
                                            WriteDataConsumer<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.writeData(t);
        }
    }

    @FunctionalInterface
    private interface ElementReader<T> {
        T readDataElement() throws IOException;
    }

    private static <T> List<T> readCollection(DataInputStream dis, ElementReader<T> elementReader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(elementReader.readDataElement());
        }
        return list;
    }

    @FunctionalInterface
    private interface ReadDataConsumer {
        void readData() throws IOException;
    }

    private static void readWithException(DataInputStream dis, ReadDataConsumer consumer) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            consumer.readData();
        }
    }
}
