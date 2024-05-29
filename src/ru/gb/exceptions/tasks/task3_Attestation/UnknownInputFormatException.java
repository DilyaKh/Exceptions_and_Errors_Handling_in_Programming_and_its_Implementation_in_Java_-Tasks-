package ru.gb.exceptions.tasks.task3_Attestation;

import java.util.ArrayList;
import java.util.List;



class UnknownInputFormatException extends InputException {
    private final List<String> unknownFormats;

    public UnknownInputFormatException(List<String> unknownFormats) {
        super("Введены компоненты, не соответствующие ни одному из требуемых форматов:\n" + unknownFormats);
        this.unknownFormats = new ArrayList<>(unknownFormats);
    }

    public List<String> getUnknownFormats() {
        return unknownFormats;
    }
}


//--------------------------------------------------------------------