package business;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ValidationException extends Exception {


    private List<String> invalidFieldNames = new ArrayList<>();

    public ValidationException() {
    }

    public ValidationException(String invalidFieldName) {
        super();
        fieldError(invalidFieldName);
    }

    public ValidationException fieldError(String fieldName) {
        invalidFieldNames.add(fieldName);
        return this;
    }


    public boolean hasErrors() {
        return !invalidFieldNames.isEmpty();
    }

    public List<String> getInvalidFieldNames() {
        return invalidFieldNames;
    }

    public boolean hasInvalidField(String name) {
        return invalidFieldNames.contains(name);
    }
}
