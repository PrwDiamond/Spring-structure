package com.example.demo.exception;

public class UserException extends BaseException {

    public UserException(String code) {
        super("user." + code);
    }

    public static UserException notFound() {
        return new UserException("user.not.found");
    }

    public static UserException unauthorized() {
        return new UserException("unauthorized");
    }


    //CREATE

    public static UserException createEmailNull() {
        return new UserException("create.email.null");
    }

    public static UserException createEmailDuplicated() {
        return new UserException("create.email.duplicated");
    }

    public static UserException createPasswordNull() {
        return new UserException("create.password.null");
    }

    public static UserException createNameNull() {
        return new UserException("create.name.null");
    }


    // LOGIN

    public static UserException loginFailEmailNotFound() {
        return new UserException("login.fail");
    }

    public static UserException loginFailPasswordIncorrect() {
        return new UserException("login.fail");
    }

    public static UserException loginFailUserUnactivated() {
        return new UserException("login.fail.unactivated");
    }


    //Activate

    public static UserException activateNoToken() {
        return new UserException("activate.no.token");
    }

    public static UserException activateAlready() {
        return new UserException("activate.already");
    }

    public static UserException activateFail() {
        return new UserException("activate.fail");
    }

    public static UserException activateTokenExpire() {
        return new UserException("activate.token.expire");
    }


    //Resend Activation Email

    public static UserException resendActivationEmailNoEmail() {
        return new UserException("resend.activation.no.email");
    }

    public static UserException resendActivationEmailNotFound() {
        return new UserException("resend.email.fail");
    }


}
