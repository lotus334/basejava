import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int lastIndex = 0;

    void clear() {
        for (int i = 0; i <= lastIndex; i++) {
            storage[i] = null;
            lastIndex = 0;
        }
    }

    void save(Resume r) {
        storage[lastIndex] = r;
        lastIndex++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < lastIndex; i++) {
            if (uuid.equals(storage[i].toString())) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < lastIndex; i++) {
            if (uuid.equals(storage[i].toString())) {

                Resume[] newStorage = new Resume[10000];
                int remainingElements = size() - ( i + 1 );
                System.arraycopy(storage, 0, newStorage, 0, i);
                System.arraycopy(storage, i + 1, newStorage, i, remainingElements);
                storage = newStorage;
                lastIndex--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] newStorage = Arrays.copyOfRange(storage, 0, lastIndex);
        return newStorage;
    }

    int size() {
        return lastIndex;
    }
}
