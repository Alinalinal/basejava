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
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            WriteDataConsumer.writeWithException(contacts.entrySet(), dos, (dos1, o) -> {
                Map.Entry<ContactType, String> entry = (Map.Entry<ContactType, String>) o;
                dos1.writeUTF(entry.getKey().name());
                dos1.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            WriteDataConsumer.writeWithException(sections.entrySet(), dos, (dos12, o) -> {
                Map.Entry<SectionType, AbstractSection> entry = (Map.Entry<SectionType, AbstractSection>) o;
                SectionType sectionType = entry.getKey();
                dos12.writeUTF(sectionType.name());
                AbstractSection section = entry.getValue();
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos12.writeUTF(((TextSection)section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list =
                                ((ListSection) entry.getValue()).getContent();
                        dos12.writeInt(list.size());
                        WriteDataConsumer.writeWithException(list, dos, (dos13, o1) -> dos13.writeUTF((String) o1));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> content = ((OrganizationSection)
                                entry.getValue()).getContent();
                        dos12.writeInt(content.size());
                        WriteDataConsumer.writeWithException(content, dos, (dos15, o13) -> {
                            Organization organization = (Organization) o13;
                            Link link = organization.getHomePage();
                            dos12.writeUTF(link.getName());
                            String url = link.getUrl();
                            dos12.writeUTF(url == null ? "null" : url);
                            List<Organization.Position> positions = organization.getPositions();
                            dos12.writeInt(positions.size());
                            WriteDataConsumer.writeWithException(positions, dos15, (dos14, o12) -> {
                                Organization.Position position = (Organization.Position) o12;
                                DateUtil.writeAsData(dos12, position.getStartDate());
                                DateUtil.writeAsData(dos12, position.getEndDate());
                                dos12.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                dos12.writeUTF(description == null ? "null" : description);
                            });
                        });
                        break;
                }
            });

            /*
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            /*for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }*/
            /*contacts.entrySet().forEach((entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(String.valueOf(entry.getValue()));
            });
            //Set<Map.Entry<ContactType, String>> set = contacts.entrySet();
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) entry.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = ((ListSection) entry.getValue()).getContent();
                        dos.writeInt(list.size());
                        for (String s : list) {
                            dos.writeUTF(s);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> content = ((OrganizationSection) entry.getValue()).getContent();
                        dos.writeInt(content.size());
                        for (Organization org : content) {
                            Link link = org.getHomePage();
                            dos.writeUTF(link.getName());
                            String url = link.getUrl();
                            dos.writeUTF(url == null ? "null" : url);
                            List<Organization.Position> positions = org.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                DateUtil.writeAsData(dos, position.getStartDate());
                                DateUtil.writeAsData(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                dos.writeUTF(description == null ? "null" : description);
                            }
                        }
                        break;
                }
            } */
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
        void writeData(DataOutputStream dos, T t) throws IOException;

        static <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriteDataConsumer consumer)
                throws IOException {
            for (T t: collection) {
                consumer.writeData(dos, t);
            }
        }
    }
}
