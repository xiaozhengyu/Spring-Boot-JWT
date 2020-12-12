package com.xzy.sbjt.common.utils.msg;

/**
 * @param <T>
 * @author xzy
 * @date 2020-10-26 10:23
 * 说明：{ "status":xxx,"message":"xxx","ok":xxx,"data":xxx}
 */

public class MessageBox<T> extends Message {
    private T data;

    public MessageBox() {
        super();
        this.data = null;
    }

    public MessageBox(T data) {
        this(BOOLEAN_OK, data);
    }

    public MessageBox(boolean ok, T data) {
        super(BOOLEAN_OK);
        this.data = data;
    }

    public MessageBox(int status, String message, boolean ok, T data) {
        super(status, message, ok);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return - { "status":1,"message":"成功","ok":true,"data":xxx}
     */
    public static <E> MessageBox<E> ok(E data) {
        return new MessageBox<>(BOOLEAN_OK, data);
    }

    /**
     * @return - { "status":0,"message":"失败","ok":false,"data":xxx}
     */
    public static <E> MessageBox<E> fail(E data) {
        return new MessageBox<>(BOOLEAN_FAIL, data);
    }

    public static MessageBox send(Object data, Object... params) {
        int status = STATUS_OK;
        String message = MESSAGE_OK;
        boolean getStatus = false;
        boolean getMsg = false;

        for (Object param : params) {
            if (param instanceof Integer && !getStatus) {
                status = (Integer) param;
                getStatus = true;
            } else if (param instanceof String && !getMsg) {
                message = (String) param;
                getMsg = true;
            }
            if (getStatus && getMsg) {
                break;
            }
        }

        return new MessageBox(status, message, BOOLEAN_OK, data);
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"status\": " + status + ",\n" +
                "    \"ok\": " + ok + ",\n" +
                "    \"message\": " + message + ",\n" +
                "    \"data\": " + data.toString() + "\n" +
                "}\n";
    }
}
