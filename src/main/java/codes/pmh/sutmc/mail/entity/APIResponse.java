package codes.pmh.sutmc.mail.entity;

public class APIResponse<T> {
    private T result;
    private Boolean success;
    private APIMessage[] errors;
    private APIMessage[] messages;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public APIMessage[] getErrors() {
        return errors;
    }

    public void setErrors(APIMessage[] errors) {
        this.errors = errors;
    }

    public APIMessage[] getMessages() {
        return messages;
    }

    public void setMessages(APIMessage[] messages) {
        this.messages = messages;
    }
}
