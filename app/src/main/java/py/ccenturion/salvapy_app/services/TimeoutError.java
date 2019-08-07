package py.ccenturion.salvapy_app.services;

import java.io.IOException;

/**
 * Created by isabelinorolandolopezroman on 21/7/17.
 */

public class TimeoutError extends IOException {
    private static final long serialVersionUID = -6469766654369165864L;

    public TimeoutError() {
        super();
    }

    public TimeoutError(Throwable cause) {
        super(cause);
    }
}
