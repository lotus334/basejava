import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int lastIndex = 0;

    void clear() {
        for (int i = 0; i < lastIndex; i++) {
            storage[i] = null;
        }
        lastIndex = 0;
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
                int remainingElements = size() - ( i + 1 );
                System.arraycopy(storage, i + 1, storage, i, remainingElements);
                storage[lastIndex] = null;
                lastIndex--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, lastIndex);
    }

    int size() {
        return lastIndex;
    }
}
