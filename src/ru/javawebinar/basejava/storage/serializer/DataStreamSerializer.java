package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            WriteDataConsumer.writeWithException(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            WriteDataConsumer.writeWithException(resume.getSections().entrySet(), dos, entry -> {
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
                        WriteDataConsumer.writeWithException(((ListSection) section).getContent(), dos,
                                dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        WriteDataConsumer.writeWithException(((OrganizationSection) section).getContent(), dos,
                                organization -> {
                                    Link link = organization.getHomePage();
                                    dos.writeUTF(link.getName());
                                    String url = link.getUrl();
                                    dos.writeUTF(url == null ? "null" : url);
                                    WriteDataConsumer.writeWithException(organization.getPositions(), dos, position -> {
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
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSize = dis.readInt();
                        List<String> content = new ArrayList<>(listSize);
                        for (int j = 0; j < listSize; j++) {
                            content.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(content));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int orgListSize = dis.readInt();
                        List<Organization> orgContent = new ArrayList<>(orgListSize);
                        for (int j = 0; j < orgListSize; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            Link link = new Link(name, (url.equals("null") ? null : url));
                            int posListSize = dis.readInt();
                            List<Organization.Position> posContent = new ArrayList<>(posListSize);
                            for (int k = 0; k < posListSize; k++) {
                                LocalDate startDate = DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
                                LocalDate endDate = DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                posContent.add(new Organization.Position(startDate, endDate, title,
                                        (description.equals("null") ? null : description)));
                            }
                            orgContent.add(new Organization(link, posContent));
                        }
                        resume.addSection(sectionType, new OrganizationSection(orgContent));
                }
            }
            return resume;
        }
    }

    @FunctionalInterface
    public interface WriteDataConsumer<T> {
        void writeData(T t) throws IOException;

        static <T> void writeWithException(Collection<T> collection, DataOutputStream dos,
                                           WriteDataConsumer<T> consumer) throws IOException {
            dos.writeInt(collection.size());
            for (T t : collection) {
                consumer.writeData(t);
            }
        }
    }
}
