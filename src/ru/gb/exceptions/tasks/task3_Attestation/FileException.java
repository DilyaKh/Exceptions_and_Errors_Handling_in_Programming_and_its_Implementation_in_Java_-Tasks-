package ru.gb.exceptions.tasks.task3_Attestation;



class FileException extends Exception {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}


//---------------------------------------------------------------