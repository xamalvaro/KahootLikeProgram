import java.util.*;

class Student {
    private String id;
    //sets the Unique identifier for each student
    public Student() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}

class Question {
    private String type; //Sets the question type to "single-choice" or "multiple-choice"
    private List<String> studentAnswers;

    public Question(String type, List<String> studentAnswers) {
        this.type = type;
        this.studentAnswers = studentAnswers;
    }

    public String getType() {
        return type;
    }

    public List<String> getStudentAnswers() {
        return studentAnswers;
    }
}

class VotingService {
    private Question question;
    private Map<String, List<String>> submissions;

    public VotingService() {
        submissions = new HashMap<>();
    }

    public void configureQuestion(Question question) {
        this.question = question;
    }

    public void submitAnswer(String studentId, String answer) {
        if (question != null && question.getStudentAnswers().contains(answer)) {
            if (question.getType().equals("single-choice")) {
                submissions.put(studentId, Collections.singletonList(answer));
            } else {
                submissions.putIfAbsent(studentId, new ArrayList<>());
                submissions.get(studentId).add(answer);
            }
        } else {
            System.out.println("Invalid answer submission.");
        }
    }

    public void getResults() {
        Map<String, Integer> results = new HashMap<>();
        for (String answer : question.getStudentAnswers()) {
            results.put(answer, 0);
        }

        for (List<String> answers : submissions.values()) {
            for (String answer : answers) {
                results.put(answer, results.get(answer) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class SimulationDriver {

    private Random rand = new Random();

    public void simulateMultipleChoice(int numberOfStudents, List<String> studentAnswers) {
        // Creates the multiple choice question
        Question question = new Question("multiple-choice", studentAnswers);

        // Configures the VotingService with the question
        VotingService votingService = new VotingService();
        votingService.configureQuestion(question);

        // Generates the students and answers
        for (int i = 0; i < numberOfStudents; i++) {
            Student student = new Student();
            String randomAnswer = studentAnswers.get(rand.nextInt(studentAnswers.size()));
            votingService.submitAnswer(student.getId(), randomAnswer);
        }

        // Prints the results
        System.out.println("Multiple Choice Simulation:");
        votingService.getResults();
    }

    public void simulateSingleChoice(int numberOfStudents, List<String> studentAnswers) {
        // Creates the single choice question
        Question question = new Question("single-choice", studentAnswers);

        // Configures the VotingService with the question
        VotingService votingService = new VotingService();
        votingService.configureQuestion(question);

        // Generates the students and answers
        for (int i = 0; i < numberOfStudents; i++) {
            Student student = new Student();
            String randomAnswer = studentAnswers.get(rand.nextInt(studentAnswers.size()));
            votingService.submitAnswer(student.getId(), randomAnswer);
        }

        // Prints the results
        System.out.println("Single Choice Simulation:");
        votingService.getResults();
    }
    //Multiple scenarios were added, the amount of students and can be changed 
    //  with the answers being randomized in the SimulationDriver class.
    public void simulateVariousScenarios() {
        List<String> multipleChoiceAnswers = Arrays.asList("A", "B", "C", "D");
        List<String> singleChoiceAnswers = Arrays.asList("Yes", "No");

        System.out.println("\nScenario 1: 10 students, multiple-choice");
        simulateMultipleChoice(10, multipleChoiceAnswers);

        System.out.println("\nScenario 2: 50 students, multiple-choice");
        simulateMultipleChoice(50, multipleChoiceAnswers);

        System.out.println("\nScenario 3: 10 students, single-choice");
        simulateSingleChoice(10, singleChoiceAnswers);

        System.out.println("\nScenario 4: 50 students, single-choice");
        simulateSingleChoice(50, singleChoiceAnswers);
    }
}

public class Main1 {
    public static void main(String[] args) {
        SimulationDriver simulationDriver = new SimulationDriver();
        simulationDriver.simulateVariousScenarios();
    }
}