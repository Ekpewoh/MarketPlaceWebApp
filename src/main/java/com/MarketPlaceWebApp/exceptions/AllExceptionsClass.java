package com.MarketPlaceWebApp.exceptions;

public class AllExceptionsClass{

    //CUSTOMER EXCEPTIONS
    public static class CreateNewRoleException extends RuntimeException{
        public CreateNewRoleException(String msg){
            super(msg);
        }
    }
    public static class CustomerDoesNotExistException extends RuntimeException{
        public CustomerDoesNotExistException(String msg){
            super(msg);
        }
    }
    public static class CustomerNotFoundException extends RuntimeException{
        public CustomerNotFoundException(String msg){
            super(msg);
        }
    }
    public static class UnableToCreateCustomerException extends RuntimeException{
        public UnableToCreateCustomerException(String msg){
            super(msg);
        }
    }

    public static class UnableToCreateAuthException extends RuntimeException{
        public UnableToCreateAuthException(String msg){
            super(msg);
        }
    }

    //PRODUCT EXCEPTIONS
    public static class UnableToCreateProduct extends RuntimeException{
        public UnableToCreateProduct(String msg){super(msg);}
    }
    public static class UnableToFindAuthCredentials extends RuntimeException{
        public UnableToFindAuthCredentials(String msg){super(msg);}
    }
}
