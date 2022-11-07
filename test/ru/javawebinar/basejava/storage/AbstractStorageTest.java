package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.ResumeTestData.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "NewName");
        newResume.setContact(ContactType.EMAIL, "mail1@google.com");
        newResume.setContact(ContactType.SKYPE, "NewSkype");
        newResume.setContact(ContactType.MOBILE_PHONE_NUMBER, "+7(921) 222-2222");
        newResume.setSection(SectionType.OBJECTIVE, new TextSection("Новая позиция"));
        newResume.setSection(SectionType.PERSONAL, new TextSection("Новые личные качества"));
        newResume.setSection(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<>(Arrays.asList
                ("Новые достижения 1", "Новые достижения 2", "Новые достижения 3"))));
        newResume.setSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList
                ("Новая квалификация 1", "Новая квалификация 2", "Новая квалификация 3"))));
        newResume.setSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Новая Организация", "http://новаяОрганизация.ua/",
                        new Organization.Position(LocalDate.of(2013, Month.NOVEMBER, 1),
                                LocalDate.of(2022, Month.DECEMBER, 1), "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок."))));
        newResume.setSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("Новое образование", "https://www.новоеОбразование.org/course/progfun",
                        new Organization.Position(LocalDate.of(2013, Month.APRIL, 1),
                                LocalDate.of(2013, Month.JUNE, 1),
                                "\"Functional Programming Principles in Scala\" by Martin Odersky", null))));
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(R4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        List<Resume> expectedList = Arrays.asList(R1, R2, R3);
        Collections.sort(expectedList);
        assertEquals(expectedList, list);
    }

    @Test
    public void save() {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}