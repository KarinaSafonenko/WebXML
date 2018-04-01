package by.epam.safonenko.xml.command;

import by.epam.safonenko.xml.exception.XMLParsingException;
import by.epam.safonenko.xml.type.CommandType;

public class CommandFactory {
    public Command defineCommand(String commandType) throws XMLParsingException {
        CommandType type = CommandType.valueOf(commandType);
        switch (type){
            case PARSER:
                return new ParserCommand();
            case MAIL:
                return new EmailCommand();
            default:
                throw new XMLParsingException("Exception in defining command");
        }
    }
}
