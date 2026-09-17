package nabmak.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses and validates user commands.
 */
public class Parser {
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Parses and validates user commands.
     *
     * @param input user command
     * @param taskNum number of tasks currently in task list
     * @throws NabmakException if user command is invalid
     */
    public static void parse(String input, int taskNum, int placeNum) throws NabmakException {
        if (input.equals("find")) {
            throw new NabmakException("Find what?");
        }
        if (input.equals("todo")) {
            throw new NabmakException("You need to say what task you wanna do.");
        }
        if (input.equals("deadline")) {
            throw new NabmakException("Deadline must have a '<description> /by <date>'.");
        }
        if (input.equals("event")) {
            throw new NabmakException("Event must have a '<description> /from <date> /to <date>'.");
        }
        if (input.equals("mark")) {
            throw new NabmakException("mark which task?");
        }
        if (input.equals("unmark")) {
            throw new NabmakException("unmark which task?");
        }
        if (input.equals("delete")) {
            throw new NabmakException("delete which task?");
        }
        if (input.equals("place")) {
            throw new NabmakException("what place?");
        }
        if (input.startsWith("todo ")) {
            String desc = input.substring(5);
            if (desc.isEmpty()) {
                throw new NabmakException("You need to say what task you wanna do.");
            }
        } else if (input.startsWith("deadline ")) {
            String info = input.substring(9);
            int mid = info.indexOf(" /by ");
            if (input.startsWith("deadline /by")) {
                throw new NabmakException("Deadline cant have empty description");
            }
            if (mid == -1 && info.indexOf(" /by") >= 0) {
                throw new NabmakException("Deadline cant have empty end date");
            }
            if (mid == -1) {
                throw new NabmakException("Deadline must have a '<description> /by <date>'.");
            }
            String desc = info.substring(0, mid);
            if (desc.isEmpty()) {
                throw new NabmakException("Deadline cant have empty description");
            }
            String deadline = info.substring(mid + 5);
            if (deadline.isEmpty()) {
                throw new NabmakException("Deadline cant have empty end date");
            }

            try {
                LocalDateTime.parse(deadline, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                throw new NabmakException("Deadline date must be 'dd-MM-yyyy HH:mm'.");
            }
        } else if (input.startsWith("event ")) {
            String info = input.substring(6);
            int left = info.indexOf(" /from ");
            if (input.startsWith("event /from")) {
                throw new NabmakException("Event cant have empty description");
            }
            if (left == -1 && info.indexOf(" /from") >= 0) {
                throw new NabmakException("Event cant have empty start date");
            }
            if (left == -1) {
                throw new NabmakException("Event must have a '<description> /from <date> /to <date>'.");
            }
            int right = info.indexOf(" /to ");
            if (right == -1) {
                throw new NabmakException("Event must have a '<description> /from <date> /to <date>'.");
            }
            String desc = info.substring(0, left);
            if (desc.isEmpty()) {
                throw new NabmakException("Event cant have empty description");
            }
            if (left + 7 > right) {
                throw new NabmakException("Event cant have empty start date");
            }
            String start = info.substring(left + 7, right);
            if (start.isEmpty()) {
                throw new NabmakException("Event cant have empty start date");
            }

            try {
                LocalDateTime.parse(start, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                throw new NabmakException("Event start date must be 'dd-MM-yyyy HH:mm'.");
            }
            String end = info.substring(right + 4);
            if (end.isEmpty()) {
                throw new NabmakException("Event cant have empty end date");
            }

            end = info.substring(right + 5);

            try {
                LocalDateTime.parse(end, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                throw new NabmakException("Event end date must be 'dd-MM-yyyy HH:mm'.");
            }
        } else if (input.equals("bye") || input.equals("list")) {

        } else if (input.startsWith("mark ")) {
            checkTaskNum(input.substring(5), taskNum, "You arent that busy");

        } else if (input.startsWith("unmark ")) {
            checkTaskNum(input.substring(7), taskNum,
                "You are making yourself busier than needed");

        } else if (input.startsWith("delete ")) {
            checkTaskNum(input.substring(7), taskNum,
                "You cant delete something that doesnt exist");

        } else if (input.startsWith("find ")) {
            String keyword = input.substring(5);

            if (keyword.isEmpty()) {
                throw new NabmakException("Find what?");
            }
        } else if (input.startsWith("place ")) {
            String info = input.substring(6);

            if (info.equals("list")) {

            } else if (info.startsWith("delete ")) {
                checkPlaceNum(info.substring(7), placeNum, "You dont have that many places");
            } else if (info.startsWith("add ")) {
                valideatePlaceAdd(info.substring(4));
            } else {
                throw new NabmakException("Use place add, place list or place delete.");
            }
        } else {
            throw new NabmakException(
                "Idk whatchu mean. Input either a todo, deadline or event. Or mark, unmark, delete.");
        }
    }

    /**
     * Checks if given taks number is valid.
     *
     * @param input task number being checked
     * @param taskNum number of tasks currently in task list
     * @param error error message if input is invalid
     * @throws NabmakException if input is invalid
     */
    private static void checkTaskNum(String input, int taskNum, String error)
            throws NabmakException {

        int num;

        try {
            num = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new NabmakException("Give valid task number");
        }

        if (num < 1 || num > taskNum) {
            throw new NabmakException(error);
        }
    }

    /**
     * Checks if given place number is valid.
     *
     * @param input place number being checked
     * @param placeNum number of places currently in place list
     * @param error error message if input is invalid
     * @throws NabmakException if input is invalid
     */
    private static void checkPlaceNum(String input, int placeNum, String error)
            throws NabmakException {

        int num;

        try {
            num = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new NabmakException("Give valid place number");
        }

        if (num < 1 || num > placeNum) {
            throw new NabmakException(error);
        }
    }

    /**
     * Validates the input for adding a place.
     *
     * @param input input for adding a place
     * @throws NabmakException if input is invalid
     */
    private static void valideatePlaceAdd(String input) throws NabmakException {
        int mid = input.indexOf(" /details ");

        if (mid == -1) {
            throw new NabmakException("Place must have a '<name> /details <details>'.");
        }

        String name = input.substring(0, mid);
        String details = input.substring(mid + 10);

        if (name.isEmpty()) {
            throw new NabmakException("Place cant have empty name");
        }
        if (details.isEmpty()) {
            throw new NabmakException("Place cant have empty details");
        }
    }
}
