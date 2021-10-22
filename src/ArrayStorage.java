/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int size = size();
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            } else if (storage[i].uuid.equals(r.uuid)) {
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume == null) {
                // no Resume with this uuid
                break;
            } else if (resume.uuid.equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                rebuiltArray();
                break;
            }
        }
    }

    /**
     * nulls to the tail of a storage
     */
    private void rebuiltArray() {
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] == null && storage[i + 1] == null) {
                break;
            } else if (storage[i] == null && storage[i + 1] != null) {
                storage[i] = storage[i + 1];
                storage[i + 1] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int size = size();
        Resume[] arr = new Resume[size];
        System.arraycopy(storage, 0, arr, 0, size);
        return arr;
    }

    int size() {
        int size = 0;
        for (Resume resume : storage) {
            if (resume == null) {
                return size;
            } else {
                size++;
            }
        }
        return size;
    }
}
