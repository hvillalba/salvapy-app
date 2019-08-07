package py.ccenturion.salvapy_app.data.dto;

import java.io.Serializable;

public class Request<T> implements Serializable {

    private String type;
    private T data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
