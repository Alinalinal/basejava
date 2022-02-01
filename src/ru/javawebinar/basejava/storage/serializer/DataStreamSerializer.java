package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                String sectionName = entry.getKey().name();
                dos.writeUTF(sectionName);
                switch (sectionName) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        dos.writeUTF(((TextSection) entry.getValue()).getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> list = ((ListSection) entry.getValue()).getContent();
                        dos.writeInt(list.size());
                        for (String s : list) {
                            dos.writeUTF(s);
                        }
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Organization> content = ((OrganizationSection) entry.getValue()).getContent();
                        dos.writeInt(content.size());
                        for (Organization org : content) {
                            Link link = org.getHomePage();
                            dos.writeUTF(link.getName());
                            String url = link.getUrl();
                            if (url == null) {
                                url = "null";
                            }
                            dos.writeUTF(url);
                            List<Organization.Position> positions = org.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                LocalDate startDate = position.getStartDate();
                                dos.writeInt(startDate.getYear());
                                dos.writeInt(startDate.getMonthValue());
                                LocalDate endDate = position.getEndDate();
                                dos.writeInt(endDate.getYear());
                                dos.writeInt(endDate.getMonthValue());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                if (description == null) {
                                    description = "null";
                                }
                                dos.writeUTF(description);
                            }
                        }
                        break;
                }
            }
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
                            if (url.equals("null")) {
                                url = null;
                            }
                            Link link = new Link(name, url);
                            int posListSize = dis.readInt();
                            List<Organization.Position> posContent = new ArrayList<>(posListSize);
                            for (int k = 0; k < posListSize; k++) {
                                LocalDate startDate = DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
                                LocalDate endDate = DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("null")) {
                                    description = null;
                                }
                                posContent.add(new Organization.Position(startDate, endDate, title, description));
                            }
                            orgContent.add(new Organization(link, posContent));
                        }
                        resume.addSection(sectionType, new OrganizationSection(orgContent));
                }
            }
            return resume;
        }
    }
}
