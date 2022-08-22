public class Bot {

    protected final String name;
    protected final TaskList taskList;
    protected final Parser parser;
    protected static final String BORDER = "____________________________________________________________";

    public Bot() {
        this.name = "Bocil";
        this.taskList = new TaskList();
        this.parser = new Parser();
    }

    public String introduce() {
        String line1 = String.format("Hello! I'm %s", this.name);
        String line2 = "What can I do for you?";
        return String.format("%s\n%s\n%s\n%s\n", BORDER, line1, line2, BORDER);
    }

    public String answer(String input) {
        String response;
        try {
            String[] split = input.split("\\s+");
            if (split.length == 0) {
                throw DukeException.DukeEmptyInputException();
            }
            String command = split[0];
            if (command.equals("todo") | command.equals("event") | command.equals("deadline")) {
                Task task;
                String name = "";
                for (int i = 1; i < split.length; i++) {
                    name = String.format("%s%s", name, split[i]);
                    if (i < split.length-1) {
                        name = name.concat(" ");
                    }
                }
                if (name.equals("")) {
                    throw DukeException.DukeEmptyNameException();
                }
                try {
                    if (command.equals("todo")) {
                        task = new Todo(name);
                    } else if (command.equals("deadline")) {
                        String[] details = name.split("\\s+/by\\s+");
                        task = new Deadline(details[0], this.parser.parseTime(details[1]));
                    } else {
                        String[] details = name.split("\\s+/at\\s+");
                        task = new Event(details[0], this.parser.parseTime(details[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw DukeException.DukeInvalidFormatException();
                }
                this.taskList.addTask(task);
                String header = "Got it. I've added this task:";
                String line = String.format("  %s", task);
                String footer = String.format("Now you have %s task in the list", this.taskList.getSize());
                response = String.format("%s\n%s\n%s", header, line, footer);
            } else if (command.equals("mark")) {
                try {
                    if (split.length == 2) {
                        int num = Integer.parseInt(split[1]);
                        Task task = this.taskList.getTask(num);
                        task.mark();
                        String header = "Nice! I've marked this task as done:";
                        String line = String.format("  %s", task);
                        response = String.format("%s\n%s", header, line);
                    } else {
                        throw DukeException.DukeInvalidIndexException();
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    throw DukeException.DukeInvalidIndexException();
                }
            } else if (command.equals("unmark")) {
                try {
                    if (split.length == 2) {
                        int num = Integer.parseInt(split[1]);
                        Task task = this.taskList.getTask(num);
                        task.unmark();
                        String header = "OK, I've marked this task as not done yet:";
                        String line = String.format("  %s", task);
                        response = String.format("%s\n%s", header, line);
                    } else {
                        throw DukeException.DukeInvalidIndexException();
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    throw DukeException.DukeInvalidIndexException();
                }
            } else if (command.equals("delete")) {
                try {
                    if (split.length == 2) {
                        int num = Integer.parseInt(split[1]);
                        Task task = this.taskList.getTask(num);
                        taskList.removeTask(num);
                        String header = "Noted. I've removed this task:";
                        String line = String.format("  %s", task.toString());
                        response = String.format("%s\n%s", header, line);
                    } else {
                        throw DukeException.DukeInvalidIndexException();
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    throw DukeException.DukeInvalidIndexException();
                }
            } else if (command.equals("bye")) {
                if (input.equals("bye")) {
                    String header = "Bye. Hope to see you again soon!";
                    response = String.format("%s", header);
                } else {
                    throw DukeException.DukeUnknownCommandException();
                }
            } else if (command.equals("list")) {
                if (input.equals("list")) {
                    String header = "Here are the tasks in your list";
                    response = String.format("%s\n%s", header, taskList);
                } else {
                    throw DukeException.DukeUnknownCommandException();
                }
            } else {
                throw DukeException.DukeUnknownCommandException();
            }
            return String.format("%s\n%s\n%s\n", BORDER, response, BORDER);
        } catch (DukeException e) {
            return String.format("%s\n%s\n%s\n", BORDER, e.getMessage(), BORDER);
        }
    }
}
