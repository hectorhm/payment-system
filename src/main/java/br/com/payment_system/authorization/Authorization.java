package br.com.payment_system.authorization;

public record Authorization(String message, DataResponse data) {

    public Boolean isAuthorized() {
        return message.equals("success") || data.authorization().equals(Boolean.TRUE);
    }
}
